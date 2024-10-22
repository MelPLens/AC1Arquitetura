package com.example.projetoescola.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    @ManyToMany(mappedBy = "projetos", cascade = CascadeType.ALL)
    @JsonBackReference // Adicione aqui
    private List<Funcionario> funcionarios = new ArrayList<>(); // Inicialização correta

    // Método para adicionar funcionário ao projeto
    public void adicionarFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.getProjetos().add(this); // Adiciona este projeto na lista do funcionário
    }

    // Método para remover funcionário do projeto
    public void removerFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.getProjetos().remove(this); // Remove este projeto da lista do funcionário
    }
}
