package com.login.userService.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.login.userService.models.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
//@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {

    private String authority;

    // Manually defining a no-args constructor
    public CustomGrantedAuthority() {
        // Default constructor, sets authority to null or an empty string.
        this.authority = null; // Or "" if you prefer an empty string.
    }

    public CustomGrantedAuthority(Role role){
        this.authority=role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
