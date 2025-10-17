package com.senac.gustavo.dto;


import com.senac.gustavo.entity.RoleName;

public record CreateUserDto(

        String matricula,
        String chaveAcesso,
        RoleName role

) {
}