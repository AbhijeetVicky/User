package com.login.userService.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonDeserialize
public class Role extends BaseModel{
    private String name;

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
}
