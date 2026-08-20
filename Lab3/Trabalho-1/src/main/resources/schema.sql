-- Esquema DDL para criação da tabela de Alunos
CREATE TABLE IF NOT EXISTS aluno (
    id VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    curso VARCHAR(255) NOT NULL
);
