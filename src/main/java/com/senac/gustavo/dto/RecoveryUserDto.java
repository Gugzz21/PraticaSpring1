package com.senac.gustavo.dto;



import com.senac.gustavo.entity.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String matricula,
        List<Role> roles

) {
}