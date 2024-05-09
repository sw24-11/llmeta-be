package kr.co.datastreams.llmetabe.global.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.datastreams.llmetabe.global.advice.ErrorCode;
import kr.co.datastreams.llmetabe.global.advice.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                    throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        ErrorResponse<?> body = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .code(ErrorCode.U01)
                .message(ErrorCode.U01.getDescription())
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body)));
    }
}
