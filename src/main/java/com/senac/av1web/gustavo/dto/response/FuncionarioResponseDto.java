package com.senac.av1web.gustavo.dto.response;

import com.senac.av1web.gustavo.dto.FolhaPagamentoDto;

import java.time.LocalDate;
import java.util.List;

public class FuncionarioResponseDto {
    private Integer id;
    private String matricula;
    private String nome;
    private LocalDate dataNascimento;
    private Integer status;
    private List<String> roles;
    private List<FolhaPagamentoDto> folhasPagamento;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public List<FolhaPagamentoDto> getFolhasPagamento() { return folhasPagamento; }
    public void setFolhasPagamento(List<FolhaPagamentoDto> folhasPagamento) { this.folhasPagamento = folhasPagamento; }
}