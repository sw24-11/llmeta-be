package kr.co.datastreams.llmetabe.api.auth.dao;

import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;
import kr.co.datastreams.llmetabe.api.member.domain.MemberEntity;
import kr.co.datastreams.llmetabe.api.member.repository.MemberRepository;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final MemberRepository memberRepository;

    public void saveMemberEntity(SignupRequestDto signupRequestDto) {
        try {
            memberRepository.save(new MemberEntity(signupRequestDto));
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }
}
