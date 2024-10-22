package com.example.projetoescola.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private Long setorId; // Este campo deve existir
    private String setorNome; // Este campo deve existir
    private List<ProjetoInfoDTO> projetos; // Adicione esta linha

    @Data
    @Builder
    public static class ProjetoInfoDTO { // Classe interna ou externa, dependendo da sua preferência
        private Long id;
        private String descricao;
    }
}