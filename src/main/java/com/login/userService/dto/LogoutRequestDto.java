package com.login.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutRequestDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}