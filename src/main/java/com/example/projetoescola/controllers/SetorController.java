package com.example.projetoescola.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoescola.models.Setor;
import com.example.projetoescola.services.SetorService;

import java.util.List;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    @Autowired
    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    // Endpoint para listar setores com funcionários
    @GetMapping("/ComFuncionarios")
    public ResponseEntity<List<Setor>> listarSetoresComFuncionarios() {
        List<Setor> setores = setorService.listarSetoresComFuncionarios();
        return ResponseEntity.ok(setores);
    }
}
