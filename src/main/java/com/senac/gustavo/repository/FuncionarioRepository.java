package com.senac.gustavo.repository;

import com.senac.gustavo.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    <T> ScopedValue<T> findByMatricula(String username);
}
