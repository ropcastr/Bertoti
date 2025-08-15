# Engenharia de Software 💻 - Prof. Betoti 👨‍🏫
Bem-vindo ao meu repositório do curso Tecnológia em Banco de Dados na FATE SJC, exploro conceitos da matéria de engenharia de software. Aqui você vai encontrar minhas impressões, estudos, exemplos práticos e afins. Bora mergulhar nisso?
 
---
 
## Sobre o Projeto  
Esse repositório é um exercício de reflexão sobre as aulas, exercícios, trabahos e interpretações sobre os conteúdos propostos em aula.
 
---
 
## Comentários sobre o Livro Software Engineering at Google, Oreilly  :book:
 
### Trecho 1: O que é Engenharia de Software?  
Neste trecho o livro tenta separar qual a diferença entre só "codar" e ser "engenheiro" de software, tentando mostrar como o software mexe com a vida real, que "programar" é tipo aprender a andar de bicicleta e em contra partida a "engenharia" tem um peso maior, tipo subir de nível, usando a teoria pra criar algo sólido e que não te deixa na mão. O texto ainda faz uma analogia com áreas tipo pontes e aviões, e nos faz pensar sobre a responsabilidade e o peso que temos em mãos, pois se uma ponte cai ou um avião despenca, a coisa fica feia e na minha visão, achei interessante como comparam os engenheiros "clássicos", que seguem regras rígidas pra não derrubar pontes, com programadores que segundo dizem, foram mais relaxados. Como o software tá em tudo hoje (pensa no teu celular ou num Tesla), eles dizem que precisamos de mais rigor e práticas confiáveis!
 
### Trecho 2: Programação ao Longo do Tempo  
O segundo trecho me fez repensar o que é "fazer software". Não é só codar um *Hello World* e pronto — envolve ferramentas e processos, tem um mundo de coisas, quase um ritual pra fazer o código durar. Os três mandamentos do livro – Tempo e Mudança, Tamanho e Boom, e Escolhas e Grana – Fazem lembrar como os softwares mudam, crescem, conflitam, se você escolhe desempenho, robustez ou tenta equilibrar isso damelhor forma possível (queria saber como o Google e as grandes empresas fazem isso, devem ter uns truques sinistros com aqueles sistemas gigantes deles). Isso me anima como aluno, mostra que software (e dados também) é um "bicho" que não para de mudar.
 
O livro foca em três pilares:  
- **Tempo e Mudança**: como o código se adapta ao futuro.  
- **Escala e Crescimento**: como lidar quando o sistema fica gigante.  
- **Trade-offs e Custos**: como decidir o que vale a pena fazer.  
 
Isso me traz a reflexão de que programar pode não ser só técnica, mas também estratégia!

 
## Exemplos de Trade-offs  
Trade-offs são escolhas que a gente faz em engenharia de software, pesando prós e contras.
 
### 1. Velocidade vs Qualidade

**Descrição:**  
Optar pela entrega rápida (MVP) pode significar código menos testado ou mais frágil; apostar na qualidade exige testes, revisão e boas práticas, mas reduz defeitos e custo de manutenção no longo prazo.

**Exemplos:**
- **MVP com frameworks ágeis**  
  Usar **Ruby on Rails** ou **Django** permite lançar um protótipo rápido. Por outro lado, sistemas críticos podem demandar **Java com Spring** ou **Go**, que oferecem desempenho, segurança, escalabilidade e tipagem forte.
