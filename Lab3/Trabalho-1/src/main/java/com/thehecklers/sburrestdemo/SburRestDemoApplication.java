package com.thehecklers.sburrestdemo;

import com.thehecklers.sburrestdemo.model.Aluno;
import com.thehecklers.sburrestdemo.service.AlunoService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SburRestDemoApplication {

    private final AlunoService alunoService;

    public SburRestDemoApplication(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SburRestDemoApplication.class, args);
    }

    // Método para popular dados iniciais quando a aplicação subir
    @PostConstruct
    private void loadData() {
        if (alunoService.count() == 0) {
            alunoService.saveAll(List.of(
                    new Aluno("Ana Silva", "ana.silva@faculdade.edu", "Engenharia de Software"),
                    new Aluno("Bruno Santos", "bruno.santos@faculdade.edu", "Ciência da Computação"),
                    new Aluno("Carla Oliveira", "carla.oliveira@faculdade.edu", "Sistemas de Informação"),
                    new Aluno("Diego Ferreira", "diego.ferreira@faculdade.edu", "Engenharia de Software")
            ));
        }
    }
}