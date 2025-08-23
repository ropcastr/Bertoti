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
5. [Diagramas e Classes](#Estudo-de-Classes)

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

##  Estudo de Classes - ![Static Badge](https://img.shields.io/badge/Java-code-brightgreen?style=plastic&logo=Java&logoSize=auto&labelColor=%23FFFF00) 

### Estacionamento

#### Classe Carro
~~~java
package estacionamento;

public class Carro {
	private String placa;
    private String marca;
    private String modelo;
    private String corCarro;

    public Carro(String placa, String marca, String modelo, String corCarro) {
    	this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.corCarro = corCarro;
    }
    
    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCorCarro() {
        return corCarro;
    }

    public void setCorCarro(String corCarro) {
        this.corCarro = corCarro;
    }

}
~~~


#### Classe Estacionamento
~~~java
package estacionamento;

import java.util.LinkedList;
import java.util.List;


public class Estacionamento {
	private List<Carro> carros = new LinkedList<Carro>();
	
	public void cadastrarCarro(Carro carro) {
		carros.add(carro);
	
	}
	
	public Carro buscarCarroPorPlaca(String placa) {
		for (Carro carro:carros) {
			if (carro.getPlaca().equals(placa)) {
				return carro;
			}
		}
		return null;
	}
	
	public List<Carro> getCarros(){
		return this.carros;
	}
}
~~~


### Teste JUnit
~~~java
package estacionamento;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class teste {

	@Test
	void test() {
		Estacionamento estacionamento = new Estacionamento();
		assertEquals(estacionamento.getCarros().size(), 0);
		
	}

}
~~~
