package kr.co.datastreams.llmetabe.api.auth.service;

import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRedundancyCheckRequestDto;
import kr.co.datastreams.llmetabe.api.auth.exception.InappropriatePasswordException;
import kr.co.datastreams.llmetabe.api.auth.exception.PasswordMismatchException;
import kr.co.datastreams.llmetabe.api.member.dao.MemberDao;
import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;
import kr.co.datastreams.llmetabe.api.auth.exception.EmailAlreadyExistException;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        if (!signupRequestDto.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            throw new InappropriatePasswordException("비밀번호는 문자와 숫자를 모두 포함하며, 최소 8자여야 합니다.");
        }
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getConfirmPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다");
        }
        try {
            signupRequestDto.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
            memberDao.saveMemberEntity(signupRequestDto);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    @Override
    public void signupRedundancyCheck(SignupRedundancyCheckRequestDto signupRedundancyCheckRequestDto) {
        if (memberDao.hasMemberEntityByEmail(signupRedundancyCheckRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("이미 존재하는 이메일입니다.");
        }
    }
}
