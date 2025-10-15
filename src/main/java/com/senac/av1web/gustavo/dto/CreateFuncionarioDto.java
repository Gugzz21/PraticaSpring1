package com.senac.av1web.gustavo.dto;

import java.time.LocalDate;

public record CreateFuncionarioDto(
    String matricula,
    String nome,
    LocalDate dataNascimento,
    String chaveAcesso,
    String role
) {}