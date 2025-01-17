package kr.co.datastreams.llmetabe.api.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRedundancyCheckRequestDto;
import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;
import kr.co.datastreams.llmetabe.api.auth.service.AuthService;
import kr.co.datastreams.llmetabe.global.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
@Tag(name = "인증 컨트롤러")
public class AuthController {

    private final AuthService authService;

    @PostMapping()
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Response.class)))
    public Response<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return Response.ok("성공적으로 회원가입하였습니다.");
    }

    @PostMapping("/redundancyCheck")
    @Operation(summary = "회원가입 중복 체크", description = "회원가입 시 아이디 중복 여부를 확인합니다.")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Response.class)))
    public Response<?> signupRedundancyCheck(@RequestBody SignupRedundancyCheckRequestDto signupRedundancyCheckRequestDto) {
        authService.signupRedundancyCheck(signupRedundancyCheckRequestDto);
        return Response.ok("중복되지 않은 이메일입니다.");
    }
}
