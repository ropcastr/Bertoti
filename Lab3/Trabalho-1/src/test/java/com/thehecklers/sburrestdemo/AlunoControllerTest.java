package com.thehecklers.sburrestdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thehecklers.sburrestdemo.model.Aluno;
import com.thehecklers.sburrestdemo.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        alunoRepository.deleteAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoAlunosExist() throws Exception {
        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldCreateNewAluno() throws Exception {
        Aluno newAluno = new Aluno("Mariana Lima", "mariana.lima@faculdade.edu", "Engenharia de Software");

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAluno)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Mariana Lima")))
                .andExpect(jsonPath("$.email", is("mariana.lima@faculdade.edu")))
                .andExpect(jsonPath("$.curso", is("Engenharia de Software")));
    }

    @Test
    void shouldGetAlunoByIdWhenExists() throws Exception {
        Aluno saved = alunoRepository.save(new Aluno("Lucas Rocha", "lucas.rocha@faculdade.edu", "Ciência da Computação"));

        mockMvc.perform(get("/alunos/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId())))
                .andExpect(jsonPath("$.nome", is("Lucas Rocha")))
                .andExpect(jsonPath("$.email", is("lucas.rocha@faculdade.edu")))
                .andExpect(jsonPath("$.curso", is("Ciência da Computação")));
    }

    @Test
    void shouldReturn404WhenAlunoDoesNotExist() throws Exception {
        mockMvc.perform(get("/alunos/non-existing-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateExistingAluno() throws Exception {
        Aluno saved = alunoRepository.save(new Aluno("Renata Mendes", "renata.mendes@faculdade.edu", "Sistemas de Informação"));
        Aluno updated = new Aluno(saved.getId(), "Renata Mendes Souza", "renata.souza@faculdade.edu", "Engenharia de Software");

        mockMvc.perform(put("/alunos/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId())))
                .andExpect(jsonPath("$.nome", is("Renata Mendes Souza")))
                .andExpect(jsonPath("$.email", is("renata.souza@faculdade.edu")))
                .andExpect(jsonPath("$.curso", is("Engenharia de Software")));
    }

    @Test
    void shouldCreateAlunoOnPutWhenNotExists() throws Exception {
        Aluno newAluno = new Aluno("aluno-custom-123", "Thiago Alves", "thiago.alves@faculdade.edu", "Banco de Dados");

        mockMvc.perform(put("/alunos/aluno-custom-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAluno)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("aluno-custom-123")))
                .andExpect(jsonPath("$.nome", is("Thiago Alves")))
                .andExpect(jsonPath("$.email", is("thiago.alves@faculdade.edu")))
                .andExpect(jsonPath("$.curso", is("Banco de Dados")));
    }

    @Test
    void shouldDeleteAlunoSuccessfully() throws Exception {
        Aluno saved = alunoRepository.save(new Aluno("Aluno Teste Exclusao", "teste@faculdade.edu", "Engenharia"));

        mockMvc.perform(delete("/alunos/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/alunos/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingAluno() throws Exception {
        mockMvc.perform(delete("/alunos/invalid-id-to-delete"))
                .andExpect(status().isNotFound());
    }
}
