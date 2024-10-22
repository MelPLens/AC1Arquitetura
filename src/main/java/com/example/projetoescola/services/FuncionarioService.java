package com.example.projetoescola.services;

import com.example.projetoescola.dtos.FuncionarioDTO;
import com.example.projetoescola.dtos.DadosProjetoDTO;

import java.util.List;

public interface FuncionarioService {

    void inserir(FuncionarioDTO funcionarioDTO);

    List<FuncionarioDTO> listarTodos();

    FuncionarioDTO buscarPorId(Long id);

    void excluir(Long id);

    void editar(Long id, FuncionarioDTO dto);

    List<DadosProjetoDTO> buscarProjetosPorFuncionario(Long idFuncionario);

    List<FuncionarioDTO> listarTodosFuncionarios(); // Adicione este método
}
