package com.sparta.delivery.user.dto.request;

import com.sparta.delivery.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해주세요.")
    private String password;
    @NotNull(message = "회원 유형을 선택해주세요. ( CUSTOMER 또는 OWNER )")
    private UserRole userRole;

    public SignupRequest(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}

