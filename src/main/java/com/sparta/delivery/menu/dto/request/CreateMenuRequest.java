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
    @NotBlank
    private String name;
    @Min(1)
    private int price;
    private String description; //설명
}
