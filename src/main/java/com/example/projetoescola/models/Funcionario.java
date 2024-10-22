package com.example.projetoescola.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    @JsonBackReference // Evita a serialização circular
    private Setor setor;

    @ManyToMany(cascade = CascadeType.ALL) // Inclua o cascade se necessário
    @JoinTable(
        name = "funcionario_projeto",
        joinColumns = @JoinColumn(name = "funcionario_id"),
        inverseJoinColumns = @JoinColumn(name = "projeto_id")
    )
    @JsonManagedReference // Serializa apenas os projetos
    private List<Projeto> projetos = new ArrayList<>(); // Inicialização correta

    // Método para adicionar projeto ao funcionário
    public void adicionarProjeto(Projeto projeto) {
        this.projetos.add(projeto);
        projeto.getFuncionarios().add(this); // Adiciona este funcionário na lista do projeto
    }

    // Método para remover projeto do funcionário
    public void removerProjeto(Projeto projeto) {
        this.projetos.remove(projeto);
        projeto.getFuncionarios().remove(this); // Remove este funcionário da lista do projeto
    }
}
