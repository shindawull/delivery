package com.sparta.delivery.global.security;

import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j(topic = "UserDetailsImpl")
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        log.info("getPassword : " + user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getUserRole();
        String authority = role.getAuthority(); // 권한

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() { // 계정만료
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정 잠금
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화
        return true;
    }
}