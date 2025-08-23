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
