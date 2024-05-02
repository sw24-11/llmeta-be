package kr.co.datastreams.llmetabe.api.auth.service;

import kr.co.datastreams.llmetabe.api.auth.dao.MemberDao;
import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;
import kr.co.datastreams.llmetabe.api.exception.EmailAlreadyExistException;
import kr.co.datastreams.llmetabe.api.member.repository.MemberRepository;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberDao memberDao;
    private final MemberRepository memberRepository;

    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        try {
            memberDao.saveMemberEntity(signupRequestDto);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    @Override
    public void signupRedundancyCheck(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new EmailAlreadyExistException("이미 존재하는 이메일입니다.");
                });
    }
}
