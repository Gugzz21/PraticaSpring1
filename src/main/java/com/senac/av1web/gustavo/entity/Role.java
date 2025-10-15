package com.senac.av1web.gustavo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_nome")
    private RoleName nome;

    // Construtores, getters e setters
    public Role() {}

    public Role(RoleName nome) {
        this.nome = nome;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public RoleName getNome() { return nome; }
    public void setNome(RoleName nome) { this.nome = nome; }
}
