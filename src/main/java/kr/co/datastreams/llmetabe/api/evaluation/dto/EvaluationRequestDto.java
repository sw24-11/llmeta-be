package kr.co.datastreams.llmetabe.api.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRequestDto {

    private Long extractionId;
    private Double rate;
    private String feedback;
}
