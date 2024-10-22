package com.example.projetoescola.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoescola.dtos.ProjetoDTO;
import com.example.projetoescola.exceptions.RegraNegocioException;
import com.example.projetoescola.dtos.DadosProjetoDTO;
import com.example.projetoescola.dtos.FuncionarioDTO;
import com.example.projetoescola.dtos.ProjetoComFuncionariosDTO;
import com.example.projetoescola.models.Funcionario;
import com.example.projetoescola.models.Projeto;
import com.example.projetoescola.repositories.ProjetoRepository;
import com.example.projetoescola.repositories.FuncionarioRepository;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public ProjetoService(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    // Inserir um novo projeto
    public void inserir(ProjetoDTO projetoDTO) {
        Projeto projeto = new Projeto();
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto.setDataInicio(projetoDTO.getDataInicio());
        projeto.setDataFim(projetoDTO.getDataFim());
        projetoRepository.save(projeto);
    }

    // Buscar projetos por data
    public List<ProjetoComFuncionariosDTO> buscarProjetosPorData(LocalDate dataInicio, LocalDate dataFim) {
        List<Projeto> projetos = projetoRepository.findByDataInicioBetween(dataInicio, dataFim);
        
        return projetos.stream().map(projeto -> 
            ProjetoComFuncionariosDTO.builder()
                .id(projeto.getId())
                .descricao(projeto.getDescricao())
                .dataInicio(projeto.getDataInicio())
                .dataFim(projeto.getDataFim())
                .funcionarios(projeto.getFuncionarios().stream()
                    .map(funcionario -> FuncionarioDTO.builder()
                        .id(funcionario.getId())
                        .nome(funcionario.getNome())
                        .build())
                    .collect(Collectors.toList()))
                .build())
        .collect(Collectors.toList());
    }

    // Buscar projeto com funcionários
   public DadosProjetoDTO buscarProjetoComFuncionarios(Long id) {
    Projeto projeto = projetoRepository.findById(id)
            .orElseThrow(() -> new RegraNegocioException("Projeto não encontrado"));

    return DadosProjetoDTO.builder()
            .id(projeto.getId())
            .descricao(projeto.getDescricao())
            .dataInicio(projeto.getDataInicio())
            .dataFim(projeto.getDataFim())
            .funcionarios(projeto.getFuncionarios().stream()
                    .map(funcionario -> FuncionarioDTO.builder()
                            .id(funcionario.getId())
                            .nome(funcionario.getNome())
                            .setorId(funcionario.getSetor() != null ? funcionario.getSetor().getId() : null)
                            .setorNome(funcionario.getSetor() != null ? funcionario.getSetor().getNome() : null)
                            .build())
                    .collect(Collectors.toList()))
            .build();
}


    // Vincular funcionário a um projeto
    public void vincularFuncionario(Long idProjeto, Long idFuncionario) {
        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new RegraNegocioException("Projeto não encontrado"));
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RegraNegocioException("Funcionário não encontrado"));
        
        // Adiciona o funcionário ao projeto
        projeto.getFuncionarios().add(funcionario);
        
        // Adiciona o projeto ao funcionário
        funcionario.getProjetos().add(projeto);
        
        // Salva as alterações
        projetoRepository.save(projeto);
        funcionarioRepository.save(funcionario); // Também salve o funcionário se necessário
    }
    
    
}
