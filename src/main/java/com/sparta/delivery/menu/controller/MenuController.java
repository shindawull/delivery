package com.sparta.delivery.menu.controller;

import com.sparta.delivery.menu.dto.request.CreateMenuRequest;
import com.sparta.delivery.menu.dto.response.MenuResponse;
import com.sparta.delivery.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    //등록 (owner)
    @PostMapping("")
    public ResponseEntity<MenuResponse> createMenu(@RequestHeader("loginUserId") Long loginUserId,
                                                   @Valid @RequestBody CreateMenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createMenu(loginUserId, request));
    }

    @GetMapping("")
    public ResponseEntity<List<MenuResponse>> getAllMenu() {
        List<MenuResponse> result = menuService.getAllMenu();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //TODO: 단건 조회(상세조회)

    //TODO: 수정 (owner)

    //TODO: 삭제(soft delete, owner)

}
