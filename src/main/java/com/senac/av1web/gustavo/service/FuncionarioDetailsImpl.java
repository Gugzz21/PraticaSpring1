package com.senac.av1web.gustavo.service;

import com.senac.av1web.gustavo.entity.Funcionario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class FuncionarioDetailsImpl implements UserDetails {
    private Funcionario funcionario;

    public FuncionarioDetailsImpl(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return funcionario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return funcionario.getChaveAcesso();
    }

    @Override
    public String getUsername() {
        return funcionario.getMatricula();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return funcionario.getStatus() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return funcionario.getStatus() == 1;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}