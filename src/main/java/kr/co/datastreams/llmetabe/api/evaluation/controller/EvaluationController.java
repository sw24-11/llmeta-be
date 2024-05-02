package kr.co.datastreams.llmetabe.api.evaluation.controller;

import kr.co.datastreams.llmetabe.api.evaluation.dto.EvaluationRequestDto;
import kr.co.datastreams.llmetabe.api.evaluation.service.EvaluationService;
import kr.co.datastreams.llmetabe.global.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/metadata")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping("/evaluation")
    public Response<?> evaluate(@RequestBody EvaluationRequestDto evaluationRequestDto) {
        evaluationService.evaluate(evaluationRequestDto);
        return Response.ok("평가가 완료되었습니다.");
    }
}
