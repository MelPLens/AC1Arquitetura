package com.example.projetoescola.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.projetoescola.dtos.ProjetoDTO;
import com.example.projetoescola.models.Projeto;
import com.example.projetoescola.dtos.DadosProjetoDTO;
import com.example.projetoescola.dtos.ProjetoComFuncionariosDTO;
import com.example.projetoescola.services.ProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    @Autowired
    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    // Adicionar um novo projeto
    @PostMapping
    public ResponseEntity<Void> adicionar(@RequestBody ProjetoDTO projeto) {
        projetoService.inserir(projeto); // Chama o método de inserção do serviço
        return ResponseEntity.ok().build();
    }

    // Buscar projeto por ID e incluir funcionários vinculados
    @GetMapping("/{id}")
    public ResponseEntity<DadosProjetoDTO> buscarProjetoPorId(@PathVariable Long id) {
        DadosProjetoDTO dadosProjeto = projetoService.buscarProjetoComFuncionarios(id); // Atualize para usar o método correto
        return ResponseEntity.ok(dadosProjeto);
    }

    // Vincular funcionário a um projeto
    @PostMapping("/{idProjeto}/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> vincularFuncionario(@PathVariable Long idProjeto, @PathVariable Long idFuncionario) {
        projetoService.vincularFuncionario(idProjeto, idFuncionario); // Chama o método para vincular funcionário
        return ResponseEntity.ok().build();
    }

     // Método para buscar projetos por data de início
    @GetMapping("/buscaPorData")
public ResponseEntity<List<ProjetoComFuncionariosDTO>> buscarPorData(
        @RequestParam LocalDate dataInicio,
        @RequestParam LocalDate dataFim) {
    List<ProjetoComFuncionariosDTO> projetos = projetoService.buscarProjetosPorData(dataInicio, dataFim);
    return ResponseEntity.ok(projetos);
}

}
