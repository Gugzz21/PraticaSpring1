package com.senac.av1web.gustavo.dto.response;

import com.senac.av1web.gustavo.entity.FolhaPagamento;

import java.time.LocalDate;
import java.util.List;

public class FuncionarioDtoResponse {
    private Integer id;

    private String matricula;

    private String nome;

    private LocalDate dataNascimento;

    private String chaveAcesso ;

    private Integer status;

    private List<FolhaPagamento> folhaPagamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<FolhaPagamento> getFolhaPagamento() {
        return folhaPagamento;
    }

    public void setFolhaPagamento(List<FolhaPagamento> folhaPagamento) {
        this.folhaPagamento = folhaPagamento;
    }
}
