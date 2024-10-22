package com.example.projetoescola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoescola.models.Setor;
import com.example.projetoescola.repositories.SetorRepository;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    @Autowired
    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public List<Setor> listarSetoresComFuncionarios() {
        return setorRepository.findAllWithFuncionarios();
    }
}
