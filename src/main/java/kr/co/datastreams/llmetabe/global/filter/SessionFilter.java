package kr.co.datastreams.llmetabe.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import kr.co.datastreams.llmetabe.api.member.repository.MemberRepository;
import kr.co.datastreams.llmetabe.global.session.LoginRequestDto;
import kr.co.datastreams.llmetabe.global.session.SessionAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;

public class SessionFilter extends AbstractAuthenticationProcessingFilter {

    public SessionFilter(final String defaultFilterProcessesUrl, final AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(defaultFilterProcessesUrl, authenticationManager);
        setAuthenticationSuccessHandler(new SessionAuthenticationSuccessHandler(memberRepository));
        HttpSessionSecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();
        httpSessionSecurityContextRepository.setAllowSessionCreation(true);
        setSecurityContextRepository(httpSessionSecurityContextRepository);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication Method Not Supported: " + request.getMethod());
        }
        return this.getAuthenticationManager().authenticate(getAuthRequest(request));
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String username = null;
        String password = null;

        if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            try {
                ServletInputStream inputStream = request.getInputStream();
                LoginRequestDto loginRequestDto = objectMapper.readValue(
                    StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8),
                    LoginRequestDto.class);
                username = loginRequestDto.getEmail();
                password = loginRequestDto.getPassword();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            username = obtainEmail(request);
            password = obtainPassword(request);
        }

        username = (username != null) ? username.trim() : "";
        password = (password != null) ? password.trim() : "";

        return new UsernamePasswordAuthenticationToken(username, password);
    }

    private String obtainEmail(HttpServletRequest request) {
        return request.getParameter("email");
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }
}
