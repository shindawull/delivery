package com.sparta.delivery.global.filter;

import com.sparta.delivery.global.security.JwtUtil;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "JwtAuthenticationFilter")
@Component
@Order(2)
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url)
                && url.startsWith("/user") || url.startsWith("/css") || url.startsWith("/js")) {
            // 회원가입, 로그인 관련 API는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행, 토근 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 subString
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims claimsInfo = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(claimsInfo.getSubject())
                        .orElseThrow(() -> new NullPointerException("Not Found User"));

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter로 이동

            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }
}
