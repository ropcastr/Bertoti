# Guia Detalhado: Migração de ArrayList para Banco de Dados no Spring Boot

Este documento analisa o projeto atual **sbur-rest-demo** e explica passo a passo, em detalhes didáticos, como substituir a lista em memória (`ArrayList`) por um Banco de Dados Relacional utilizando **Spring Data JPA** e **H2 Database** (ou PostgreSQL/MySQL).

---

## 1. Análise do Projeto Atual

No código atual ([SburRestDemoApplication.java](file:///c:/Users/Rodrigo/Documents/IdeaProjects/bertoti-lab-3-main/src/main/java/com/thehecklers/sburrestdemo/SburRestDemoApplication.java)), a aplicação guarda a lista de cafés na memória RAM utilizando um `ArrayList`:

```java
private List<Coffee> coffees = new ArrayList<>();
```

### Por que o `ArrayList` não é ideal?
1. **Volatilidade (Perda de Dados):** Toda vez que a aplicação é reiniciada ou desligada, todos os cafés cadastrados, alterados ou removidos são perdidos, voltando para a lista inicial do construtor.
2. **Concorrência (Segurança de Threads):** Se múltiplos usuários fizerem requisições HTTP ao mesmo tempo (ex: inserindo e deletando itens simultaneamente), o `ArrayList` pode causar exceções (`ConcurrentModificationException`) ou corrupção de dados.
3. **Escala e Desempenho:** Se a lista tiver 1 milhão de itens, a busca por ID precisa percorrer item por item na memória RAM. Um banco de dados faz buscas indexadas instantâneas.

---

## 2. Conceitos Importantes antes de Mudar o Código

Para integrar um Banco de Dados no Spring Boot de forma moderna, utilizamos a especificação **JPA (Java Persistence API)** através do **Spring Data JPA** e **Hibernate**.

- **JPA:** Uma especificação Java que define como objetos Java devem ser salvos em tabelas do banco de dados (ORM - Mapeamento Objeto-Relacional).
- **Hibernate:** A ferramenta que implementa a JPA por baixo dos panos. Ela gera o código SQL (`CREATE TABLE`, `INSERT`, `SELECT`, `UPDATE`, `DELETE`) automaticamente.
- **Spring Data JPA:** Uma camada do Spring que reduz drasticamente o código Java necessário, permitindo criar interfaces que já vêm com métodos prontos para consultar, salvar e deletar dados.
- **H2 Database:** Banco de dados relacional em memória. É perfeito para desenvolvimento e aprendizado, pois roda direto na aplicação sem precisar instalar nada no computador.

---

## 3. Passo a Passo Completo da Migração

---

### Passo 1: Atualizar o arquivo `pom.xml` (Dependências Maven)

Para utilizar o banco de dados, precisamos adicionar duas dependências ao arquivo [pom.xml](file:///c:/Users/Rodrigo/Documents/IdeaProjects/bertoti-lab-3-main/pom.xml):

```xml
<dependencies>
    <!-- Dependência Web (já existente) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 1. NOVA DEPENDÊNCIA: Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- 2. NOVA DEPENDÊNCIA: Banco de Dados H2 -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Dependência de Teste (já existente) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### Explicação linha por linha das novas dependências:
- `<artifactId>spring-boot-starter-data-jpa</artifactId>`: Traz o Hibernate, JPA e o Spring Data para dentro do projeto.
- `<artifactId>h2</artifactId>`: Fornece o driver de conexão com o banco de dados em memória H2.
- `<scope>runtime</scope>`: Indica que o driver só precisa estar disponível durante a execução da aplicação.

---

### Passo 2: Configurar o Banco em `application.properties`

No arquivo `src/main/resources/application.properties`, adicionamos as configurações para o Spring reconhecer o banco H2 e criar um painel visual (console web):

```properties
# Habilita o console do banco H2 no navegador (http://localhost:8080/h2-console)
spring.h2.console.enabled=true

# Define o caminho do console H2
spring.h2.console.path=/h2-console

# Nome do banco de dados em memória
spring.datasource.url=jdbc:h2:mem:coffeedb

# Driver JDBC do H2
spring.datasource.driverClassName=org.h2.Driver

# Usuário padrão do banco
spring.datasource.username=sa

# Senha padrão (vazia)
spring.datasource.password=

# Instrui o Hibernate a criar/atualizar as tabelas do banco automaticamente baseado nas nossas entidades Java
spring.jpa.hibernate.ddl-auto=update

# Mostra no console do Terminal os comandos SQL gerados pelo Hibernate
spring.jpa.show-sql=true
```

#### Explicação do porquê de cada propriedade:
- `spring.h2.console.enabled=true`: Permite acessar um painel web no navegador para ver as tabelas e rodar queries SQL de teste.
- `spring.datasource.url=jdbc:h2:mem:coffeedb`: Garante que a URL do banco seja fixa (`coffeedb`), facilitando a conexão pelo console.
- `spring.jpa.hibernate.ddl-auto=update`: Toda vez que a aplicação sobe, o Hibernate lê as anotações `@Entity` e cria a tabela `COFFEE` no banco automaticamente se ela não existir.
- `spring.jpa.show-sql=true`: Excelente para aprendizado, pois imprime no console todo o comando SQL que o Spring gera ao salvar ou buscar dados.

---

### Passo 3: Transformar a classe `Coffee` em uma Entidade JPA

Atualmente a classe `Coffee` é um POJO simples. Para ser salva em banco de dados, ela precisa ser anotada como uma Entidade JPA.

#### Código Como Ficaria:

```java
package com.thehecklers.sburrestdemo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Coffee {

    @Id
    private String id;
    private String name;

    // Construtor sem argumentos (Obrigatório para o JPA/Hibernate)
    public Coffee() {
    }

    public Coffee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

#### Explicação linha por linha:
1. `@Entity`: Informa ao Spring Boot e ao Hibernate que essa classe Java representa uma **tabela no banco de dados** (por padrão, a tabela se chamará `COFFEE`).
2. `@Id`: Marca o atributo `id` como a **Chave Primária (Primary Key)** da tabela no banco de dados.
3. `private String id;` (Removido o `final`): O Hibernate precisa de atributos mutáveis para conseguir instanciar objetos a partir de linhas retornadas do banco via *reflection*.
4. `public Coffee() {}`: **Construtor padrão/vazio**. O Hibernate EXIGE esse construtor sem parâmetros para recriar objetos lidos das tabelas SQL.
5. `public void setId(String id)`: Adicionado o setter do ID, permitindo que a JPA ajuste ou atribua o identificador caso necessário.

---

### Passo 4: Criar a Interface `CoffeeRepository`

No padrão Spring Data, não escrevemos código para conectar ao banco nem SQL manual para operações básicas (CRUD - Create, Read, Update, Delete). Criamos uma **interface** estendendo `CrudRepository` ou `JpaRepository`.

#### Código Como Ficaria:

```java
package com.thehecklers.sburrestdemo;

import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, String> {
}
```

#### Explicação linha por linha:
1. `public interface CoffeeRepository`: Declaramos uma interface (e não uma classe).
2. `extends CrudRepository<Coffee, String>`:
   - O primeiro parâmetro (`Coffee`) indica qual **Entidade** este repositório gerencia.
   - O segundo parâmetro (`String`) indica o tipo de dado da **Chave Primária (`@Id`)** da entidade.
3. **Como funciona a "mágica"?** O Spring Data JPA cria uma implementação em tempo de execução dessa interface. Ela injeta automaticamente métodos como:
   - `findAll()`: Retorna um `Iterable<Coffee>` com todos os registros do banco.
   - `findById(id)`: Retorna um `Optional<Coffee>` contendo o café se encontrado.
   - `save(coffee)`: Salva um novo café ou atualiza se o ID já existir.
   - `deleteById(id)`: Deleta o registro pelo ID.
   - `existsById(id)`: Verifica se determinado registro existe.

---

### Passo 5: Atualizar o Controller (`RestApiDemoController`)

Agora substituímos a lista `ArrayList` pela interface `CoffeeRepository` usando **Injeção de Dependência**.

#### Código Como Ficaria:

```java
package com.thehecklers.sburrestdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/coffees")
class RestApiDemoController {

    // Em vez de ArrayList, declaramos o repositório do banco de dados
    private final CoffeeRepository coffeeRepository;

    // Injeção de dependência via Construtor
    public RestApiDemoController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    // Método para popular dados iniciais quando a aplicação subir
    @PostConstruct
    private void loadData() {
        if (coffeeRepository.count() == 0) {
            coffeeRepository.saveAll(List.of(
                    new Coffee("Café Cereza"),
                    new Coffee("Café Ganador"),
                    new Coffee("Café Lareño"),
                    new Coffee("Café Três Pontas")
            ));
        }
    }

    // 1. GET - Buscar todos os cafés
    @GetMapping
    Iterable<Coffee> getCoffees() {
        return coffeeRepository.findAll();
    }

    // 2. GET/{id} - Buscar café por ID
    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        return coffeeRepository.findById(id);
    }

    // 3. POST - Cadastrar novo café
    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    // 4. PUT/{id} - Atualizar café existente ou criar caso não exista
    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        return coffeeRepository.existsById(id)
                ? new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK)
                : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED);
    }

    // 5. DELETE/{id} - Remover café pelo ID
    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffeeRepository.deleteById(id);
    }
}
```

#### Explicação linha por linha do novo Controller:

1. `private final CoffeeRepository coffeeRepository;`
   - Substitui a declaração do `ArrayList`. O atributo é `final` garantindo que seja inicializado no construtor.

2. `public RestApiDemoController(CoffeeRepository coffeeRepository) { ... }`
   - O Spring detecta que o Controller precisa de `CoffeeRepository` e passa automaticamente uma instância (Injeção de Dependência).

3. `@PostConstruct private void loadData() { ... }`
   - Executa logo após a criação do bean. Substitui a inserção manual no construtor antigo.
   - `coffeeRepository.count() == 0`: Verifica se o banco está vazio.
   - `coffeeRepository.saveAll(...)`: Insere a lista inicial de cafés na tabela SQL através do comando `INSERT INTO COFFEE ...`.

4. `@GetMapping Iterable<Coffee> getCoffees()`
   - `coffeeRepository.findAll()` substitui o retorno da lista em memória. Executa internamente `SELECT * FROM COFFEE`.

5. `@GetMapping("/{id}") Optional<Coffee> getCoffeeById(@PathVariable String id)`
   - `coffeeRepository.findById(id)` substitui o laço `for (Coffee c : coffees)`. Executa internamente `SELECT * FROM COFFEE WHERE ID = ?`. O próprio Spring Data já retorna um `Optional<Coffee>`.

6. `@PostMapping Coffee postCoffee(@RequestBody Coffee coffee)`
   - `coffeeRepository.save(coffee)` substitui `coffees.add(coffee)`. Executa `INSERT INTO COFFEE (ID, NAME) VALUES (?, ?)`.

7. `@PutMapping("/{id}") ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee)`
   - `coffeeRepository.existsById(id)` substitui a busca manual de índice no `ArrayList`.
   - Se o ID existe, atualiza no banco com código HTTP 200 (OK). Caso contrário, insere um novo com código HTTP 201 (CREATED).

8. `@DeleteMapping("/{id}") void deleteCoffee(@PathVariable String id)`
   - `coffeeRepository.deleteById(id)` substitui `coffees.removeIf(...)`. Executa internamente `DELETE FROM COFFEE WHERE ID = ?`.

---

## 4. Comparativo Directo: ArrayList vs. Banco de Dados

| Operação | Como estava (ArrayList) | Como fica (Spring Data JPA) | O que acontece por trás (SQL) |
| :--- | :--- | :--- | :--- |
| **Buscar Todos** | `return coffees;` | `coffeeRepository.findAll()` | `SELECT * FROM coffee;` |
| **Buscar por ID** | Laço `for (Coffee c : coffees)` | `coffeeRepository.findById(id)` | `SELECT * FROM coffee WHERE id = ?;` |
| **Salvar / Inserir** | `coffees.add(coffee)` | `coffeeRepository.save(coffee)` | `INSERT INTO coffee VALUES (?, ?);` |
| **Atualizar** | `coffees.set(index, coffee)` | `coffeeRepository.save(coffee)` | `UPDATE coffee SET name = ? WHERE id = ?;` |
| **Deletar** | `coffees.removeIf(...)` | `coffeeRepository.deleteById(id)` | `DELETE FROM coffee WHERE id = ?;` |

---

## 5. Como Testar a Aplicação e o Banco de Dados no Postman (Passo a Passo)

Após iniciar a aplicação através da classe [SburRestDemoApplication.java](file:///c:/Users/Rodrigo/Documents/IdeaProjects/bertoti-lab-3-main/src/main/java/com/thehecklers/sburrestdemo/SburRestDemoApplication.java), todas as rotas REST da API estarão acessíveis em `http://localhost:8080/coffees`.

Abaixo está o guia prático passo a passo para testar os endpoints e validar a persistência no banco de dados usando o **Postman**.

---

### Preparação no Postman
1. Abra o aplicativo **Postman**.
2. (Opcional) Crie uma nova Collection clicando no botão **+** -> **New Collection** e renomeie para `SBUR REST Demo`.

---

### Requisição 1: GET - Listar Todos os Cafés

- **Verbo HTTP:** `GET`
- **URL:** `http://localhost:8080/coffees`
- **Headers:** Não requer headers adicionais.
- **Passos:**
  1. Selecione a opção **GET** no dropdown de métodos HTTP.
  2. Insira a URL `http://localhost:8080/coffees`.
  3. Clique no botão **Send**.
- **Resposta Esperada:** Status `200 OK` e um JSON contendo a lista dos 4 cafés pré-carregados pelo `@PostConstruct`:
  ```json
  [
      {
          "id": "c6204c3e-2fdf-4e2b-a010-3c13b3558f00",
          "name": "Café Cereza"
      },
      {
          "id": "7662c9bb-3642-4fdf-9fb7-2c9e37ec6bd8",
          "name": "Café Ganador"
      },
      {
          "id": "e6a0d644-8d4e-4f05-8fb9-9689b7cf68c2",
          "name": "Café Lareño"
      },
      {
          "id": "9dfc4db6-5389-4a0b-9d41-3b7c4a1e9447",
          "name": "Café Três Pontas"
      }
  ]
  ```
> **Dica:** Copie um dos valores de `"id"` gerados na resposta para utilizar nos testes de GET por ID, PUT e DELETE a seguir.

---

### Requisição 2: GET por ID - Buscar um Café Específico

- **Verbo HTTP:** `GET`
- **URL:** `http://localhost:8080/coffees/{ID_COPIADO}` (Ex: `http://localhost:8080/coffees/c6204c3e-2fdf-4e2b-a010-3c13b3558f00`)
- **Passos:**
  1. Selecione a opção **GET**.
  2. Cole o ID do café ao final da URL.
  3. Clique no botão **Send**.
- **Resposta Esperada:** Status `200 OK` e o JSON com as informações do café solicitado:
  ```json
  {
      "id": "c6204c3e-2fdf-4e2b-a010-3c13b3558f00",
      "name": "Café Cereza"
  }
  ```

---

### Requisição 3: POST - Cadastrar um Novo Café

- **Verbo HTTP:** `POST`
- **URL:** `http://localhost:8080/coffees`
- **Configuração no Postman:**
  1. Selecione o método **POST**.
  2. Vá para a aba **Body**.
  3. Selecione a opção **raw**.
  4. No menu dropdown à direita (onde diz *Text*), altere para **JSON**.
  5. Insira o seguinte JSON no corpo da requisição:
     ```json
     {
         "name": "Café Gourmet Sul de Minas"
     }
     ```
  6. Clique no botão **Send**.
- **Resposta Esperada:** Status `200 OK` e a API retornará o registro criado com seu `id` (UUID) gerado automaticamente:
  ```json
  {
      "id": "f51270d1-12cd-4389-9a22-83b6329c368d",
      "name": "Café Gourmet Sul de Minas"
  }
  ```

---

### Requisição 4: PUT - Atualizar Café Existente ou Inserir Novo (Upsert)

#### 4.1. Atualizando um café existente (Status 200 OK):
- **Verbo HTTP:** `PUT`
- **URL:** `http://localhost:8080/coffees/{ID_COPIADO}`
- **Configuração no Postman:**
  1. Selecione o método **PUT**.
  2. Na aba **Body** -> **raw** -> **JSON**, envie o objeto atualizado incluindo o ID correspondente:
     ```json
     {
         "id": "c6204c3e-2fdf-4e2b-a010-3c13b3558f00",
         "name": "Café Cereza Premium Especial"
     }
     ```
  3. Clique em **Send**.
- **Resposta Esperada:** Status `200 OK` com o objeto atualizado no banco de dados.

#### 4.2. Criando um café novo via PUT quando o ID não existe (Status 201 CREATED):
- **Verbo HTTP:** `PUT`
- **URL:** `http://localhost:8080/coffees/cafe-123-customizado`
- **Body JSON:**
  ```json
  {
      "id": "cafe-123-customizado",
      "name": "Café Arábica Especial"
  }
  ```
- **Resposta Esperada:** Status `201 Created` e o objeto persistido.

---

### Requisição 5: DELETE - Remover um Café pelo ID

- **Verbo HTTP:** `DELETE`
- **URL:** `http://localhost:8080/coffees/{ID_COPIADO}`
- **Passos:**
  1. Selecione a opção **DELETE**.
  2. Cole o ID do café que deseja remover ao final da URL.
  3. Clique no botão **Send**.
- **Resposta Esperada:** Status `200 OK` com corpo vazio.
- **Validação:** Ao realizar uma nova busca `GET http://localhost:8080/coffees`, o café removido não constará mais na lista.

---

## 6. Verificação Visual no Banco de Dados (Console Web H2)

Para confirmar que os testes realizados via Postman alteraram a tabela do banco de dados relacional em tempo real:

1. Acesse no seu navegador: `http://localhost:8080/h2-console`
2. Preencha os campos de conexão conforme configurado no `application.properties`:
   - **JDBC URL:** `jdbc:h2:mem:coffeedb`
   - **User Name:** `sa`
   - **Password:** *(deixe em branco)*
3. Clique em **Connect**.
4. Digite a consulta SQL no painel de execução:
   ```sql
   SELECT * FROM COFFEE;
   ```
5. Clique em **Run**. Você verá todas as alterações (novos registros inseridos via POST, nomes editados via PUT e linhas removidas via DELETE) refletidas diretamente na tabela `COFFEE`!

