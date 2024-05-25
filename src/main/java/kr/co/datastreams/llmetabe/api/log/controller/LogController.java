package kr.co.datastreams.llmetabe.api.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.datastreams.llmetabe.api.log.service.LogService;
import kr.co.datastreams.llmetabe.global.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/metadata/logs")
@Tag(name = "로그 컨트롤러")
public class LogController {

    private final LogService logService;

    /**
     * 로그 조회 메소드
     * @param principal
     *
     * @return Response<?>
     */
    @GetMapping()
    @Operation(summary = "로그 조회", description = "로그를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Response.class)))
    public Response<?> getLogs(Principal principal) {
        return Response.ok(logService.getLogs(principal), "성공적으로 로그를 조회하였습니다.");
    }
}
