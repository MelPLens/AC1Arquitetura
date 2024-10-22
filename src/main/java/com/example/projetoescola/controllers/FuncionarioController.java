package com.example.projetoescola.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.projetoescola.dtos.FuncionarioDTO;
import com.example.projetoescola.dtos.DadosProjetoDTO;
import com.example.projetoescola.services.FuncionarioService;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestBody FuncionarioDTO funcionario) {
        funcionarioService.inserir(funcionario); // Certifique-se de que o método 'inserir' esteja correto
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idFuncionario}/projetos")
    public ResponseEntity<List<DadosProjetoDTO>> buscarProjetos(@PathVariable Long idFuncionario) {
        List<DadosProjetoDTO> projetos = funcionarioService.buscarProjetosPorFuncionario(idFuncionario); // Certifique-se de que este método está implementado
        return ResponseEntity.ok(projetos);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> listarTodos() {
        List<FuncionarioDTO> funcionarios = funcionarioService.listarTodosFuncionarios();
        return ResponseEntity.ok(funcionarios); // Retorna a lista de funcionários
    }
}
