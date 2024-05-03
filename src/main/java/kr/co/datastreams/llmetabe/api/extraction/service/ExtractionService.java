package kr.co.datastreams.llmetabe.api.extraction.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import kr.co.datastreams.llmetabe.api.extraction.dao.ExtractionDao;
import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import kr.co.datastreams.llmetabe.api.extraction.dto.request.ExtractionRequestDto;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.ExtractionResponseDto;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.FlaskResponseDto;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.MetaData;
import kr.co.datastreams.llmetabe.api.member.dao.MemberDao;
import kr.co.datastreams.llmetabe.global.exception.FileInputStreamException;
import kr.co.datastreams.llmetabe.api.extraction.exception.FlaskHttpNoResponseException;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * metadata extraction service class
 * @version 1.0.0
 * @author Kim Dayeong
 */
@Service
@RequiredArgsConstructor
public class ExtractionService {

    @Value("${flask.url}")
    private String flaskUrl;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final ExtractionDao extractionDao;
    private final MemberDao memberDao;
    private final AmazonS3Client amazonS3Client;

    /**
     * metadata를 추출하고 이를 DB와 S3에 저장하는 메소드
     * @param extractionRequestDto extractionRequest 시 사용하는 dto
     * @param principal principal
     *
     * @return extractionResponseDto
     * @throws FlaskHttpNoResponseException Flask 서버에서 응답이 없을 경우
     * @throws FileInputStreamException 파일을 읽는 중 예외가 발생할 경우
     */
    public ExtractionResponseDto extractData(ExtractionRequestDto extractionRequestDto, Principal principal) {

        FlaskResponseDto flaskResponseDto;
        List<MetaData> metaData;

        // Flask server로부터 response를 받아옴 -> 받아온 response를 parsing하여 MetaData로 변환
        try {
            flaskResponseDto = getFlaskResponse(extractionRequestDto);
            metaData = parseMetaDataFromFlaskResponse(flaskResponseDto);
        } catch (Exception e) {
            throw new FlaskHttpNoResponseException();
        }

        // log를 남기기 위해 S3에 추출 전 파일을 저장
        try {
            saveFileToS3(extractionRequestDto.getFile());
        } catch (IOException e) {
            throw new FileInputStreamException();
        }

        // 추출 정보를 DB에 저장
        try {
            ExtractionEntity extractionEntity = new ExtractionEntity();
            extractionEntity.setMember(memberDao.getMemberEntityByEmail(principal.getName()));
            extractionEntity.setType(extractionRequestDto.getDataType());
            extractionEntity.setFileName(extractionRequestDto.getFile().getOriginalFilename());
            extractionEntity.setMetaData(metaDataToString(metaData));

            extractionDao.saveExtractionEntity(extractionEntity);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }

        // response 생성
        ExtractionResponseDto extractionResponseDto = new ExtractionResponseDto();
        extractionResponseDto.setType(extractionRequestDto.getDataType());
        extractionResponseDto.setMetaData(metaData);

        return extractionResponseDto;
    }

    /**
     * Flask 서버로부터 response를 받아오는 메소드
     * @param extractionRequestDto extractionRequest 시 사용하는 dto
     *
     * @return FlaskResponseDto
     * @throws Exception Flask 서버에서 응답이 없을 경우 등
     */
    private FlaskResponseDto getFlaskResponse(ExtractionRequestDto extractionRequestDto) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Body set
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("type", extractionRequestDto.getDataType().getValue());
        body.add("file", extractionRequestDto.getFile());

        // Message
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // Request
        HttpEntity<?> response = restTemplate.postForEntity(flaskUrl, requestMessage, String.class);

        // Response parsing
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return objectMapper.readValue(Objects.requireNonNull(response.getBody()).toString(), FlaskResponseDto.class);
    }

    /**
     * Flask 서버로부터 받아온 response를 parsing하여 MetaData로 변환하는 메소드
     * @param flaskResponseDto Flask 서버로부터 받아온 response
     *
     * @return List<MetaData>
     */
    private List<MetaData> parseMetaDataFromFlaskResponse(FlaskResponseDto flaskResponseDto) {
        List<String> metaDataSplit = List.of(flaskResponseDto.getText().split(", "));
        List<MetaData> metaDatas = new ArrayList<>();

        for (String data : metaDataSplit) {
            String[] split = data.split(": ");
            metaDatas.add(new MetaData(split[0], split[1]));
        }

        return metaDatas;
    }

    /**
     * S3에 파일을 저장하는 메소드
     * @param file MultipartFile
     *
     * @throws IOException 파일을 읽는 중 예외가 발생할 경우
     */
    private void saveFileToS3(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
    }

    /**
     * MetaData를 String으로 변환하는 메소드
     * @param metaData 변환할 MetaData 리스트
     *
     * @return String
     */
    private String metaDataToString(List<MetaData> metaData) {
        StringBuilder sb = new StringBuilder();
        for (MetaData data : metaData) {
            sb.append(data.getKey()).append(": ").append(data.getValue()).append("\n");
        }
        return sb.toString();
    }
}
