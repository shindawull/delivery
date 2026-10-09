package com.sparta.delivery.menu.controller;

import com.sparta.delivery.menu.dto.request.CreateMenuRequest;
import com.sparta.delivery.menu.dto.response.MenuResponse;
import com.sparta.delivery.menu.service.MenuService;
import com.sparta.delivery.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("")
    public ResponseEntity<MenuResponse> createMenu(HttpServletRequest req, @Valid @RequestBody CreateMenuRequest request) {
        User user = (User) req.getAttribute("user");
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createMenu(user, request));
    }

    @GetMapping("")
    public ResponseEntity<List<MenuResponse>> getAllMenu() {
        List<MenuResponse> result = menuService.getAllMenu();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(menuService.getMenu(id));
    }

    //TODO: 수정 (owner)

    //TODO: 삭제(soft delete, owner)


}