- **Dívida técnica e testes**  
  Negligenciar testes automatizados acelera a entrega, mas gera complicações futuras. Um estudo mostrou que **códigos de baixa qualidade têm 15× mais defeitos e levam 124 % mais tempo para corrigir** ([arxiv.org](https://arxiv.org/abs/2203.04374?utm_source=chatgpt.com)).
- **Qualidade gera velocidade**  
  Relatórios do DORA e análises de [Jeff Kolesky](https://medium.com/%40jeffkole/the-fallacy-of-the-speed-cost-quality-trade-off-fdcd83b1c2a5?utm_source=chatgpt.com) mostram que equipes que priorizam qualidade tendem a evoluir mais rápido — velocidade e qualidade podem formar um ciclo virtuoso.


### 2. Escalabilidade vs Simplicidade

**Descrição:**  
Sistemas simples são rápidos de desenvolver e fáceis de entender, mas às vezes, para escalar, é preciso arquiteturas ou tecnologias mais complexas.

**Exemplos:**
- **SQL vs NoSQL**  
  - **SQL (PostgreSQL, MySQL):** robustos, conhecidos, ótimos para consistência.  
  - **NoSQL (MongoDB, Cassandra):** facilitam escalabilidade horizontal e alta flexibilidade, ideais para grandes volumes de dados.
- **Monolito vs Microsserviços**  
  - **Monolítico:** tudo num único deploy — simples inicialmente.  
  - **Microsserviços:** permitem escalar partes isoladamente, mas exigem orquestração, comunicação e rastreamento distribuído.
- **Exemplo real no Google**  
  Ao adicionar qualidade nos resultados de busca (imagens, features), o Google aumentou a latência. Isso os levou a reavaliar os trade-offs entre **qualidade**, **latência** e **capacidade** ([abseil.io](https://abseil.io/resources/swe-book/html/ch06.html?utm_source=chatgpt.com)).


### 3. Custo vs Manutenção

**Descrição:**  
Ferramentas baratas ou soluções “build in-house” podem parecer econômicas no curto prazo, mas trazem custos ocultos como manutenção, segurança e evolução do código.

**Exemplos:**
- **Build vs Buy**  
  - **Build:** usar código aberto ou construir internamente evita licenças, mas requer manutenção contínua.  
  - **Buy:** contratar solução (API/SaaS) custa mais hoje, mas oferece suporte, atualizações e menor ônus interno.  
  - [Exemplo real](https://aakashgupta.medium.com/the-product-leaders-guide-to-buying-vs-building-software-a67a87bfca04?utm_source=chatgpt.com): equipe construiu sistema interno e depois percebeu que estava "recriando a roda".
- **Infraestrutura gerenciada vs autogerenciada**  
  - **Serverless/PaaS (Google App Engine, AWS Lambda):** abstraem infraestrutura; menor manutenção operacional, mas custo unitário maior.  
  - **IaaS (Google Compute Engine, AWS EC2):** mais controle e menor custo de longo prazo, mas exige manutenção e gestão de escala.
- **Simplicidade operacional**  
  Segundo o [SRE do Google](https://sre.google/sre-book/simplicity/?utm_source=chatgpt.com), menos complexidade acidental aumenta a confiabilidade.


## Tabela Resumo

| Trade-off                 | Exemplo Rápido                                              | Implicação                                                      |
|---------------------------|-------------------------------------------------------------|-----------------------------------------------------------------|
| Velocidade vs Qualidade   | MVP com Rails vs sistema robusto em Go/Java + testes        | Qualidade reduz custos e acelera manutenção                     |
| Escalabilidade vs Simplicidade | SQL vs NoSQL; Monolito vs Microsserviços              | Mais complexidade permite escala, mas exige mais esforço        |
| Custo vs Manutenção       | Build (open-source) vs Buy (SaaS/API)                       | Build: baixo custo inicial, alto custo de manutenção            |
| Infraestrutura            | Serverless/PaaS vs IaaS                                     | PaaS reduz manutenção, IaaS custa menos mas exige mais gestão   |

---

## Conclusão
A ideia de que **programar é técnica, engenharia é estratégia** é central no livro.  
A beleza dos trade-offs está justamente em reconhecer que não existe “a melhor solução”, mas sim **a melhor escolha para o contexto atual**, com disposição para revisitar e adaptar conforme o cenário evolui.

> Mais detalhes podem ser vistos no capítulo de trade-offs do [Software Engineering at Google](https://abseil.io/resources/swe-book/html/ch06.html?utm_source=chatgpt.com).

 
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
