package com.sparta.delivery.global.jwt;

import com.sparta.delivery.global.security.UserDetailsImpl;
import com.sparta.delivery.user.dto.request.LoginRequest;
import com.sparta.delivery.user.entity.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter : 로그인 시도");
        try {
            LoginRequest requestDto = new tools.jackson.databind.ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            Authentication result =
                    getAuthenticationManager().authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    requestDto.getUsername(),
                                    requestDto.getPassword()
                            )
                    );
            log.info("JwtAuthenticationFilter : 인증 매니저 호출 성공");
            return result;
        } catch (AuthenticationException e) {
            log.error("인증 실패 원인: {}", e.getClass().getName());
            throw e;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("1. 로그인 성공 및 JWT 생성 : 인증 성공 메서드 진입");
        log.info("2. principal 확인 : {}", authResult.getPrincipal());
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();
        log.info("3. UserDetailsImpl 변환 완료 ");
        String username = user.getUsername();
        log.info("username 조회 완료: {}", username);
        UserRole role = user.getUser().getUserRole();
        log.info("5. userRole 조회 완료: {}", role);

        String token = jwtUtil.createToken(username, role);
        log.info("6. JWT 생성 완료");

        jwtUtil.addJwtToCookie(token, response);
        log.info("7. 쿠키 저장 완료");
        /*UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserRole();
        log.info("successfulAuthentication : " + username + " " + role);

        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);*/
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.error("로그인 실패 원인: {}", failed.getClass().getName());
        log.error("로그인 실패 메시지: {}", failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}