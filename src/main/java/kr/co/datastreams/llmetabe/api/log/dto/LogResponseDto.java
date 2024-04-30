package kr.co.datastreams.llmetabe.api.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Log Response Dto
 * @version 1.0.0
 * @author Kim Dayeong
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogResponseDto {
    private List<Log> logs;
}
