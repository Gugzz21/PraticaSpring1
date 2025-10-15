package com.senac.av1web.gustavo.repository;

import com.senac.av1web.gustavo.entity.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Integer> {
    
    List<FolhaPagamento> findByFuncionarioId(Integer funcionarioId);
    
    @Query("SELECT fp FROM FolhaPagamento fp WHERE fp.funcionario.id = :funcionarioId AND fp.funcionario.status = 1")
    List<FolhaPagamento> findByFuncionarioIdAndAtivo(@Param("funcionarioId") Integer funcionarioId);
}