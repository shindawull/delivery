package com.sparta.delivery.menu.service;

import com.sparta.delivery.menu.dto.request.CreateMenuRequest;
import com.sparta.delivery.menu.dto.response.MenuResponse;
import com.sparta.delivery.menu.entity.Menu;
import com.sparta.delivery.menu.repository.MenuRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional
    public MenuResponse createMenu(Long loginUserId, CreateMenuRequest request) {
        // owner 만 등록 가능 : 회원 유형 확인
        // TODO: token에서 누구껀지 확인
        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 회원입니다."));

        if (user.getUserRole() != UserRole.OWNER)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장님만 메뉴를 등록할 수 있습니다.");

        // 메뉴 담기
        Menu menu = new Menu(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                user
        );
        return MenuResponse.form(menuRepository.save(menu));
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> getAllMenu() {
        return menuRepository.findAllByDeletedFalse();
    }

    @Transactional(readOnly = true)
    public MenuResponse getMenu(Long id) {
        Menu getMenu = menuRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다."));
        return MenuResponse.form(getMenu);
    }
}

