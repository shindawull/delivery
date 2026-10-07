package com.sparta.delivery.user.service;

import com.sparta.delivery.user.dto.request.SignupRequest;
import com.sparta.delivery.user.dto.response.UserResponse;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserType;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUp(SignupRequest request) {
        // 아이디 중복 확인
        Optional<User> checkUserName = userRepository.existsByUsername(request.getUsername());
        if (checkUserName.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        // 회원 권한 확인
        UserType userType = request.getUserType();
        if (userType != UserType.CUSTOMER && userType != UserType.OWNER)
            throw new IllegalArgumentException("회원 유형을 확인해주세요. (CUSTOMER or OWNER)");

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getUserType()
        );
        return UserResponse.form(userRepository.save(user));
    }
}
