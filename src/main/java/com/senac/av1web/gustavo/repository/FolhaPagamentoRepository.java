package com.senac.av1web.gustavo.repository;

import com.senac.av1web.gustavo.entity.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Integer> {

    List<FolhaPagamento> findByFuncionarioId(Integer funcionarioId);

    @Query("SELECT fp FROM FolhaPagamento fp WHERE fp.funcionario.id = :funcionarioId AND fp.funcionario.status = 1")
    List<FolhaPagamento> findByFuncionarioIdAndAtivo(@Param("funcionarioId") Integer funcionarioId);

    @Query("SELECT fp FROM FolhaPagamento fp WHERE fp.funcionario.id = :funcionarioId AND fp.mes = :mes AND fp.ano = :ano")
    Optional<FolhaPagamento> findByFuncionarioIdAndMesAndAno(
            @Param("funcionarioId") Integer funcionarioId,
            @Param("mes") Integer mes,
            @Param("ano") Integer ano);

    @Query("SELECT fp FROM FolhaPagamento fp WHERE fp.funcionario.id = :funcionarioId AND fp.mes = :mes AND fp.ano = :ano AND fp.id != :id")
    Optional<FolhaPagamento> findByFuncionarioIdAndMesAndAnoAndIdNot(
            @Param("funcionarioId") Integer funcionarioId,
            @Param("mes") Integer mes,
            @Param("ano") Integer ano,
            @Param("id") Integer id);
}