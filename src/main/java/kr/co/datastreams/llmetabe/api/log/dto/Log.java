package kr.co.datastreams.llmetabe.api.log.dto;

import kr.co.datastreams.llmetabe.api.extraction.dto.request.ExtractionRequestDto;
import kr.co.datastreams.llmetabe.api.extraction.dto.response.MetaData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private Timestamp createAt;
    private Input input;
    private List<MetaData> output; // 형식이 같아 차용
}
