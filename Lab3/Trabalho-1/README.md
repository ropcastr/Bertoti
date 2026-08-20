# 🎓 Sistema Acadêmico - API REST & Spring Boot JDBC

Aplicação didática para o ensino de **Java**, **Spring Boot**, **Spring JDBC (JdbcTemplate)**, **SQL Explícito** e **Banco de Dados Relacional H2** para estudantes de Banco de Dados e Engenharia de Software.

---

## 📋 Requisitos Prévios
- **Java 17+** (configurado para Java 24 / compatível)
- **Maven** (não precisa instalar nada além; os wrappers `./mvnw` e `mvnw.cmd` já estão inclusos no projeto)

---

## 🚀 Passo a Passo: Como Rodar e Acessar a Aplicação

### 🔹 Passo 1: Abrir o Terminal e Entrar na Pasta do Projeto
Abra o seu terminal (PowerShell, Prompt de Comando, Bash ou o terminal integrado da IDE) e navegue até a pasta:
```bash
cd Lab3/Trabalho-1
```

---

### 🔹 Passo 2: Iniciar o Servidor Spring Boot
Execute o comando correspondente ao seu sistema operacional:

* 🪟 **No Windows (PowerShell ou Prompt de Comando - CMD):**
  ```powershell
  .\mvnw.cmd clean spring-boot:run
  ```
  *(Dica: Se você já tiver o Maven instalado globalmente no Windows, também pode usar: `mvn clean spring-boot:run`)*

* 🐧🍎 **No Linux, macOS ou Git Bash:**
  ```bash
  ./mvnw clean spring-boot:run
  ```

---

### 🔹 Passo 3: Aguardar a Inicialização
Acompanhe os logs no terminal. Quando você visualizar a mensagem de sucesso abaixo, significa que o servidor web Tomcat e o banco de dados H2 já estão ativos e prontos para receber requisições:
```text
Tomcat started on port 8080 (http) with context path '/'
Started SburRestDemoApplication in X.XXX seconds
```

---

### 🔹 Passo 4: Abrir no Navegador Web (Interface Visual)
Com o terminal rodando em segundo plano, abra o seu navegador de preferência (Google Chrome, Edge, Firefox) e acesse:

👉 **[http://localhost:8080/](http://localhost:8080/)**

> 💡 **O que você verá na tela:** A interface visual interativa de **Gestão de Alunos**, permitindo matricular novos alunos, editar cadastros, excluir matrículas e pesquisar por nome/curso em tempo real.

---

### 🔹 Passo 5: Explorar o Banco de Dados e a API REST

Enquanto a aplicação estiver rodando, você também pode abrir novas abas no seu navegador para inspecionar os dados:

1. **🗃️ Console Web do Banco H2 (Visualizar Tabelas e Executar SQL):**
   * **URL de Acesso:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   * **JDBC URL:** `jdbc:h2:mem:academicodb`
   * **User Name:** `sa`
   * **Password:** *(deixe em branco)*
   * Clique no botão **"Connect"** para visualizar a tabela `ALUNO` e rodar consultas manuais como `SELECT * FROM ALUNO;`.

2. **🔌 API REST JSON:**
   * **URL de Acesso:** [http://localhost:8080/alunos](http://localhost:8080/alunos)
   * Exibe o JSON puro com todos os alunos cadastrados no banco relacional.

---

### 🔹 Passo 6: Como Encerrar a Aplicação
Para parar o servidor, volte ao terminal onde o comando está rodando e pressione o atalho:
* `Ctrl + C` (e confirme com `S` ou `Y` se solicitado).

---

## 🧪 Rodar os Testes Automatizados

Caso queira validar o funcionamento de todas as rotas e queries SQL sem precisar abrir o navegador:

* 🪟 **No Windows (PowerShell / CMD):**
  ```powershell
  .\mvnw.cmd test
  ```

* 🐧🍎 **No Linux / macOS / Git Bash:**
  ```bash
  ./mvnw test
  ```

---

## 📚 Documentação Técnica e Didática Disponível

- 📖 **[Guia Definitivo de Arquitetura e Código: Spring Boot com JDBC do Zero ao Banco de Dados](./ARQUITETURA_E_FUNCIONAMENTO_SPRING_BOOT.md)**: Explicação aprofundada, linha por linha, sobre arquitetura em camadas, pilares de Orientação a Objetos (Abstração, Encapsulamento, Herança, Polimorfismo e Sobrecarga), `JdbcTemplate`, `RowMapper`, comandos SQL explícitos (DDL, DML, DQL), injeção de dependência, endpoints REST, interface frontend e testes de integração com MockMvc.
