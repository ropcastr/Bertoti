package com.thehecklers.sburrestdemo.controller;

import com.thehecklers.sburrestdemo.model.Aluno;
import com.thehecklers.sburrestdemo.service.AlunoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }


    @GetMapping
    public Iterable<Aluno> getAlunos() {
        return alunoService.findAll();
    }

    // GET /alunos/{id} - Busca aluno por ID (retorna 404 se não encontrado)
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable String id) {
        return alunoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> postAluno(@RequestBody(required = false) Aluno aluno) {
        if (isInvalid(aluno)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", "Os campos 'nome', 'email' e 'curso' são obrigatórios e não podem ser vazios."));
        }
        Aluno saved = alunoService.save(aluno);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> putAluno(@PathVariable String id, @RequestBody(required = false) Aluno aluno) {
        if (isInvalid(aluno)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", "Os campos 'nome', 'email' e 'curso' são obrigatórios e não podem ser vazios."));
        }
        aluno.setId(id);
        boolean exists = alunoService.existsById(id);
        Aluno saved = alunoService.save(aluno);
        return exists ? ResponseEntity.ok(saved) : new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable String id) {
        return alunoService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", "Violação de integridade nos dados: certifique-se de que todos os campos obrigatórios foram preenchidos corretamente."));
    }


    private boolean isInvalid(Aluno aluno) {
        return aluno == null
                || aluno.getNome() == null || aluno.getNome().isBlank()
                || aluno.getEmail() == null || aluno.getEmail().isBlank()
                || aluno.getCurso() == null || aluno.getCurso().isBlank();
    }
}
