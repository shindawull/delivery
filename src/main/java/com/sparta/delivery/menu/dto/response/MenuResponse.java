package com.sparta.delivery.menu.dto.response;


import com.sparta.delivery.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MenuResponse {
    private String name;
    private int price;
    private String description; //설명
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static MenuResponse form(Menu menu) {
        return MenuResponse.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .createdDate(menu.getCreatedDate())
                .modifiedDate(menu.getModifiedDate())
                .build();
    }
}
