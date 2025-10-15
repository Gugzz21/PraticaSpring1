package com.senac.av1web.gustavo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funcionario_id")
    private Integer id;

    @Column(name = "funcionario_matricula", unique = true)
    private String matricula;

    @Column(name = "funcionario_nome")
    private String nome;

    @Column(name = "funcionario_data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "funcionario_chave_acesso")
    private String chaveAcesso;

    @Column(name = "funcionario_status")
    private Integer status; // 1 = Ativo, 0 = Bloqueado

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "funcionario_role",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolhaPagamento> folhasPagamento;

    // Construtores
    public Funcionario() {}

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    
    public String getChaveAcesso() { return chaveAcesso; }
    public void setChaveAcesso(String chaveAcesso) { this.chaveAcesso = chaveAcesso; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }
    
    public List<FolhaPagamento> getFolhasPagamento() { return folhasPagamento; }
    public void setFolhasPagamento(List<FolhaPagamento> folhasPagamento) { 
        this.folhasPagamento = folhasPagamento; 
    }

    // Método auxiliar para verificar se é gerente
    public boolean isGerente() {
        return roles.stream().anyMatch(role -> role.getNome() == RoleName.GERENTE);
    }
}