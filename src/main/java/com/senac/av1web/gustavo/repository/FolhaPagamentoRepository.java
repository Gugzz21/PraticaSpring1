package com.senac.av1web.gustavo.repository;

import com.senac.av1web.gustavo.entity.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Integer> {
}
