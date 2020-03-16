package com.scnu.ribbon.dto;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}