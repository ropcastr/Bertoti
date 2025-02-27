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

