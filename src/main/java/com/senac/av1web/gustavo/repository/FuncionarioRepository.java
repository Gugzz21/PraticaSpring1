package com.senac.av1web.gustavo.repository;

import com.senac.av1web.gustavo.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByMatricula(String matricula);

    @Query("SELECT f FROM Funcionario f WHERE f.status = 1")
    List<Funcionario> findByStatusAtivo();

    @Query("SELECT f FROM Funcionario f WHERE f.id = :id AND f.status = 1")
    Optional<Funcionario> findByIdAndStatusAtivo(@Param("id") Integer id);

    @Query("SELECT f FROM Funcionario f WHERE f.status = 1")
    List<Funcionario> findAllAtivos();

    @Query("SELECT COUNT(f) > 0 FROM Funcionario f WHERE f.matricula = :matricula AND f.id != :id")
    boolean existsByMatriculaAndIdNot(@Param("matricula") String matricula, @Param("id") Integer id);
}