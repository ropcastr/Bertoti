package com.thehecklers.sburrestdemo.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Representa a entidade de domínio Aluno (POJO puro, sem anotações JPA).
 * Os dados desta classe são mapeados manualmente para as colunas SQL via JDBC RowMapper.
 */
@SuppressWarnings("unused")
public class Aluno {

    private String id;
    private String nome;
    private String email;
    private String curso;

    // Construtor padrão sem argumentos
    public Aluno() {
    }

    // Construtor completo
    public Aluno(String id, String nome, String email, String curso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.curso = curso;
    }

    // Construtor de conveniência que gera um UUID automaticamente caso o ID não seja fornecido
    public Aluno(String nome, String email, String curso) {
        this(UUID.randomUUID().toString(), nome, email, curso);
    }

    public void ensureId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno aluno)) return false;
        return Objects.equals(id, aluno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
}
