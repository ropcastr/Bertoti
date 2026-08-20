# Laboratório de Desenvolvimento em Banco de Dados III 🗄️ — Prof. Bertoti 👨‍🏫

Bem-vindo ao meu repositório da disciplina **Laboratório de Desenvolvimento em Banco de Dados III (Lab 3)** do curso **Tecnologia em Banco de Dados** na **FATEC SJC**.  
Aqui compartilho projetos práticos, códigos-fonte, estudos e documentações aprofundadas sobre persistência, manipulação e integração de bancos de dados relacionais com ecossistemas Java modernos.

> _"Banco de dados não é apenas armazenamento: é a espinha dorsal da integridade, consistência e performance dos sistemas."_

<br>

<a id="indice"></a>
## 📑 Índice
1. [Sobre a Disciplina](#sobre-a-disciplina)
2. [Projetos e Trabalhos](#projetos-e-trabalhos)
   - [Trabalho 1: Sistema Acadêmico (API REST & Spring Boot JDBC)](#trabalho-1-sistema-academico)
3. [Tecnologias e Ferramentas](#tecnologias-e-ferramentas)

<br>

<a id="sobre-a-disciplina"></a>
## 📌 Sobre a Disciplina
Este repositório contém os trabalhos e atividades práticas desenvolvidos na disciplina de **Laboratório de Desenvolvimento em Banco de Dados III**, ministrada pelo Prof. Bertoti na FATEC. O objetivo é aprofundar a integração entre linguagens de programação orientadas a objetos (Java) e sistemas gerenciadores de banco de dados (SGBDs), explorando:
* Acesso a dados de baixo nível e intermediário com **Spring JDBC (`JdbcTemplate`)**.
* Execução e parametrização explícita de comandos SQL (**DDL, DML e DQL**).
* Construção de APIs **RESTful** padronizadas e arquitetura de software em camadas (*Controller*, *Service*, *Repository* e *Model*).
* Testes automatizados de integração com **MockMvc** e interface web responsiva para consumo da API.

---
<br>

<a id="projetos-e-trabalhos"></a>
## 🚀 Projetos e Trabalhos

<a id="trabalho-1-sistema-academico"></a>
### 🎓 Trabalho 1: Sistema Acadêmico — API REST & Spring Boot JDBC

Aplicação completa de **Gestão Acadêmica de Alunos**, construída para ensinar a aplicação prática de conceitos de banco de dados relacional e orientação a objetos com **Java 24**, **Spring Boot 3**, **Spring JDBC (`JdbcTemplate`)**, **SQL Explícito** e **H2 Database em memória**.

A aplicação permite cadastrar, consultar, atualizar e excluir matrículas de alunos por meio de uma API REST e de uma interface visual integrada no navegador, permitindo também inspeção direta das tabelas SQL através do console web do H2.

#### 🔗 Links Principais do Projeto

* **📖 Documentação Técnica Completa (Guia Linha a Linha):**
    * [**Leia a `ARQUITETURA_E_FUNCIONAMENTO_SPRING_BOOT.md`**](/Lab3/Trabalho-1/ARQUITETURA_E_FUNCIONAMENTO_SPRING_BOOT.md)
    * Este guia detalha exaustivamente cada classe, método, comando SQL, pilares de POO (Abstração, Encapsulamento, Herança, Polimorfismo e Sobrecarga), ciclo de vida HTTP e suíte de testes.

* **🚀 Código-Fonte da Aplicação:**
    * [**Acesse a pasta Trabalho-1:**](/Lab3/Trabalho-1)
    * Código estruturado em camadas com POJOs limpos, repositórios DAO parametrizados e serviços desacoplados.

* **📘 Guia de Execução Rápida:**
    * [**Leia o `README.md` do Trabalho 1**](/Lab3/Trabalho-1/README.md)
    * Instruções práticas passo a passo para executar o projeto no Windows (`.\mvnw.cmd`) e Linux/macOS (`./mvnw`), com URLs de acesso visual.

* **📮 Coleção de Requisições Postman:**
    * [**Acesse a pasta postman:**](/Lab3/Trabalho-1/postman)
    * Coleção pronta com todas as requisições HTTP (`GET`, `POST`, `PUT`, `DELETE`) para teste da API.

---
<br>

<a id="tecnologias-e-ferramentas"></a>
## 🛠️ Tecnologias e Ferramentas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2%20Database-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![SQL](https://img.shields.io/badge/SQL-CC292B?style=for-the-badge&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
