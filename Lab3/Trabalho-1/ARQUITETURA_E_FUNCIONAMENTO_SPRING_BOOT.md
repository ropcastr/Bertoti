# 📖 Guia Definitivo de Arquitetura e Código: Spring Boot com JDBC do Zero ao Banco de Dados

Este documento é o guia didático completo do projeto **Sistema Acadêmico** (Trabalho 1 da disciplina de Laboratório de Desenvolvimento em Banco de Dados III - Prof. Bertoti). Ele foi projetado para estudantes iniciantes em **Java**, **Orientação a Objetos (POO)**, **Spring Boot**, **Spring JDBC** e **Banco de Dados Relacional (H2 / SQL)**, explicando exaustivamente cada classe, método, linha de código, conceito teórico, comando SQL, construtor, herança, polimorfismo e encapsulamento.

---

## 📑 Sumário Completo

1. [Visão Geral e Objetivos de Aprendizagem](#1-visão-geral-e-objetivos-de-aprendizagem)
2. [Fundamentos de Banco de Dados: Por que usar JDBC no início?](#2-fundamentos-de-banco-de-dados-por-que-usar-jdbc-no-início)
   - [Diferenças entre DDL, DML e DQL](#21-diferenças-entre-ddl-dml-e-dql)
   - [A Anatomia da Conexão JDBC e o Papel do JdbcTemplate](#22-a-anatomia-da-conexão-jdbc-e-o-papel-do-jdbctemplate)
3. [Pilares de Orientação a Objetos (POO) Aplicados no Projeto](#3-pilares-de-orientação-a-objetos-poo-aplicados-no-projeto)
   - [Abstração e Modelagem de Entidade](#31-abstração-e-modelagem-de-entidade)
   - [Encapsulamento e Modificadores de Acesso](#32-encapsulamento-e-modificadores-de-acesso)
   - [Herança e Sobrescrita (@Override)](#33-herança-e-sobrescrita-override)
   - [Polimorfismo e Interfaces Funcionais (RowMapper / Lambdas)](#34-polimorfismo-e-interfaces-funcionais-rowmapper--lambdas)
   - [Sobrecarga de Construtores (Overloading)](#35-sobrecarga-de-construtores-overloading)
4. [Arquitetura em Camadas (Layered Architecture) e Fluxo de Dados](#4-arquitetura-em-camadas-layered-architecture-e-fluxo-de-dados)
   - [Diagrama da Arquitetura](#41-diagrama-da-arquitetura)
   - [Ciclo de Vida Passo a Passo de uma Requisição Web](#42-ciclo-de-vida-passo-a-passo-de-uma-requisição-web)
5. [Análise Detalhada Linha por Linha de Todos os Arquivos do Backend](#5-análise-detalhada-linha-por-linha-de-todos-os-arquivos-do-backend)
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
   - [7.3. style.css (Estilização)](#73-stylecss-estilização)
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

No Java tradicional (JDBC puro antigo), para executar uma consulta era necessário escrever dezenas de linhas manuais:

```java
// Forma Antiga (Verborrágica e propensa a vazamento de memória):
Connection conn = DriverManager.getConnection(url, user, pass);
PreparedStatement stmt = conn.prepareStatement("SELECT * FROM aluno WHERE id = ?");
stmt.setString(1, "123");
ResultSet rs = stmt.executeQuery();
while (rs.next()) { ... }
rs.close();
stmt.close();
conn.close();
```

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

Em Java, toda classe que não declara explicitamente um `extends` herda automaticamente da classe raiz **`java.lang.Object`**.

No arquivo `Aluno.java`, sobrescrevemos (`@Override`) três métodos fundamentais herdados de `Object`:
1. **`equals(Object o)`:** Define que dois alunos são considerados iguais se possuírem o mesmo `id` (identificador único).
2. **`hashCode()`:** Gera um código hash consistente baseado no `id`, permitindo o uso eficiente em coleções como `HashSet` e `HashMap`.
3. **`toString()`:** Converte o objeto em uma representação textual legível para logs e depuração.

---

### 3.4. Polimorfismo e Interfaces Funcionais (RowMapper / Lambdas)

O **Polimorfismo** manifesta-se fortemente no uso de interfaces:

1. **`RowMapper<T>`:** É uma interface do Spring JDBC com um único método abstrato:
   ```java
   T mapRow(ResultSet rs, int rowNum) throws SQLException;
   ```
   No `AlunoRepository`, utilizamos uma **expressão Lambda** (polimorfismo funcional em tempo de execução) para implementar essa interface de forma elegante:
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
2. **`public Aluno(String id, String nome, String email, String curso)`:** Construtor completo para quando já temos o ID definido (usado pelo `RowMapper` ao ler do banco ou em testes).
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
     ```sql
     INSERT INTO aluno (id, nome, email, curso) VALUES ('a1b2c3d4-...', 'Carla', 'carla@fatec.br', 'Banco de Dados');
     ```
   - O `jdbcTemplate.update(...)` envia o comando ao driver H2.
5. **Gravação no H2 Database:** O motor relacional do H2 grava a nova linha fisicamente na tabela `aluno`.
6. **Resposta HTTP ao Cliente:** O objeto salvo é empacotado em um `ResponseEntity<>(saved, HttpStatus.CREATED)` com status **201**, que trafega de volta pela rede até o frontend, atualizando a lista na tela instantaneamente.

---

## 5. Análise Detalhada Linha por Linha de Todos os Arquivos do Backend

---

### 5.1. `schema.sql` (Script DDL de Criação da Tabela)

Arquivo: `src/main/resources/schema.sql`

```sql
1: -- Esquema DDL para criação da tabela de Alunos
2: CREATE TABLE IF NOT EXISTS aluno (
3:     id VARCHAR(255) PRIMARY KEY,
4:     nome VARCHAR(255) NOT NULL,
5:     email VARCHAR(255) NOT NULL,
6:     curso VARCHAR(255) NOT NULL
7: );
```

* **Linha 2 (`CREATE TABLE IF NOT EXISTS aluno`):** Instrução DDL que cria a tabela `aluno` apenas se ela ainda não existir no banco.
* **Linha 3 (`id VARCHAR(255) PRIMARY KEY`):** Define a coluna `id` como texto de até 255 caracteres e estabelece que ela é a **Chave Primária (Primary Key)**, garantindo unicidade e indexação rápida.
* **Linhas 4 a 6 (`nome`, `email`, `curso` com `NOT NULL`):** Colunas de dados obrigatórias. A restrição `NOT NULL` impede que linhas sejam salvas sem essas informações.

---

### 5.2. `Aluno.java` (Modelo de Domínio / POJO)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/model/Aluno.java`

```java
1: package com.thehecklers.sburrestdemo.model;
2: 
3: import java.util.Objects;
4: import java.util.UUID;
5: 
6: public class Aluno {
7: 
8:     private String id;
9:     private String nome;
10:    private String email;
11:    private String curso;
12: 
13:    public Aluno() {
14:    }
15: 
16:    public Aluno(String id, String nome, String email, String curso) {
17:        this.id = id;
18:        this.nome = nome;
19:        this.email = email;
20:        this.curso = curso;
21:    }
22: 
23:    public Aluno(String nome, String email, String curso) {
24:        this(UUID.randomUUID().toString(), nome, email, curso);
25:    }
26: 
27:    public void ensureId() {
28:        if (this.id == null || this.id.isBlank()) {
29:            this.id = UUID.randomUUID().toString();
30:        }
31:    }
32: 
33:    public String getId() { return id; }
34:    public void setId(String id) { this.id = id; }
35:    public String getNome() { return nome; }
36:    public void setNome(String nome) { this.nome = nome; }
37:    public String getEmail() { return email; }
38:    public void setEmail(String email) { this.email = email; }
39:    public String getCurso() { return curso; }
40:    public void setCurso(String curso) { this.curso = curso; }
41: 
42:    @Override
43:    public boolean equals(Object o) {
44:        if (this == o) return true;
45:        if (!(o instanceof Aluno aluno)) return false;
46:        return Objects.equals(id, aluno.id);
47:    }
48: 
49:    @Override
50:    public int hashCode() {
51:        return Objects.hash(id);
52:    }
53: 
54:    @Override
55:    public String toString() {
56:        return "Aluno{" + "id='" + id + '\'' + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", curso='" + curso + '\'' + '}';
57:    }
58: }
```

* **Linhas 8 a 11:** Atributos privados que compõem o estado do objeto (Encapsulamento).
* **Linhas 13-14:** Construtor padrão sem argumentos (necessário para serializadores JSON como Jackson).
* **Linhas 16-21:** Construtor completo com todos os campos.
* **Linhas 23-25:** Construtor com sobrecarga (`this(...)`) que gera automaticamente um identificador UUID.
* **Linhas 27-31 (`ensureId`):** Método de segurança defensiva: se o aluno foi instanciado sem ID, gera um UUID antes de enviar a query de inserção.
* **Linhas 33-40:** Métodos Getters e Setters para leitura e alteração controlada.
* **Linhas 42-52 (`equals` e `hashCode`):** Sobrescrita dos métodos de `Object` para comparação baseada no `id`.
* **Linhas 54-57 (`toString`):** Formata os dados do objeto em String para fácil visualização.

---

### 5.3. `AlunoRepository.java` (Acesso a Dados com JdbcTemplate e SQL)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/repository/AlunoRepository.java`

```java
1: package com.thehecklers.sburrestdemo.repository;
2: 
3: import com.thehecklers.sburrestdemo.model.Aluno;
4: import org.springframework.jdbc.core.JdbcTemplate;
5: import org.springframework.jdbc.core.RowMapper;
6: import org.springframework.stereotype.Repository;
7: 
8: import java.util.List;
9: import java.util.Optional;
10: 
11: @Repository
12: public class AlunoRepository {
13: 
14:     private final JdbcTemplate jdbcTemplate;
15: 
16:     private final RowMapper<Aluno> alunoRowMapper = (rs, rowNum) -> new Aluno(
17:             rs.getString("id"),
18:             rs.getString("nome"),
19:             rs.getString("email"),
20:             rs.getString("curso")
21:     );
22: 
23:     public AlunoRepository(JdbcTemplate jdbcTemplate) {
24:         this.jdbcTemplate = jdbcTemplate;
25:     }
26: 
27:     public List<Aluno> findAll() {
28:         String sql = "SELECT id, nome, email, curso FROM aluno";
29:         return jdbcTemplate.query(sql, alunoRowMapper);
30:     }
31: 
32:     public Optional<Aluno> findById(String id) {
33:         String sql = "SELECT id, nome, email, curso FROM aluno WHERE id = ?";
34:         List<Aluno> results = jdbcTemplate.query(sql, alunoRowMapper, id);
35:         return results.stream().findFirst();
36:     }
37: 
38:     public Aluno save(Aluno aluno) {
39:         aluno.ensureId();
40:         if (existsById(aluno.getId())) {
41:             String sql = "UPDATE aluno SET nome = ?, email = ?, curso = ? WHERE id = ?";
42:             jdbcTemplate.update(sql, aluno.getNome(), aluno.getEmail(), aluno.getCurso(), aluno.getId());
43:         } else {
44:             String sql = "INSERT INTO aluno (id, nome, email, curso) VALUES (?, ?, ?, ?)";
45:             jdbcTemplate.update(sql, aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getCurso());
46:         }
47:         return aluno;
48:     }
49: 
50:     public boolean existsById(String id) {
51:         String sql = "SELECT COUNT(*) FROM aluno WHERE id = ?";
52:         Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
53:         return count != null && count > 0;
54:     }
55: 
56:     public boolean deleteById(String id) {
57:         String sql = "DELETE FROM aluno WHERE id = ?";
58:         int rowsAffected = jdbcTemplate.update(sql, id);
59:         return rowsAffected > 0;
60:     }
61: 
62:     public long count() {
63:         String sql = "SELECT COUNT(*) FROM aluno";
64:         Long total = jdbcTemplate.queryForObject(sql, Long.class);
65:         return total != null ? total : 0L;
66:     }
67: 
68:     public void saveAll(Iterable<Aluno> alunos) {
69:         for (Aluno aluno : alunos) {
70:             save(aluno);
71:         }
72:     }
73: 
74:     public void deleteAll() {
75:         String sql = "DELETE FROM aluno";
76:         jdbcTemplate.update(sql);
77:     }
78: }
```

* **Linha 11 (`@Repository`):** Registra a classe como um Bean gerenciado no Spring IoC Container especializado em acesso a dados.
* **Linhas 16-21 (`alunoRowMapper`):** Instância de `RowMapper` implementada via lambda que lê cada coluna do `ResultSet` (`rs.getString("...")`) e cria um objeto `Aluno`.
* **Linhas 23-25:** Injeção de dependência por construtor do `JdbcTemplate`.
* **Linhas 27-30 (`findAll`):** Executa o `SELECT` em toda a tabela e usa o `RowMapper` para devolver uma `List<Aluno>`.
* **Linhas 32-36 (`findById`):** Consulta parametrizada com `?` (evita SQL Injection). Retorna um `Optional<Aluno>` para evitar `NullPointerException`.
* **Linhas 38-48 (`save`):** Implementa o comportamento de **Upsert**: se o aluno já existir no banco, executa `UPDATE`; se não existir, executa `INSERT`.
* **Linhas 50-54 (`existsById`):** Executa `SELECT COUNT(*)` com `queryForObject`, verificando se há registros com aquele ID.
* **Linhas 56-60 (`deleteById`):** Executa o comando `DELETE` e verifica se `rowsAffected > 0` para retornar `true` ou `false`.
* **Linhas 74-77 (`deleteAll`):** Limpa a tabela com `DELETE FROM aluno` (essencial para isolamento nos testes unitários).

---

### 5.4. `AlunoService.java` (Camada de Regras de Negócio)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/service/AlunoService.java`

```java
1: package com.thehecklers.sburrestdemo.service;
2: 
3: import com.thehecklers.sburrestdemo.model.Aluno;
4: import com.thehecklers.sburrestdemo.repository.AlunoRepository;
5: import org.springframework.stereotype.Service;
6: 
7: import java.util.Optional;
8: 
9: @Service
10: public class AlunoService {
11: 
12:     private final AlunoRepository alunoRepository;
13: 
14:     public AlunoService(AlunoRepository alunoRepository) {
15:         this.alunoRepository = alunoRepository;
16:     }
17: 
18:     public Iterable<Aluno> findAll() {
19:         return alunoRepository.findAll();
20:     }
21: 
22:     public Optional<Aluno> findById(String id) {
23:         return alunoRepository.findById(id);
24:     }
25: 
26:     public Aluno save(Aluno aluno) {
27:         return alunoRepository.save(aluno);
28:     }
29: 
30:     public boolean existsById(String id) {
31:         return alunoRepository.existsById(id);
32:     }
33: 
34:     public boolean deleteById(String id) {
35:         if (alunoRepository.existsById(id)) {
36:             alunoRepository.deleteById(id);
37:             return true;
38:         }
39:         return false;
40:     }
41: 
42:     public long count() {
43:         return alunoRepository.count();
44:     }
45: 
46:     public void saveAll(Iterable<Aluno> alunos) {
47:         alunoRepository.saveAll(alunos);
48:     }
49: }
```

* **Linha 9 (`@Service`):** Marca a classe como Bean de lógica de negócio.
* **Linhas 14-16:** Injeção do `AlunoRepository` via construtor.
* **Linhas 34-40 (`deleteById`):** Regra defensiva: verifica a existência antes de tentar excluir, retornando `true` (sucesso) ou `false` (não encontrado).

---

### 5.5. `AlunoController.java` (Controlador REST e Endpoints HTTP)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/controller/AlunoController.java`

```java
1: package com.thehecklers.sburrestdemo.controller;
2: 
3: import com.thehecklers.sburrestdemo.model.Aluno;
4: import com.thehecklers.sburrestdemo.service.AlunoService;
5: import org.springframework.http.HttpStatus;
6: import org.springframework.http.ResponseEntity;
7: import org.springframework.web.bind.annotation.*;
8: 
9: @CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
10: @RestController
11: @RequestMapping("/alunos")
12: public class AlunoController {
13: 
14:     private final AlunoService alunoService;
15: 
16:     public AlunoController(AlunoService alunoService) {
17:         this.alunoService = alunoService;
18:     }
19: 
20:     @GetMapping
21:     public Iterable<Aluno> getAlunos() {
22:         return alunoService.findAll();
23:     }
24: 
25:     @GetMapping("/{id}")
26:     public ResponseEntity<Aluno> getAlunoById(@PathVariable String id) {
27:         return alunoService.findById(id)
28:                 .map(ResponseEntity::ok)
29:                 .orElse(ResponseEntity.notFound().build());
30:     }
31: 
32:     @PostMapping
33:     public ResponseEntity<Aluno> postAluno(@RequestBody Aluno aluno) {
34:         Aluno saved = alunoService.save(aluno);
35:         return new ResponseEntity<>(saved, HttpStatus.CREATED);
36:     }
37: 
38:     @PutMapping("/{id}")
39:     public ResponseEntity<Aluno> putAluno(@PathVariable String id, @RequestBody Aluno aluno) {
40:         aluno.setId(id);
41:         boolean exists = alunoService.existsById(id);
42:         Aluno saved = alunoService.save(aluno);
43:         return exists ? ResponseEntity.ok(saved) : new ResponseEntity<>(saved, HttpStatus.CREATED);
44:     }
45: 
46:     @DeleteMapping("/{id}")
47:     public ResponseEntity<Void> deleteAluno(@PathVariable String id) {
48:         return alunoService.deleteById(id)
49:                 ? ResponseEntity.noContent().build()
50:                 : ResponseEntity.notFound().build();
51:     }
52: }
```

* **Linha 9 (`@CrossOrigin`):** Permite requisições originadas de outras portas locais (ex: Live Server `5500`).
* **Linha 10 (`@RestController`):** Combinação de `@Controller` com `@ResponseBody`, instruindo o Spring a serializar o retorno dos métodos em formato **JSON**.
* **Linha 11 (`@RequestMapping("/alunos")`):** Rota base para todas as operações.
* **Linhas 20-23 (`GET /alunos`):** Lista todos os alunos cadastrados com status `200 OK`.
* **Linhas 25-30 (`GET /alunos/{id}`):** Usa `@PathVariable` para extrair o ID da URL. Se encontrar, retorna `200 OK`; se não, retorna `404 NOT FOUND`.
* **Linhas 32-36 (`POST /alunos`):** Usa `@RequestBody` para converter o corpo JSON em `Aluno` e retorna status **201 CREATED**.
* **Linhas 38-44 (`PUT /alunos/{id}`):** Se o registro já existia, atualiza e devolve `200 OK`. Se não existia, cria e devolve `201 CREATED`.
* **Linhas 46-51 (`DELETE /alunos/{id}`):** Se deletou, retorna **204 NO CONTENT** (sucesso sem corpo); se o aluno não existia, retorna **404 NOT FOUND**.

---

### 5.6. `SburRestDemoApplication.java` (Ponto de Entrada e Carga Inicial)

Arquivo: `src/main/java/com/thehecklers/sburrestdemo/SburRestDemoApplication.java`

```java
1: package com.thehecklers.sburrestdemo;
2: 
3: import com.thehecklers.sburrestdemo.model.Aluno;
4: import com.thehecklers.sburrestdemo.service.AlunoService;
5: import jakarta.annotation.PostConstruct;
6: import org.springframework.boot.SpringApplication;
7: import org.springframework.boot.autoconfigure.SpringBootApplication;
8: 
9: import java.util.List;
10: 
11: @SpringBootApplication
12: public class SburRestDemoApplication {
13: 
14:     private final AlunoService alunoService;
15: 
16:     public SburRestDemoApplication(AlunoService alunoService) {
17:         this.alunoService = alunoService;
18:     }
19: 
20:     public static void main(String[] args) {
21:         SpringApplication.run(SburRestDemoApplication.class, args);
22:     }
23: 
24:     @PostConstruct
25:     private void loadData() {
26:         if (alunoService.count() == 0) {
27:             alunoService.saveAll(List.of(
28:                     new Aluno("Ana Silva", "ana.silva@faculdade.edu", "Engenharia de Software"),
29:                     new Aluno("Bruno Santos", "bruno.santos@faculdade.edu", "Ciência da Computação"),
30:                     new Aluno("Carla Oliveira", "carla.oliveira@faculdade.edu", "Sistemas de Informação"),
31:                     new Aluno("Diego Ferreira", "diego.ferreira@faculdade.edu", "Engenharia de Software")
32:             ));
33:         }
34:     }
35: }
```

* **Linha 11 (`@SpringBootApplication`):** Habilita a configuração automática e o escaneamento de componentes nos subpacotes (`model`, `repository`, `service`, `controller`).
* **Linhas 24-34 (`@PostConstruct`):** Método executado automaticamente após o Spring inicializar o contexto. Verifica se o banco está vazio (`count() == 0`) e insere 4 alunos de exemplo para testes.

---

### 5.7. `application.properties` (Configuração do DataSource e H2)

Arquivo: `src/main/resources/application.properties`

```properties
1: spring.h2.console.enabled=true
2: spring.h2.console.path=/h2-console
3: spring.datasource.url=jdbc:h2:mem:academicodb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
4: spring.datasource.driverClassName=org.h2.Driver
5: spring.datasource.username=sa
6: spring.datasource.password=
7: spring.sql.init.mode=always
```

* **Linha 1-2:** Habilita a interface web do banco H2 em `/h2-console`.
* **Linha 3:** Conecta ao banco relacional em memória `academicodb`. Os parâmetros `DB_CLOSE_DELAY=-1` e `DB_CLOSE_ON_EXIT=FALSE` garantem que o banco permaneça vivo durante toda a execução da aplicação.
* **Linha 4:** Driver oficial do H2 (`org.h2.Driver`).
* **Linha 7 (`spring.sql.init.mode=always`):** Garante que o Spring execute o script `schema.sql` sempre que a aplicação subir, criando a tabela `aluno`.

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
    // ...
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
     ```sql
     SELECT * FROM aluno WHERE curso = 'Engenharia de Software';
     ```
2. **Teste a API com o Postman:**
   - Importe a collection [`Sistema_Academico_Alunos.postman_collection.json`](./postman/Sistema_Academico_Alunos.postman_collection.json).
   - Dispare requisições `GET`, `POST`, `PUT` e `DELETE` e compare os códigos de status HTTP retornados.
3. **Exercício Prático de Fixação:**
   - Adicione um novo campo `telefone VARCHAR(20)` na tabela `aluno` em `schema.sql`.
   - Atualize a classe `Aluno.java`, o `RowMapper` no `AlunoRepository.java`, e teste salvar e listar o novo campo!
