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
---
 
## Exemplos de Trade-offs  
Trade-offs são escolhas que a gente faz em engenharia de software, pesando prós e contras.
 
1. **Velocidade vs. Qualidade**  
   - *Descrição*: Quer entregar um projeto rápido? Pode mandar um código simples, mas talvez ele vire uma gambiarra difícil de consertar depois. Ou capricha, testa tudo, mas demora mais.  
   - *Exemplo*: Um experimento no Google pode priorizar velocidade, mas depois refatorar pra qualidade.
 
2. **Escalabilidade vs. Simplicidade**  
   - *Descrição*: Tá fazendo um app pequeno? Um banco de dados básico resolve. Mas se ele pode bombar (tipo virar um Instagram), melhor investir numa solução mais complexa desde o início.  
   - *Exemplo*: Começar simples e depois escalar quando o número de usuários explodir.
 
3. **Custo vs. Manutenção**  
   - *Descrição*: Com orçamento curto, usa uma biblioteca barata, mas limitada. Ou gasta mais numa solução sob medida, que facilita ajustes futuros.  
   - *Exemplo*: Economizar agora, mas preparar o bolso pra refazer tudo depois.
 
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
