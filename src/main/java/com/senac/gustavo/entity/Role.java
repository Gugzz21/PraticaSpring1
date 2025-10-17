package com.senac.gustavo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_nome")
    @Enumerated(EnumType.STRING)
    private RoleName nome;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleName getNome() {
        return nome;
    }

    public void setNome(RoleName nome) {
        this.nome = nome;
    }
}
