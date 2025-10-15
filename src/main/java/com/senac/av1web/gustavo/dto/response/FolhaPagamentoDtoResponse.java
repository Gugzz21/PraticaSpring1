package com.senac.av1web.gustavo.dto.response;

public class FolhaPagamentoDtoResponse {
    private Integer id;
    private Integer mes;
    private Integer ano;
    private Integer salario;
    private Integer funcionarioId;
    private String funcionarioNome;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Integer getSalario() { return salario; }
    public void setSalario(Integer salario) { this.salario = salario; }

    public Integer getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Integer funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getFuncionarioNome() { return funcionarioNome; }
    public void setFuncionarioNome(String funcionarioNome) { this.funcionarioNome = funcionarioNome; }
}