package kr.co.datastreams.llmetabe.api.extraction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.co.datastreams.llmetabe.api.extraction.dto.request.ExtractionRequestDto;
import kr.co.datastreams.llmetabe.api.extraction.service.ExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.datastreams.llmetabe.global.utils.Response;

import java.security.Principal;

/**
 * 데이터 추출 컨트롤러
 * @version 1.0.0
 * @author Kim Dayeong
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/extraction")
@Tag(name = "추출 컨트롤러")
public class ExtractionController {

    private final ExtractionService extractionService;

    /**
     * 데이터 추출 메소드
     * @param extractionRequestDto
     * @param principal
     *
     * @return Response<?>
     */
    @PostMapping()
    @Operation(summary = "데이터 추출", description = "데이터를 추출합니다.")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Response.class)))
    public Response<?> extractData(@RequestBody ExtractionRequestDto extractionRequestDto, Principal principal) {
        return Response.ok(extractionService.extractData(extractionRequestDto, principal), "성공적으로 데이터를 추출하였습니다.");
    }
}
