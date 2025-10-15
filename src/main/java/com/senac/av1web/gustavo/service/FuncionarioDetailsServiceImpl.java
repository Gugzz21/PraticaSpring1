package com.senac.av1web.gustavo.service;

import com.senac.av1web.gustavo.entity.Funcionario;
import com.senac.av1web.gustavo.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado com a matrícula: " + matricula));
        return new FuncionarioDetailsImpl(funcionario);
    }
}