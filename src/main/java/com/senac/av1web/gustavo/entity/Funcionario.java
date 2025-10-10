package com.senac.av1web.gustavo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funcionario_id")
    private Integer id;

    @Column(name="funcionario_matricula")
    private String matricula;

    @Column(name = "funcionario_nome")
    private String nome;

    @Column(name = "funcionario_data_nascimento")
    private LocalDate dataNascimento;

    @Column(name= "funcionario_chave_acesso")
    private String chaveAcesso;

    @Column(name = "funcionario_status")
    private Integer status;

    @OneToMany(mappedBy = "folha_pagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private FolhaPagamento folhaPagamento;




}
