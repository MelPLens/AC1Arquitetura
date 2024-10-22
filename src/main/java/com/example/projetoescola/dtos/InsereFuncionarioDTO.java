package com.example.projetoescola.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsereFuncionarioDTO {
    private String nome;
    private Long setorId;
}
