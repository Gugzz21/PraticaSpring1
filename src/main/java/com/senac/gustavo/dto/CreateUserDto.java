package com.senac.gustavo.dto;

import com.example.demo.entity.RoleName;

public record CreateUserDto(

        String email,
        String password,
        RoleName role

) {
}