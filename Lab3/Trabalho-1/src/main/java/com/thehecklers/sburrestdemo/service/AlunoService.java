package com.thehecklers.sburrestdemo.service;

import com.thehecklers.sburrestdemo.model.Aluno;
import com.thehecklers.sburrestdemo.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Iterable<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> findById(String id) {
        return alunoRepository.findById(id);
    }

    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public boolean existsById(String id) {
        return alunoRepository.existsById(id);
    }

    public boolean deleteById(String id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long count() {
        return alunoRepository.count();
    }

    public void saveAll(Iterable<Aluno> alunos) {
        alunoRepository.saveAll(alunos);
    }
}
