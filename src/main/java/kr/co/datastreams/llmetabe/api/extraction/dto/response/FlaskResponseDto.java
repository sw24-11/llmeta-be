package kr.co.datastreams.llmetabe.api.extraction.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlaskResponseDto {
    private List<MetaData> text;
}
