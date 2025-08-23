# Engenharia de Software 💻 — Prof. Betoti 👨‍🏫
---

## Atividades de 1 a 6

### 📑 Índice
1. [Comentários sobre o Livro *Software Engineering at Google*](#comentários-sobre-o-livro-software-engineering-at-google-oreilly)
   - [Trecho 1: O que é Engenharia de Software?](#trecho-1-o-que-é-engenharia-de-software)
   - [Trecho 2: Programação ao Longo do Tempo](#trecho-2-programação-ao-longo-do-tempo)
2. [Exemplos de Trade-offs](#exemplos-de-trade-offs)
   - [1. Velocidade vs Qualidade](#1-velocidade-vs-qualidade)
   - [2. Escalabilidade vs Simplicidade](#2-escalabilidade-vs-simplicidade)
   - [3. Custo vs Manutenção](#3-custo-vs-manutenção)
3. [Tabela Resumo](#tabela-resumo)
4. [Conclusão](#conclusão)
5. [Diagramas e Classes](#Diagramas-e-Estudo-de-Classes)

---

## 📌 Sobre o Projeto
Atividades sobre os conteúdos do livro **Software Engineering at Google**  práticas e exemplos.

---

## 📚 Comentários sobre o Livro *Software Engineering at Google*, Oreilly

### 📖 Trecho 1: O que é Engenharia de Software?
O livro diferencia “apenas programar” de **ser engenheiro de software**.  
- Programar é como aprender a andar de bicicleta: útil, mas limitado.  
- Engenharia de software é como projetar uma ponte ou avião: exige rigor, teoria e responsabilidade.  
- Hoje, o software está em tudo — de smartphones a carros autônomos — e isso exige **boas práticas e confiabilidade**.

> “Engenheiros clássicos seguem regras rígidas para não derrubar pontes; no software, precisamos do mesmo nível de rigor.”

---

### ⏳ Trecho 2: Programação ao Longo do Tempo
Fazer software não é só escrever um *Hello World*.  
Envolve **ferramentas**, **processos** e **estratégia** para garantir que o código dure.  
O livro traz três pilares fundamentais:

1. **Tempo e Mudança** — como o código se adapta ao futuro.  
2. **Escala e Crescimento** — como lidar quando o sistema fica gigante.  
3. **Trade-offs e Custos** — como decidir o que vale a pena fazer.

💡 **Reflexão:** Programar pode não ser só técnica, mas também estratégia!

---

## ⚖️ Exemplos de Trade-offs

### 1️⃣ Velocidade vs Qualidade
**Descrição:**  
Optar por entregar rápido pode significar menos testes e mais riscos. Focar na qualidade demanda tempo, mas reduz problemas no futuro.

**Exemplos práticos:**
- **MVP com frameworks ágeis** → **Ruby on Rails** ou **Django** para lançar rápido.  
- **Sistemas críticos** → **Java com Spring** ou **Go** para desempenho e segurança.  
- **Dívida técnica** → Ignorar testes acelera entrega, mas aumenta custo futuro.  
- [Estudo](https://arxiv.org/abs/2203.04374?utm_source=chatgpt.com): código de baixa qualidade tem 15× mais defeitos.

---

### 2️⃣ Escalabilidade vs Simplicidade
**Descrição:**  
Soluções simples são rápidas de implementar, mas podem não suportar crescimento massivo.

**Exemplos práticos:**
- **SQL (PostgreSQL, MySQL)** → Consistência e robustez.  
- **NoSQL (MongoDB, Cassandra)** → Escalabilidade horizontal e flexibilidade.  
- **Monolito** → Mais simples no início.  
- **Microsserviços** → Mais complexos, mas escalam melhor.  
- [Caso Google](https://abseil.io/resources/swe-book/html/ch06.html?utm_source=chatgpt.com): melhorias de qualidade nos resultados aumentaram a latência, exigindo reavaliação.

---

### 3️⃣ Custo vs Manutenção
**Descrição:**  
Escolhas de baixo custo inicial podem gerar alto custo de manutenção no futuro.

**Exemplos práticos:**
- **Build vs Buy**  
  - Build → Open-source ou solução interna (baixo custo inicial, alta manutenção).  
  - Buy → SaaS/API (custo mensal, suporte e atualização garantidos).  
  - [Exemplo real](https://aakashgupta.medium.com/the-product-leaders-guide-to-buying-vs-building-software-a67a87bfca04?utm_source=chatgpt.com).
- **Infraestrutura gerenciada vs autogerenciada**  
  - Serverless/PaaS (AWS Lambda, GCP App Engine) → Menos manutenção, mais custo unitário.  
  - IaaS (AWS EC2, GCP Compute Engine) → Mais controle, exige manutenção.

---

## 📊 Tabela Resumo

| Trade-off                   | Exemplo Rápido                                        | Implicação                                          |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------|
| Velocidade vs Qualidade     | MVP com Rails vs Go/Java + testes                     | Qualidade reduz custo e acelera manutenção          |
| Escalabilidade vs Simplicidade | SQL vs NoSQL; Monolito vs Microsserviços            | Complexidade permite escala, mas exige mais esforço |
| Custo vs Manutenção         | Build (open-source) vs Buy (SaaS/API)                  | Build é barato no início, caro no longo prazo       |
| Infraestrutura              | Serverless/PaaS vs IaaS                               | PaaS reduz manutenção, IaaS exige mais gestão       |

---

## 🎯 Conclusão
A essência do livro é clara:  
> **Não existe solução perfeita — existe a solução certa para o momento e contexto.**

Engenharia de software exige **equilíbrio**, **estratégia** e **capacidade de adaptação**.  
Os trade-offs não são obstáculos, mas sim decisões que moldam o futuro do sistema.

📌 Mais detalhes no capítulo de trade-offs do  
[Software Engineering at Google](https://abseil.io/resources/swe-book/html/ch06.html?utm_source=chatgpt.com)

---

## Diagramas e Estudo de Classes

### Diagrama UML para um estacionamento - ![Static Badge](https://img.shields.io/badge/Plant-UML-blue?style=plastic&logo=UML&logoSize=auto&labelColor=b22222)
<img src="estacionamento/Diagrama_Estacionamento.png" alt="Texto alternativo" width="400"/>

### Classes para um estacionamento - ![Static Badge](https://img.shields.io/badge/Java-code-brightgreen?style=plastic&logo=Java&logoSize=auto&labelColor=%23FFFF00) 

#### Classe Carro
~~~java
package fatec.gov.br.atividades.estacionamento;

import java.util.Objects;

public class Carro {
    private String placa;
    private String modelo;
    private String cor;
    private int ano;

    public Carro(String placa, String modelo, String cor, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Carro [placa=" + placa + ", modelo=" + modelo + ", cor=" + cor + ", ano=" + ano + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carro)) return false;
        Carro carro = (Carro) o;
        return Objects.equals(placa, carro.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa);
    }
}

~~~


#### Classe Estacionamento
~~~java
package fatec.gov.br.atividades.estacionamento;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private List<Carro> carros = new ArrayList<>();

    public void adicionarCarro(Carro carro) {
        carros.add(carro);
    }

    public boolean removerCarro(String placa) {
        return carros.removeIf(c -> c.getPlaca().equals(placa));
    }

    public Carro buscarCarro(String placa) {
        return carros.stream()
                     .filter(c -> c.getPlaca().equals(placa))
                     .findFirst()
                     .orElse(null);
    }

    public List<Carro> getCarros() {
        return carros;
    }
}

~~~


#### Teste JUnit
~~~java
package fatec.gov.br.atividades.estacionamento;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Teste_Estacionamento {

    @Test
    void testAdicionarCarro() {
        Estacionamento est = new Estacionamento();
        est.adicionarCarro(new Carro("ABC1234", "Fusca", "Azul", 1976));
        assertEquals(1, est.getCarros().size());
    }

    @Test
    void testRemoverCarro() {
        Estacionamento est = new Estacionamento();
        est.adicionarCarro(new Carro("XYZ9876", "Civic", "Prata", 2020));
        boolean removido = est.removerCarro("XYZ9876");
        assertTrue(removido);
        assertEquals(0, est.getCarros().size());
    }

    @Test
    void testBuscarCarro() {
        Estacionamento est = new Estacionamento();
        Carro c = new Carro("AAA1111", "Corolla", "Preto", 2019);
        est.adicionarCarro(c);

        Carro encontrado = est.buscarCarro("AAA1111");
        assertNotNull(encontrado);
        assertEquals("Corolla", encontrado.getModelo());
    }
}

~~~

### Relatório de Teste - Surefire

📊 [Relatório de Testes - Surefire](https://ropcastr.github.io/Bertoti/surefire.html)


![Build Status](https://github.com/ropcastr/Bertoti/actions/workflows/maven.yml/badge.svg)
