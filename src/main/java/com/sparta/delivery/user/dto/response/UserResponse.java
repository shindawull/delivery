package com.sparta.delivery.user.dto.response;

import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private UserRole userRole;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static UserResponse form(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .userRole(user.getUserRole())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .build();
    }
}
