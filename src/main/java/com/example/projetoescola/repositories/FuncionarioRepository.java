package com.example.projetoescola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.projetoescola.models.Funcionario;
import com.example.projetoescola.models.Projeto; // Importar a classe Projeto
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
    @Query("select f.projetos from Funcionario f where f.id = :id")
    List<Projeto> findProjetosByFuncionarioId(@Param("id") Long id);
    
}
