package kr.co.datastreams.llmetabe.api.extraction.dto.response;

import kr.co.datastreams.llmetabe.api.extraction.enums.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExtractionResponseDto {
    private Long extractionId;
    private DataType type;
    private MetaData metaData;
}
