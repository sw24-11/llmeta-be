package kr.co.datastreams.llmetabe.api.auth.controller;

import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;
import kr.co.datastreams.llmetabe.api.auth.service.AuthService;
import kr.co.datastreams.llmetabe.global.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public Response<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return Response.ok("성공적으로 회원가입하였습니다.");
    }

    @PostMapping("/signup/redundancyCheck")
    public Response<?> signupRedundancyCheck(@RequestBody String email) {
        authService.signupRedundancyCheck(email);
        return Response.ok("중복되지 않은 이메일입니다.");
    }
}
