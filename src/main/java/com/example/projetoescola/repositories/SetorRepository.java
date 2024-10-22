package com.example.projetoescola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.projetoescola.models.Setor;
import java.util.List;

public interface SetorRepository extends JpaRepository<Setor, Long> {
    
    @Query("select s from Setor s left join fetch s.funcionarios")
    List<Setor> findAllWithFuncionarios();
}
