package kr.co.datastreams.llmetabe.api.evaluation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "평가 컨트롤러")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping("/evaluation")
    @Operation(summary = "평가", description = "추출 결과를 평가합니다.")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Response.class)))
    public Response<?> evaluate(@RequestBody EvaluationRequestDto evaluationRequestDto) {
        evaluationService.evaluate(evaluationRequestDto);
        return Response.ok("평가가 완료되었습니다.");
    }
}
