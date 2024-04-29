package kr.co.datastreams.llmetabe.api.extraction.dto.request;

import kr.co.datastreams.llmetabe.api.extraction.enums.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ExtractionRequestDto {
    private DataType dataType;
    private MultipartFile file;
}