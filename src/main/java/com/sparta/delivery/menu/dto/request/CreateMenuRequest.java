package com.sparta.delivery.menu.dto.request;

import com.sparta.delivery.user.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
public class CreateMenuRequest {
    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String name;
    @Min(value = 1, message = "가격은 1원 이상이어야 합니다.")
    private int price;
    private String description;
}
