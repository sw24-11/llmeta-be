package kr.co.datastreams.llmetabe.api.auth.service;


import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRedundancyCheckRequestDto;
import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;

public interface AuthService {
    void signup(SignupRequestDto signupRequestDto);
    void signupRedundancyCheck(SignupRedundancyCheckRequestDto signupRedundancyCheckRequestDto);
}
