package com.senac.gustavo.dto;

import com.example.demo.entity.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}