package kr.co.datastreams.llmetabe.global.session;

import kr.co.datastreams.llmetabe.api.member.domain.MemberEntity;
import kr.co.datastreams.llmetabe.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        MemberEntity user = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .build();
    }
}
