package kr.co.datastreams.llmetabe.api.extraction.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.datastreams.llmetabe.api.extraction.dao.ExtractionDao;
import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import kr.co.datastreams.llmetabe.api.extraction.dto.request.ExtractionRequestDto;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.ExtractionResponseDto;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.FlaskResponseDto;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.MetaData;
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
    private final MemberDao memberDao; // TODO : Implement this class
    private final AmazonS3Client amazonS3Client;

    /**
     * metadata를 추출하고 이를 DB와 S3에 저장하는 메소드
     * @param extractionRequestDto
     * @param principal
     *
     * @return extractionResponseDto
     * @throws FlaskHttpNoResponseException
     * @throws FileInputStreamException
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
            extractionEntity.setMember(memberDao.getMemberEntity(principal.getName())); // TODO : Implement this method
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
     * @param extractionRequestDto
     *
     * @return FlaskResponseDto
     * @throws Exception
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
        FlaskResponseDto flaskResponseDto = objectMapper.readValue(response.getBody().toString(), FlaskResponseDto.class);

        return flaskResponseDto;
    }

    /**
     * Flask 서버로부터 받아온 response를 parsing하여 MetaData로 변환하는 메소드
     * @param flaskResponseDto
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
     * @param file
     *
     * @return void
     * @throws IOException
     */
    private void saveFileToS3(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileUrl = "https://" + bucket + "/log" + fileName;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
    }

    /**
     * MetaData를 String으로 변환하는 메소드
     * @param metaData
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
