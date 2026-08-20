# 📖 Guia Definitivo de Arquitetura e Código: Spring Boot com JDBC do Zero ao Banco de Dados

Este documento é o guia didático completo do projeto **Sistema Acadêmico** (Trabalho 1 da disciplina de Laboratório de Desenvolvimento em Banco de Dados III - Prof. Bertoti). Ele foi projetado para estudantes iniciantes em **Java**, **Orientação a Objetos (POO)**, **Spring Boot**, **Spring JDBC** e **Banco de Dados Relacional (H2 / SQL)**, explicando exaustivamente cada classe, método, conceito teórico, comando SQL, construtor, herança, polimorfismo e encapsulamento.

---

## 📑 Sumário Completo

1. [Visão Geral e Objetivos de Aprendizagem](#1-visão-geral-e-objetivos-de-aprendizagem)
   - [1.1. Como Executar e Abrir no Navegador (Passo a Passo Rápido)](#11-como-executar-e-abrir-no-navegador-passo-a-passo-rápido)
2. [Fundamentos de Banco de Dados: Por que usar JDBC no início?](#2-fundamentos-de-banco-de-dados-por-que-usar-jdbc-no-início)
   - [2.1. Diferenças entre DDL, DML e DQL](#21-diferenças-entre-ddl-dml-e-dql)
   - [2.2. A Anatomia da Conexão JDBC e o Papel do JdbcTemplate](#22-a-anatomia-da-conexão-jdbc-e-o-papel-do-jdbctemplate)
3. [Pilares de Orientação a Objetos (POO) Aplicados no Projeto](#3-pilares-de-orientação-a-objetos-poo-aplicados-no-projeto)
   - [3.1. Abstração e Modelagem de Entidade](#31-abstração-e-modelagem-de-entidade)
   - [3.2. Encapsulamento e Modificadores de Acesso](#32-encapsulamento-e-modificadores-de-acesso)
   - [3.3. Herança e Sobrescrita (@Override)](#33-herança-e-sobrescrita-override)
   - [3.4. Polimorfismo e Interfaces Funcionais (RowMapper / Lambdas)](#34-polimorfismo-e-interfaces-funcionais-rowmapper--lambdas)
   - [3.5. Sobrecarga de Construtores (Overloading)](#35-sobrecarga-de-construtores-overloading)
4. [Arquitetura em Camadas (Layered Architecture) e Fluxo de Dados](#4-arquitetura-em-camadas-layered-architecture-e-fluxo-de-dados)
   - [4.1. Diagrama da Arquitetura](#41-diagrama-da-arquitetura)
   - [4.2. Ciclo de Vida Passo a Passo de uma Requisição Web](#42-ciclo-de-vida-passo-a-passo-de-uma-requisição-web)
5. [Análise Detalhada de Todos os Arquivos do Backend](#5-análise-detalhada-de-todos-os-arquivos-do-backend)
   - [5.1. schema.sql (Script DDL de Criação da Tabela)](#51-schemasql-script-ddl-de-criação-da-tabela)
   - [5.2. Aluno.java (Modelo de Domínio / POJO)](#52-alunojava-modelo-de-domínio--pojo)
   - [5.3. AlunoRepository.java (Acesso a Dados com JdbcTemplate e SQL)](#53-alunorepositoryjava-acesso-a-dados-com-jdbctemplate-e-sql)
   - [5.4. AlunoService.java (Camada de Regras de Negócio)](#54-alunoservicejava-camada-de-regras-de-negócio)
   - [5.5. AlunoController.java (Controlador REST e Endpoints HTTP)](#55-alunocontrollerjava-controlador-rest-e-endpoints-http)
   - [5.6. SburRestDemoApplication.java (Ponto de Entrada e Carga Inicial)](#56-sburrestdemoapplicationjava-ponto-de-entrada-e-carga-inicial)
   - [5.7. application.properties (Configuração do DataSource e H2)](#57-applicationproperties-configuração-do-datasource-e-h2)
   - [5.8. pom.xml (Gerenciamento de Dependências Maven)](#58-pomxml-gerenciamento-de-dependências-maven)
6. [Análise dos Testes Automatizados](#6-análise-dos-testes-automatizados)
   - [6.1. AlunoControllerTest.java (Testes de Integração com MockMvc)](#61-alunocontrollertestjava-testes-de-integração-com-mockmvc)
   - [6.2. SburRestDemoApplicationTests.java (Teste de Contexto)](#62-sburrestdemoapplicationtestsjava-teste-de-contexto)
7. [Análise da Camada Frontend (Interface Web SPA)](#7-análise-da-camada-frontend-interface-web-spa)
   - [7.1. index.html (Estrutura da Interface)](#71-indexhtml-estrutura-da-interface)
   - [7.2. index.js (Consumo da API REST com Axios)](#72-indexjs-consumo-da-api-rest-com-axios)
   - [7.3. style.css (Estilização Customizada)](#73-stylecss-estilização-customizada)
8. [Tabela Resumo das Anotações e Tecnologias](#8-tabela-resumo-das-anotações-e-tecnologias)
9. [Guia de Estudo e Exercícios Práticos para Iniciantes](#9-guia-de-estudo-e-exercícios-práticos-para-iniciantes)

---

## 1. Visão Geral e Objetivos de Aprendizagem

Este projeto foi construído para servir como o modelo ideal de aprendizado para quem está ingressando no universo do desenvolvimento backend em Java.

### O que o estudante aprende na prática com este projeto:
1. **Comandos SQL Reais:** Como estruturar comandos de banco de dados (`CREATE TABLE`, `INSERT`, `SELECT`, `UPDATE`, `DELETE`) de forma parametrizada e segura.
2. **Conexão Java + Banco de Dados:** Como o Spring Boot gerencia o *pool de conexões* via DataSource e como o `JdbcTemplate` interage com o driver do H2 Database.
3. **Mapeamento Tabular para Objeto:** Como transformar linhas e colunas do banco relacional (`ResultSet`) em objetos Java em memória através do `RowMapper`.
4. **Arquitetura em Camadas:** O porquê de separar Controller (Web), Service (Regras) e Repository (Banco).
5. **API RESTful:** Como criar rotas HTTP que respeitam os métodos padronizados (`GET`, `POST`, `PUT`, `DELETE`) e retornam códigos de status apropriados (`200 OK`, `201 CREATED`, `204 NO CONTENT`, `404 NOT FOUND`).
6. **Frontend Integrado:** Como uma página web (HTML/JS/Axios) consome dados JSON de uma API backend.

---

### 1.1. Como Executar e Abrir no Navegador (Passo a Passo Rápido)

Para ver a aplicação funcionando na prática:

1. **Abra o Terminal e entre na pasta do projeto:**
   ```bash
   cd Lab3/Trabalho-1
   ```
2. **Inicie o servidor:**
   * No **Windows (PowerShell / CMD):** `.\mvnw.cmd clean spring-boot:run`
   * No **Linux / macOS:** `./mvnw clean spring-boot:run`
3. **Aguarde a mensagem no terminal:** `Started SburRestDemoApplication in X seconds`.
4. **Abra o Navegador e acesse:**
   * 👉 **Interface Gráfica (Frontend):** [http://localhost:8080/](http://localhost:8080/)
   * 🗃️ **Console do Banco H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console) *(JDBC URL: `jdbc:h2:mem:academicodb`, Usuário: `sa`, Senha: em branco)*
   * 🔌 **API REST JSON:** [http://localhost:8080/alunos](http://localhost:8080/alunos)

---

## 2. Fundamentos de Banco de Dados: Por que usar JDBC no início?

### 2.1. Diferenças entre DDL, DML e DQL

No aprendizado de banco de dados, os comandos SQL são divididos em categorias essenciais:

* **DDL (Data Definition Language - Linguagem de Definição de Dados):**
  * Comandos que criam e alteram a estrutura do banco.
  * **Exemplo no projeto:** `CREATE TABLE IF NOT EXISTS aluno (...)` presente em `schema.sql`.
* **DML (Data Manipulation Language - Linguagem de Manipulação de Dados):**
  * Comandos que inserem, modificam ou excluem registros.
  * **Exemplos no projeto:**
    * `INSERT INTO aluno (id, nome, email, curso) VALUES (?, ?, ?, ?)`
    * `UPDATE aluno SET nome = ?, email = ?, curso = ? WHERE id = ?`
    * `DELETE FROM aluno WHERE id = ?`
* **DQL (Data Query Language - Linguagem de Consulta de Dados):**
  * Comandos para recuperar informações armazenadas.
  * **Exemplos no projeto:**
    * `SELECT id, nome, email, curso FROM aluno`
    * `SELECT id, nome, email, curso FROM aluno WHERE id = ?`
    * `SELECT COUNT(*) FROM aluno`

---

### 2.2. A Anatomia da Conexão JDBC e o Papel do `JdbcTemplate`

No Java tradicional (JDBC puro antigo), para executar uma consulta era necessário escrever dezenas de linhas manuais para abrir `Connection`, `PreparedStatement` e `ResultSet`.

O **`JdbcTemplate` do Spring Boot** elimina todo esse código repetitivo (*boilerplate*), cuidando automaticamente de:
1. Obter uma conexão do pool gerenciado pelo **HikariCP**.
2. Criar e parametrizar o `PreparedStatement` com proteção contra **SQL Injection**.
3. Iterar sobre o `ResultSet` através de um `RowMapper`.
4. Fechar os recursos com segurança mesmo em caso de erro.
5. Traduzir exceções de banco (`SQLException`) em exceções consistentes do Spring (`DataAccessException`).

---

## 3. Pilares de Orientação a Objetos (POO) Aplicados no Projeto

---

### 3.1. Abstração e Modelagem de Entidade
A classe `Aluno` abstrai um conceito do mundo real acadêmico, selecionando apenas as propriedades relevantes para o sistema: `id`, `nome`, `email` e `curso`.

---

### 3.2. Encapsulamento e Modificadores de Acesso
* **Atributos Privados (`private`):** Nenhuma classe externa pode alterar diretamente os atributos `id`, `nome`, `email` ou `curso`.
* **Métodos Públicos (`public` - Getters e Setters):** Controlam o acesso e a modificação dos dados com segurança.

---

### 3.3. Herança e Sobrescrita (`@Override`)

Em Java, toda classe herda automaticamente da classe raiz **`java.lang.Object`**.

No arquivo `Aluno.java`, sobrescrevemos (`@Override`) três métodos fundamentais herdados de `Object`:
1. **`equals(Object o)`:** Define que dois alunos são considerados iguais se possuírem o mesmo `id` (identificador único).
2. **`hashCode()`:** Gera um código hash consistente baseado no `id`, permitindo o uso eficiente em coleções como `HashSet` e `HashMap`.
3. **`toString()`:** Converte o objeto em uma representação textual legível para logs e depuração.

---

### 3.4. Polimorfismo e Interfaces Funcionais (RowMapper / Lambdas)

O **Polimorfismo** manifesta-se fortemente no uso de interfaces:

1. **`RowMapper<T>`:** É uma interface do Spring JDBC com um único método abstrato (`mapRow`). No `AlunoRepository`, utilizamos uma **expressão Lambda** para implementar essa interface de forma elegante:
   ```java
   private final RowMapper<Aluno> alunoRowMapper = (rs, rowNum) -> new Aluno(
           rs.getString("id"),
           rs.getString("nome"),
           rs.getString("email"),
           rs.getString("curso")
   );
   ```
2. **Polimorfismo de Coleções:** Métodos no `Service` e `Controller` retornam a interface abstrata `Iterable<Aluno>` ou `List<Aluno>`, desacoplando a implementação concreta da lista.

---

### 3.5. Sobrecarga de Construtores (Overloading)

A classe `Aluno` possui **três construtores sobrecarregados**, cada um atendendo a um caso de uso específico:

1. **`public Aluno()`:** Construtor sem argumentos.
2. **`public Aluno(String id, String nome, String email, String curso)`:** Construtor completo com todos os campos (usado pelo `RowMapper` ao ler do banco ou em testes).
3. **`public Aluno(String nome, String email, String curso)`:** Construtor que utiliza a instrução **`this(...)`** para encadear a chamada ao construtor completo, gerando um identificador único universal (`UUID.randomUUID().toString()`) automaticamente:
   ```java
   public Aluno(String nome, String email, String curso) {
       this(UUID.randomUUID().toString(), nome, email, curso);
   }
   ```

---

## 4. Arquitetura em Camadas (Layered Architecture) e Fluxo de Dados

---

### 4.1. Diagrama da Arquitetura

```
+-------------------------------------------------------------------------+
|                  CAMADA DE APRESENTAÇÃO / FRONTEND                      |
|           Navegador Web / Postman / static (index.html, index.js)       |
+-------------------------------------------------------------------------+
                                    │  ▲  HTTP Requests (GET, POST, PUT, DELETE)
                                    ▼  │  HTTP Responses (JSON, Status Codes)
+-------------------------------------------------------------------------+
|                  1. CAMADA CONTROLADORA (CONTROLLER)                    |
|                        AlunoController.java                             |
|  - Mapeia rotas REST (/alunos)                                          |
|  - Converte JSON do cliente em objetos Java                             |
|  - Retorna ResponseEntity com status HTTP (200, 201, 204, 404)          |
+-------------------------------------------------------------------------+
                                    │  ▲  Chamadas de métodos Java
                                    ▼  │  Retorno de objetos / Optionals
+-------------------------------------------------------------------------+
|                  2. CAMADA DE SERVIÇO (SERVICE)                         |
|                          AlunoService.java                              |
|  - Orquestra regras de negócio e validações defensivas                  |
|  - Isola o Controller de detalhes de persistência                       |
+-------------------------------------------------------------------------+
                                    │  ▲  Chamadas de persistência
                                    ▼  │  Objetos mapeados (Aluno)
+-------------------------------------------------------------------------+
|                  3. CAMADA DE ACESSO A DADOS (REPOSITORY / DAO)         |
|                        AlunoRepository.java                             |
|  - Executa SQL via JdbcTemplate (SELECT, INSERT, UPDATE, DELETE)        |
|  - Converte linhas do ResultSet em objetos Aluno via RowMapper          |
+-------------------------------------------------------------------------+
                                    │  ▲  Comandos SQL / ResultSet
                                    ▼  │  via Driver JDBC (org.h2.Driver)
+-------------------------------------------------------------------------+
|                  4. BANCO DE DADOS RELACIONAL (DATABASE)                |
|                    H2 Database em memória (academicodb)                 |
|  - Tabela: ALUNO (ID VARCHAR, NOME VARCHAR, EMAIL VARCHAR, CURSO VARCHAR|
+-------------------------------------------------------------------------+
```

---

### 4.2. Ciclo de Vida Passo a Passo de uma Requisição Web

Vamos rastrear o que acontece quando o usuário clica em **"Salvar Aluno"** com os dados: `Nome: "Carla"`, `E-mail: "carla@fatec.br"`, `Curso: "Banco de Dados"`:

1. **Disparo no Frontend:** O arquivo `index.js` captura o evento de `submit` do formulário e faz um `axios.post('/alunos', { nome: "Carla", email: "carla@fatec.br", curso: "Banco de Dados" })`.
2. **Recepção no Controller:** O método `@PostMapping postAluno(@RequestBody Aluno aluno)` em `AlunoController` intercepta a requisição HTTP. O Spring converte o JSON em uma instância da classe `Aluno`.
3. **Repasse para o Service:** O controller invoca `alunoService.save(aluno)`.
4. **Execução no Repository (DAO):** O service repassa para `alunoRepository.save(aluno)`.
   - O repositório chama `aluno.ensureId()`, gerando um UUID caso o ID esteja vazio.
   - O repositório verifica se o aluno já existe via `existsById(aluno.getId())`.
   - Como é novo, monta o comando SQL:
     ```
     INSERT INTO aluno (id, nome, email, curso) VALUES (?, ?, ?, ?)
     ```
   - O `jdbcTemplate.update(...)` envia o comando ao driver H2.
5. **Gravação no H2 Database:** O motor relacional do H2 grava a nova linha fisicamente na tabela `aluno`.
6. **Resposta HTTP ao Cliente:** O objeto salvo é empacotado em um `ResponseEntity<>(saved, HttpStatus.CREATED)` com status **201**, que trafega de volta pela rede até o frontend, atualizando a lista na tela instantaneamente.

---

## 5. Análise Detalhada de Todos os Arquivos do Backend

---

### 5.1. `schema.sql` (Script DDL de Criação da Tabela)

Arquivo: `src/main/resources/schema.sql`

```
-- Esquema DDL para criacao da tabela de Alunos
CREATE TABLE IF NOT EXISTS aluno (
    id VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    curso VARCHAR(255) NOT NULL
);
```

* **`CREATE TABLE IF NOT EXISTS aluno`:** Instrução DDL que cria a tabela `aluno` apenas se ela ainda não existir no banco.
* **`id VARCHAR(255) PRIMARY KEY`:** Define a coluna `id` como texto de até 255 caracteres e estabelece que ela é a **Chave Primária (Primary Key)**, garantindo unicidade e indexação rápida.
* **`nome`, `email`, `curso` com `NOT NULL`:** Colunas de dados obrigatórias. A restrição `NOT NULL` impede que linhas sejam salvas sem essas informações.

---

### 5.2. `Aluno.java` (Modelo de Domínio / POJO)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/model/Aluno.java`

```java
package com.thehecklers.sburrestdemo.model;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public class Aluno {

    private String id;
    private String nome;
    private String email;
    private String curso;

    public Aluno() {
    }

    public Aluno(String id, String nome, String email, String curso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.curso = curso;
    }

    public Aluno(String nome, String email, String curso) {
        this(UUID.randomUUID().toString(), nome, email, curso);
    }

    public void ensureId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

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
        return "Aluno{" + "id='" + id + '\'' + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", curso='" + curso + '\'' + '}';
    }
}
```

* **Atributos privados:** Compõem o estado do objeto (Encapsulamento).
* **Construtor padrão sem argumentos:** Necessário para serializadores JSON como Jackson.
* **Construtor completo:** Inicializa todos os campos.
* **Construtor com sobrecarga (`this(...)`):** Gera automaticamente um identificador UUID.
* **`ensureId`:** Método de segurança defensiva: se o aluno foi instanciado sem ID, gera um UUID antes de enviar a query de inserção.
* **Getters e Setters:** Métodos para leitura e alteração controlada.
* **`equals` e `hashCode`:** Sobrescrita dos métodos de `Object` para comparação baseada no `id`.
* **`toString`:** Formata os dados do objeto em String para fácil visualização.

---

### 5.3. `AlunoRepository.java` (Acesso a Dados com JdbcTemplate e SQL)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/repository/AlunoRepository.java`

```java
package com.thehecklers.sburrestdemo.repository;

import com.thehecklers.sburrestdemo.model.Aluno;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlResolve", "SqlWithoutWhere", "unused"})
@Repository
public class AlunoRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Aluno> alunoRowMapper = (rs, rowNum) -> new Aluno(
            rs.getString("id"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("curso")
    );

    public AlunoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Aluno> findAll() {
        String sql = "SELECT id, nome, email, curso FROM aluno";
        return jdbcTemplate.query(sql, alunoRowMapper);
    }

    public Optional<Aluno> findById(String id) {
        String sql = "SELECT id, nome, email, curso FROM aluno WHERE id = ?";
        List<Aluno> results = jdbcTemplate.query(sql, alunoRowMapper, id);
        return results.stream().findFirst();
    }

    public Aluno save(Aluno aluno) {
        aluno.ensureId();
        if (existsById(aluno.getId())) {
            String sql = "UPDATE aluno SET nome = ?, email = ?, curso = ? WHERE id = ?";
            jdbcTemplate.update(sql, aluno.getNome(), aluno.getEmail(), aluno.getCurso(), aluno.getId());
        } else {
            String sql = "INSERT INTO aluno (id, nome, email, curso) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getCurso());
        }
        return aluno;
    }

    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public boolean deleteById(String id) {
        String sql = "DELETE FROM aluno WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM aluno";
        Long total = jdbcTemplate.queryForObject(sql, Long.class);
        return total != null ? total : 0L;
    }

    public void saveAll(Iterable<Aluno> alunos) {
        for (Aluno aluno : alunos) {
            save(aluno);
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM aluno";
        jdbcTemplate.update(sql);
    }
}
```

* **`@Repository`:** Registra a classe como um Bean gerenciado no Spring IoC Container especializado em acesso a dados.
* **`alunoRowMapper`:** Instância de `RowMapper` implementada via lambda que lê cada coluna do `ResultSet` (`rs.getString("...")`) e cria um objeto `Aluno`.
* **Injeção do `JdbcTemplate`:** Realizada no construtor.
* **`findAll`:** Executa o `SELECT` em toda a tabela e usa o `RowMapper` para devolver uma `List<Aluno>`.
* **`findById`:** Consulta parametrizada com `?` (evita SQL Injection). Retorna um `Optional<Aluno>` para evitar `NullPointerException`.
* **`save`:** Implementa o comportamento de **Upsert**: se o aluno já existir no banco, executa `UPDATE`; se não existir, executa `INSERT`.
* **`existsById`:** Executa `SELECT COUNT(*)` com `queryForObject`, verificando se há registros com aquele ID.
* **`deleteById`:** Executa o comando `DELETE` e verifica se `rowsAffected > 0` para retornar `true` ou `false`.
* **`deleteAll`:** Limpa a tabela com `DELETE FROM aluno` (essencial para isolamento nos testes unitários).

---

### 5.4. `AlunoService.java` (Camada de Regras de Negócio)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/service/AlunoService.java`

```java
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
```

* **`@Service`:** Marca a classe como Bean de lógica de negócio.
* **Injeção por construtor:** Injeta o `AlunoRepository`.
* **`deleteById`:** Regra defensiva: verifica a existência antes de tentar excluir, retornando `true` (sucesso) ou `false` (não encontrado).

---

### 5.5. `AlunoController.java` (Controlador REST e Endpoints HTTP)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/controller/AlunoController.java`

```java
package com.thehecklers.sburrestdemo.controller;

import com.thehecklers.sburrestdemo.model.Aluno;
import com.thehecklers.sburrestdemo.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable String id) {
        return alunoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aluno> postAluno(@RequestBody Aluno aluno) {
        Aluno saved = alunoService.save(aluno);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> putAluno(@PathVariable String id, @RequestBody Aluno aluno) {
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
}
```

* **`@CrossOrigin`:** Permite requisições originadas de outras portas locais (ex: Live Server `5500`).
* **`@RestController`:** Combinação de `@Controller` com `@ResponseBody`, instruindo o Spring a serializar o retorno dos métodos em formato **JSON**.
* **`@RequestMapping("/alunos")`:** Rota base para todas as operações.
* **`GET /alunos`:** Lista todos os alunos cadastrados com status `200 OK`.
* **`GET /alunos/{id}`:** Usa `@PathVariable` para extrair o ID da URL. Se encontrar, retorna `200 OK`; se não, retorna `404 NOT FOUND`.
* **`POST /alunos`:** Usa `@RequestBody` para converter o corpo JSON em `Aluno` e retorna status **201 CREATED**.
* **`PUT /alunos/{id}`:** Se o registro já existia, atualiza e devolve `200 OK`. Se não existia, cria e devolve `201 CREATED`.
* **`DELETE /alunos/{id}`:** Se deletou, retorna **204 NO CONTENT** (sucesso sem corpo); se o aluno não existia, retorna **404 NOT FOUND**.

---

### 5.6. `SburRestDemoApplication.java` (Ponto de Entrada e Carga Inicial)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/SburRestDemoApplication.java`

```java
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
```

* **`@SpringBootApplication`:** Habilita a configuração automática e o escaneamento de componentes nos subpacotes (`model`, `repository`, `service`, `controller`).
* **`@PostConstruct`:** Método executado automaticamente após o Spring inicializar o contexto. Verifica se o banco está vazio (`count() == 0`) e insere 4 alunos de exemplo para testes.

---

### 5.7. `application.properties` (Configuração do DataSource e H2)

Arquivo: `src/main/resources/application.properties`

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:academicodb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.sql.init.mode=always
```

* **`spring.h2.console.enabled=true`:** Habilita a interface web do banco H2 em `/h2-console`.
* **`spring.datasource.url`:** Conecta ao banco relacional em memória `academicodb`. Os parâmetros `DB_CLOSE_DELAY=-1` e `DB_CLOSE_ON_EXIT=FALSE` garantem que o banco permaneça vivo durante toda a execução da aplicação.
* **`spring.datasource.driverClassName`:** Driver oficial do H2 (`org.h2.Driver`).
* **`spring.sql.init.mode=always`:** Garante que o Spring execute o script `schema.sql` sempre que a aplicação subir, criando a tabela `aluno`.

---

### 5.8. `pom.xml` (Gerenciamento de Dependências Maven)

Arquivo: `pom.xml`

* **`spring-boot-starter-web`:** Fornece o servidor Tomcat embutido, Jackson (JSON) e o framework Spring MVC para construção da API REST.
* **`spring-boot-starter-jdbc`:** Fornece o `JdbcTemplate`, `DataSource` e pool de conexões **HikariCP**.
* **`com.h2database:h2`:** Banco de dados relacional SQL embutido em memória.
* **`spring-boot-starter-test`:** Fornece JUnit 5, Mockito, AssertJ e MockMvc para testes automatizados.

---

## 6. Análise dos Testes Automatizados

---

### 6.1. `AlunoControllerTest.java` (Testes de Integração com MockMvc)

Arquivo: `src/test/java/com/thehecklers/sburrestdemo/AlunoControllerTest.java`

Esta classe executa testes simulando chamadas HTTP completas:

```java
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
        alunoRepository.deleteAll(); // Limpa a tabela antes de cada teste
    }
}
```

#### Cenários Testados:
1. **`shouldReturnEmptyListWhenNoAlunosExist`:** Garante que `GET /alunos` retorna `200 OK` e um array vazio `[]` quando não há dados.
2. **`shouldCreateNewAluno`:** Envia `POST /alunos` com JSON e valida status `201 CREATED`, geração de ID e campos via `jsonPath`.
3. **`shouldGetAlunoByIdWhenExists`:** Salva um aluno no banco e verifica se `GET /alunos/{id}` recupera os dados corretamente (`200 OK`).
4. **`shouldReturn404WhenAlunoDoesNotExist`:** Valida retorno de status `404 NOT FOUND` para IDs inexistentes.
5. **`shouldUpdateExistingAluno`:** Testa a alteração de dados de um aluno existente via `PUT /alunos/{id}` (`200 OK`).
6. **`shouldCreateAlunoOnPutWhenNotExists`:** Testa a funcionalidade de *Upsert* do `PUT`, criando o registro caso o ID informado na URL não exista (`201 CREATED`).
7. **`shouldDeleteAlunoSuccessfully`:** Testa a remoção com `DELETE /alunos/{id}`, verificando status `204 NO CONTENT` e confirmando que uma busca posterior resulta em `404`.
8. **`shouldReturn404WhenDeletingNonExistingAluno`:** Valida retorno `404` ao tentar deletar registro que não existe.

---

### 6.2. `SburRestDemoApplicationTests.java` (Teste de Contexto)

Valida se toda a configuração de injeção de dependência e banco H2 inicializa sem erros (`contextLoads()`).

---

## 7. Análise da Camada Frontend (Interface Web SPA)

Os arquivos estáticos ficam em `src/main/resources/static/` e são servidos diretamente pelo Spring Boot em `http://localhost:8080/`.

---

### 7.1. `index.html` (Estrutura da Interface)
* Utiliza **Bootstrap 4** e **FontAwesome** para um design moderno e responsivo.
* Contém cabeçalho com atalhos para o **Console H2** (`/h2-console`) e **API REST** (`/alunos`).
* Campo de busca em tempo real e contador de matrículas cadastradas.
* Modal interativo para cadastro e edição de alunos com validação de campos.

---

### 7.2. `index.js` (Consumo da API REST com Axios)
* **`carregarAlunos()`:** Faz `axios.get('/alunos')` e renderiza a lista dinamicamente com avatares customizados por iniciais e badges coloridas de acordo com o curso.
* **Filtro em Tempo Real:** Evento `input` no campo de busca que filtra a lista por nome, e-mail ou curso sem recarregar a página.
* **Cadastro e Edição:** Captura o formulário e decide entre enviar `axios.post` (novo) ou `axios.put` (edição).
* **Exclusão:** Confirmação com modal/alert e disparo de `axios.delete('/alunos/{id}')`.

---

### 7.3. `style.css` (Estilização Customizada)
* Personalização visual moderna com esquema de cores azul e cinza claro.
* Animações suaves para abertura e fechamento do modal customizado (`custom-modal`).
* Estilização dos cartões e itens de lista com hover states.

---

## 8. Tabela Resumo das Anotações e Tecnologias

| Tecnologia / Anotação | Onde é Aplicada? | Função Detalhada |
| :--- | :--- | :--- |
| `@SpringBootApplication` | `SburRestDemoApplication` | Inicializa autoconfiguração, escaneamento de pacotes e servidor Tomcat. |
| `@PostConstruct` | Métodos | Executa uma rotina logo após a injeção de dependências estar pronta (carga inicial de dados). |
| `@Repository` | `AlunoRepository` | Registra a classe DAO de persistência no Spring. |
| `JdbcTemplate` | `AlunoRepository` | Executa comandos SQL parametrizados com segurança e tratamento automático de conexões. |
| `RowMapper<T>` | `AlunoRepository` | Converte linhas do `ResultSet` SQL em objetos da classe Java `Aluno`. |
| `@Service` | `AlunoService` | Registra a classe que encapsula a lógica e regras de negócio. |
| `@RestController` | `AlunoController` | Define controlador web cujos retornos de métodos são serializados em JSON. |
| `@RequestMapping` | `AlunoController` | Define a rota base da API (ex: `/alunos`). |
| `@GetMapping` | Métodos de Controller | Mapeia requisições HTTP do tipo `GET` (leitura). |
| `@PostMapping` | Métodos de Controller | Mapeia requisições HTTP do tipo `POST` (criação). |
| `@PutMapping` | Métodos de Controller | Mapeia requisições HTTP do tipo `PUT` (atualização / upsert). |
| `@DeleteMapping` | Métodos de Controller | Mapeia requisições HTTP do tipo `DELETE` (exclusão). |
| `@PathVariable` | Parâmetros de Método | Captura variáveis passadas na URL (ex: `/alunos/{id}`). |
| `@RequestBody` | Parâmetros de Método | Converte o corpo JSON da requisição em um objeto Java. |
| `@CrossOrigin` | `AlunoController` | Permite chamadas de origens e portas externas (CORS). |
| `@SpringBootTest` | Classes de Teste | Sobe o contexto do Spring para testes de integração. |
| `@AutoConfigureMockMvc` | Classes de Teste | Injeta o `MockMvc` para simular requisições HTTP sem abrir portas reais de rede. |

---

## 9. Guia de Estudo e Exercícios Práticos para Iniciantes

Para fixar o conteúdo deste projeto, pratique os seguintes passos:

1. **Inspecione o Banco pelo Console H2:**
   - Acesse [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
   - Conecte usando a JDBC URL `jdbc:h2:mem:academicodb`.
   - Execute comandos manuais:
     ```
     SELECT * FROM aluno WHERE curso = 'Engenharia de Software';
     ```
2. **Teste a API com o Postman:**
   - Importe a collection [`Sistema_Academico_Alunos.postman_collection.json`](./postman/Sistema_Academico_Alunos.postman_collection.json).
   - Dispare requisições `GET`, `POST`, `PUT` e `DELETE` e compare os códigos de status HTTP retornados.
3. **Exercício Prático de Fixação:**
   - Adicione um novo campo `telefone VARCHAR(20)` na tabela `aluno` em `schema.sql`.
   - Atualize a classe `Aluno.java`, o `RowMapper` no `AlunoRepository.java`, e teste salvar e listar o novo campo!
