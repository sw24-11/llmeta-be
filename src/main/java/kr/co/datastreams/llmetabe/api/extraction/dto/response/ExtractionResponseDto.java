package kr.co.datastreams.llmetabe.api.extraction.dto.response;

import kr.co.datastreams.llmetabe.api.extraction.enums.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExtractionResponseDto {
    private DataType type;
    private List<MetaData> metaData;
}
