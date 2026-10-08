package com.sparta.delivery.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8)
    private String password;
}
