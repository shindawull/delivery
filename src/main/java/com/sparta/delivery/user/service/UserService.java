package com.sparta.delivery.user.service;

import com.sparta.delivery.global.security.JwtUtil;
import com.sparta.delivery.user.dto.request.LoginRequest;
import com.sparta.delivery.user.dto.request.SignupRequest;
import com.sparta.delivery.user.dto.response.UserResponse;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponse signUp(SignupRequest request) {
        // 아이디 중복 확인
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        // 회원 유형 확인
        UserRole userRole = request.getUserRole();
        if (userRole != UserRole.CUSTOMER && userRole != UserRole.OWNER)
            throw new IllegalArgumentException("회원 유형을 확인해주세요. (CUSTOMER or OWNER)");

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getUserRole()
        );
        return UserResponse.form(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request, HttpServletResponse res) {
        // 회원 존재 유무 확인
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 비밀번호 확인
        boolean checkPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!checkPassword) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(), user.getUserRole());
        jwtUtil.addJwtToCookie(token,  res);

        return user.getUsername();
    }
}
