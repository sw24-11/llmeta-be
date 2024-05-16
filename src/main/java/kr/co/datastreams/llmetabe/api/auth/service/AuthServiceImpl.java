package kr.co.datastreams.llmetabe.api.auth.service;

import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRedundancyCheckRequestDto;
import kr.co.datastreams.llmetabe.api.member.dao.MemberDao;
import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;
import kr.co.datastreams.llmetabe.api.exception.EmailAlreadyExistException;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import kr.co.datastreams.llmetabe.global.exception.NoSearchResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberDao memberDao;

    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        try {
            memberDao.saveMemberEntity(signupRequestDto);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    @Override
    public void signupRedundancyCheck(SignupRedundancyCheckRequestDto signupRedundancyCheckRequestDto) {
        try {
            memberDao.getMemberEntityByEmail(signupRedundancyCheckRequestDto.getEmail());
            throw new EmailAlreadyExistException("이미 존재하는 이메일입니다.");
        } catch (NoSearchResultException ignored) {
        }
    }
}
