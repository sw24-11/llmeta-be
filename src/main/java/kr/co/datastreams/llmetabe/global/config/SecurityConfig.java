package kr.co.datastreams.llmetabe.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.datastreams.llmetabe.global.session.SessionAccessDeniedHandler;
import kr.co.datastreams.llmetabe.global.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final SessionAccessDeniedHandler sessionAccessDeniedHandler;

    @Value("${spring.websecurity.debug:false}")
    boolean webSecurityDebug;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .headers(
                    (headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                            .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
                )
                .authorizeHttpRequests(
                        (authorizeRequests) ->
                                authorizeRequests
                                        .requestMatchers("/signup/**").permitAll()
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers("/logout").permitAll()
                                        .requestMatchers("/metadata/**").authenticated()
                                        .anyRequest().denyAll()
                )
                // todo: addFilterBefore
                .formLogin(
                        AbstractHttpConfigurer::disable
                )
                .cors(
                    (cors) ->
                        cors.configurationSource(corsConfigurationSource())
                )
                .logout(
                    (logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(
                                ((request, response, authentication) ->  {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    response.setStatus(200);
                                    response.setContentType("application/json");
                                    response.setCharacterEncoding("utf-8");
                                    response.getWriter().write(objectMapper.writeValueAsString(
                                        Response.ok("성공적으로 로그아웃하였습니다.")));
                                })
                        )
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(
                    (exceptionHandlingConfigurer) ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint)
                                                .accessDeniedHandler(sessionAccessDeniedHandler)
                )
        ;

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
