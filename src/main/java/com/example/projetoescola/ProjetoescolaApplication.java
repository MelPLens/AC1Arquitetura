package com.example.projetoescola;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.projetoescola.models.Setor;
import com.example.projetoescola.models.Funcionario;
import com.example.projetoescola.models.Projeto;
import com.example.projetoescola.repositories.SetorRepository;
import com.example.projetoescola.repositories.FuncionarioRepository;
import com.example.projetoescola.repositories.ProjetoRepository;

@SpringBootApplication
public class ProjetoescolaApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired FuncionarioRepository funcionarioRepository,
            @Autowired SetorRepository setorRepository,
            @Autowired ProjetoRepository projetoRepository) {
        return args -> {
            // Criação de Setores
            Setor setor1 = new Setor(null, "Administrativo", new ArrayList<>());
            Setor setor2 = new Setor(null, "Financeiro", new ArrayList<>());
            Setor setor3 = new Setor(null, "Inovação", new ArrayList<>());
            setorRepository.save(setor1);
            setorRepository.save(setor2);
            setorRepository.save(setor3);

            // Criação de Funcionários
            Funcionario funcionario1 = funcionarioRepository.save(new Funcionario(null, "Mel", setor1, new ArrayList<>()));
            Funcionario funcionario2 = funcionarioRepository.save(new Funcionario(null, "Bruno", setor1, new ArrayList<>()));
            Funcionario funcionario3 = funcionarioRepository.save(new Funcionario(null, "Joao", setor2, new ArrayList<>()));
            Funcionario funcionario4 = funcionarioRepository.save(new Funcionario(null, "Maria", setor2, new ArrayList<>()));
            Funcionario funcionario5 = funcionarioRepository.save(new Funcionario(null, "Carla", setor3, new ArrayList<>()));
            Funcionario funcionario6 = funcionarioRepository.save(new Funcionario(null, "Func. sem projeto 1", setor3, new ArrayList<>())); //
            Funcionario funcionario7 = funcionarioRepository.save(new Funcionario(null, "Func. sem projeto 2", setor3, new ArrayList<>())); //

            funcionarioRepository.save(funcionario6); // 
            funcionarioRepository.save(funcionario7); //

            // Criação de Projetos
            Projeto projeto1 = Projeto.builder()
                    .descricao("Projeto Financeiro")
                    .dataInicio(LocalDate.of(2024, 1, 1))
                    .dataFim(LocalDate.of(2024, 12, 31))
                    .build();

            Projeto projeto2 = Projeto.builder()
                    .descricao("Projeto Tecnologia")
                    .dataInicio(LocalDate.of(2024, 1, 15))
                    .dataFim(LocalDate.of(2024, 11, 30))
                    .build();

            projetoRepository.save(projeto1);
            projetoRepository.save(projeto2);

            // Associar funcionários ao Projeto 1
            for (Funcionario funcionario : List.of(funcionario1, funcionario2, funcionario3)) {
                if (funcionario != null) {
                    if (funcionario.getProjetos() == null) {
                        funcionario.setProjetos(new ArrayList<>());
                    }
                    funcionario.getProjetos().add(projeto1);
                    funcionarioRepository.save(funcionario);
                }
            }

            // Associar funcionários ao Projeto 2
            funcionario4.getProjetos().add(projeto2); // Associando funcionario 4 ao Projeto 2
            funcionario5.getProjetos().add(projeto2); // Associando funcionario 5 ao Projeto 2

            funcionarioRepository.save(funcionario4); // Salvar funcionario 4
            funcionarioRepository.save(funcionario5); // Salvar funcionario 5

            // Listar projetos
            List<Projeto> listaProjetos = projetoRepository.findAll();
            listaProjetos.stream().map(Projeto::getDescricao).forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjetoescolaApplication.class, args);
    }
}
