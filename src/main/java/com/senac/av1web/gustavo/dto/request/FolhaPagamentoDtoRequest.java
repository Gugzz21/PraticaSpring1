package com.senac.av1web.gustavo.dto.request;

public class FolhaPagamentoDtoRequest {
    private Integer mes;
    private Integer ano;
    private Integer salario;
    private Integer funcionarioId;

    // Getters e Setters
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Integer getSalario() { return salario; }
    public void setSalario(Integer salario) { this.salario = salario; }

    public Integer getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Integer funcionarioId) { this.funcionarioId = funcionarioId; }
}