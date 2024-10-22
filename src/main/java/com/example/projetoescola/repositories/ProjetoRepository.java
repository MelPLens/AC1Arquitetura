package com.example.projetoescola.repositories;

import com.example.projetoescola.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    // Método para buscar projetos que com determinado id com seus funcionarios
    @Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.funcionarios WHERE p.id = :id")
    Optional<Projeto> findProjetoWithFuncionarios(Long id);

     // Método para buscar projetos que começam entre as datas especificadas
    List<Projeto> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);
}
