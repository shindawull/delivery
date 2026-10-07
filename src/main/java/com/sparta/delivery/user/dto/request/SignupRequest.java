package com.sparta.delivery.user.dto.request;

import com.sparta.delivery.user.entity.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotNull
    private UserType userType;

    public SignupRequest(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
}

