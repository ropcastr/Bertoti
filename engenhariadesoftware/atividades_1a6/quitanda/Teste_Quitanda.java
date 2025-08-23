package fatec.gov.br.atividades.quitanda;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Teste_Quitanda {

    @Test
    void testAdicionarProduto() {
        Quitanda q = new Quitanda();
        q.adicionarProduto(new Produto("Maçã", 2.5, 10));
        assertEquals(1, q.getProdutos().size());
    }

    @Test
    void testRemoverProduto() {
        Quitanda q = new Quitanda();
        q.adicionarProduto(new Produto("Banana", 3.0, 5));
        boolean removido = q.removerProduto("Banana");
        assertTrue(removido);
        assertEquals(0, q.getProdutos().size());
    }

    @Test
    void testBuscarProduto() {
        Quitanda q = new Quitanda();
        Produto p = new Produto("Laranja", 4.0, 8);
        q.adicionarProduto(p);

        Produto encontrado = q.buscarProduto("Laranja");
        assertNotNull(encontrado);
        assertEquals("Laranja", encontrado.getNome());
    }

    @Test
    void testCalcularValorTotal() {
        Quitanda q = new Quitanda();
        q.adicionarProduto(new Produto("Tomate", 5.0, 2)); // 10.0
        q.adicionarProduto(new Produto("Batata", 4.0, 3)); // 12.0

        assertEquals(22.0, q.calcularValorTotal());
    }
}