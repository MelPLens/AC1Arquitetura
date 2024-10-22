package com.example.projetoescola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoescola.services.FuncionarioService;
import com.example.projetoescola.dtos.FuncionarioDTO;
import com.example.projetoescola.dtos.DadosProjetoDTO;
import com.example.projetoescola.exceptions.RegraNegocioException;
import com.example.projetoescola.models.Funcionario;
import com.example.projetoescola.models.Projeto;
import com.example.projetoescola.models.Setor;
import com.example.projetoescola.repositories.FuncionarioRepository;
import com.example.projetoescola.repositories.SetorRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;

    @Autowired
    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, SetorRepository setorRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
    }

    @Override
    public void inserir(FuncionarioDTO funcionarioDTO) {
        if (funcionarioDTO.getNome() == null || funcionarioDTO.getNome().isEmpty()) {
            throw new RegraNegocioException("Nome do funcionário não pode ser nulo ou vazio.");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDTO.getNome());

        if (funcionarioDTO.getSetorId() != null) {
            Setor setor = setorRepository.findById(funcionarioDTO.getSetorId())
                    .orElseThrow(() -> new RegraNegocioException("Setor não encontrado"));
            funcionario.setSetor(setor);
        }

        funcionarioRepository.save(funcionario);
    }

    @Override
    public List<FuncionarioDTO> listarTodos() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        return funcionarios.stream().map(c -> FuncionarioDTO.builder()
                .id(c.getId())
                .nome(c.getNome())
                .setorId(c.getSetor() != null ? c.getSetor().getId() : 0)
                .setorNome(c.getSetor() != null ? c.getSetor().getNome() : "")
                .build()).collect(Collectors.toList());
    }

    @Override
    public FuncionarioDTO buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Funcionário não encontrado"));

        return FuncionarioDTO.builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                .setorId(funcionario.getSetor() != null ? funcionario.getSetor().getId() : 0)
                .setorNome(funcionario.getSetor() != null ? funcionario.getSetor().getNome() : "")
                .build();
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    @Override
    public void editar(Long id, FuncionarioDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Código usuário não encontrado."));

        if (dto.getSetorId() == null) {
            throw new RegraNegocioException("ID do setor não pode ser nulo.");
        }

        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new RegraNegocioException("Setor não encontrado."));

        funcionario.setNome(dto.getNome());
        funcionario.setSetor(setor);

        funcionarioRepository.save(funcionario);
    }

    @Override
    public List<DadosProjetoDTO> buscarProjetosPorFuncionario(Long idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RegraNegocioException("Funcionário não encontrado"));
List<Projeto> projetos = funcionarioRepository.findProjetosByFuncionarioId(idFuncionario);

    return projetos.stream()
            .map(projeto -> DadosProjetoDTO.builder()
                    .id(projeto.getId())
                    .descricao(projeto.getDescricao())
                    .dataInicio(projeto.getDataInicio())
                    .dataFim(projeto.getDataFim())
                    .funcionarios(projeto.getFuncionarios().stream()
                            .map(func -> FuncionarioDTO.builder()
                                    .id(func.getId())
                                    .nome(func.getNome())
                                    .setorId(func.getSetor() != null ? func.getSetor().getId() : null) // Adicione isso
                                    .setorNome(func.getSetor() != null ? func.getSetor().getNome() : null) // E isso
                                    .build())
                            .collect(Collectors.toList())) // Preencha a lista de funcionários
                    .build())
            .collect(Collectors.toList());
}

@Override
public List<FuncionarioDTO> listarTodosFuncionarios() {
    List<Funcionario> funcionarios = funcionarioRepository.findAll(); // Busca todos os funcionários

    return funcionarios.stream().map(funcionario -> FuncionarioDTO.builder()
            .id(funcionario.getId())
            .nome(funcionario.getNome())
            .setorId(funcionario.getSetor() != null ? funcionario.getSetor().getId() : null)
            .setorNome(funcionario.getSetor() != null ? funcionario.getSetor().getNome() : null)
            .projetos(funcionario.getProjetos().stream()
                    .map(projeto -> FuncionarioDTO.ProjetoInfoDTO.builder() // Mapeia para o DTO de projeto
                            .id(projeto.getId())
                            .descricao(projeto.getDescricao())
                            .build())
                    .collect(Collectors.toList())) // Coleta os projetos em uma lista
            .build())
            .collect(Collectors.toList()); // Converte para DTO
}
}
