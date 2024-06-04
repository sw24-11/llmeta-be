package kr.co.datastreams.llmetabe.api.log.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import kr.co.datastreams.llmetabe.api.extraction.dao.ExtractionDao;
import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.MetaData;
import kr.co.datastreams.llmetabe.api.member.dao.MemberDao;
import kr.co.datastreams.llmetabe.global.exception.FileInputStreamException;
import kr.co.datastreams.llmetabe.api.log.dto.Input;
import kr.co.datastreams.llmetabe.api.log.dto.Log;
import kr.co.datastreams.llmetabe.api.log.dto.LogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * log service class
 * @version 1.0.0
 * @author Kim Dayeong
 */
@Service
@RequiredArgsConstructor
public class LogService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final MemberDao memberDao;
    private final ExtractionDao extractionDao;
    private final AmazonS3 amazonS3Client;

    /**
     * 로그를 가져와 반환하는 메소드
     * @param principal 인증된 계정인 지 확인, 유저 정보를 담고 있는 principal
     *
     * @return logResponseDto
     */
    public LogResponseDto getLogs(Principal principal) {

        List<ExtractionEntity> extractionEntities = extractionDao.getExtractionEntitiesByMember(memberDao.getMemberEntityByEmail(principal.getName()));
        LogResponseDto logResponseDto = new LogResponseDto();
        List<Log> logs = new ArrayList<>();

        try {
            for (ExtractionEntity extractionEntity : extractionEntities) {
                Log log = new Log();
                log.setExtractionId(extractionEntity.getExtractionId());
                log.setCreateAt(extractionEntity.getCreateAt());
                byte[] file = getFileFromS3(extractionEntity.getFileName());
                log.setInput(new Input(extractionEntity.getCreateAt(), file));
                log.setOutput(parseStringToMetaData(extractionEntity.getMetaData()));

                logs.add(log);
            }
        } catch (IOException e) {
            throw new FileInputStreamException();
        }

        logResponseDto.setLogs(logs);

        return logResponseDto;
    }

    /**
     * S3로부터 파일을 가져와 반환하는 메소드
     * @param fileName 파일 이름
     *
     * @return byte[]
     */
    private byte[] getFileFromS3(String fileName) throws IOException {

        S3Object s3Object = amazonS3Client.getObject(bucket, fileName);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[4096];

        while ((len = s3ObjectInputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        return outputStream.toByteArray();
    }

    /**
     * String -> List<MetaData>로 변환하는 메소드
     * @param metaData 메타데이터
     *
     * @return List<MetaData>
     */
    private List<MetaData> parseStringToMetaData(String metaData) {
        List<String> metaDataSplit = Arrays.asList(metaData.split("\n"));
        List<MetaData> metaDataList = new ArrayList<>();
        for (String data : metaDataSplit) {
            String[] splitData = data.split(": ");
            metaDataList.add(new MetaData(splitData[0], splitData[1]));
        }
        return metaDataList;
    }

}
