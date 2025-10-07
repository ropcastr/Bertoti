package fatec.gov.br.atividades.estacionamento;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class Teste_Estacionamento {
    private Estacionamento est;
    private Repositorio repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new Repositorio();
        try {
            repositorio.criarTabelaVeiculo(); // Atualizado para veiculo
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela: " + e.getMessage(), e);
        }
        est = new Estacionamento(repositorio);

        // Limpar o banco de dados antes de cada teste
        try (Connection conn = DriverManager.getConnection(Repositorio.URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM veiculo"); // Atualizado para veiculo
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar o banco de dados: " + e.getMessage(), e);
        }
    }

    @AfterEach
    void tearDown() {
        // Opcional: pode ser usado para limpezas adicionais
    }

    @Test
    void testAdicionarVeiculo() {
        est.adicionarVeiculo(new Veiculo("Carro", "Fusca", "EGF3365", "Azul", 1976));
        assertEquals(1, est.getVeiculos().size());
    }

    @Test
    void testRemoverVeiculo() {
        est.adicionarVeiculo(new Veiculo("Carro", "Civic", "XYZ9876", "Prata", 2020));
        boolean removido = est.removerVeiculo("XYZ9876");
        assertTrue(removido);
        assertEquals(0, est.getVeiculos().size());
    }

    @Test
    void testBuscarVeiculo() {
        Veiculo v = new Veiculo("Carro", "Corolla", "AAA1111", "Preto" , 2019);
        est.adicionarVeiculo(v);

        Veiculo encontrado = est.buscarVeiculo("AAA1111");
        assertNotNull(encontrado);
        assertEquals("Corolla", encontrado.getModelo());
        assertEquals("Carro", encontrado.getTipo());
    }

    @Test
    void testAdicionarVeiculoComPlacaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            est.adicionarVeiculo(new Veiculo("", "Fusca", "EGF3365", "Azul", 1976));
        });
    }

    @Test
    void testAdicionarVeiculoComPlacaDuplicada() {
        est.adicionarVeiculo(new Veiculo("Carro", "Fusca", "EGF3365", "Azul", 1976));
        assertThrows(RuntimeException.class, () -> {
            est.adicionarVeiculo(new Veiculo("Carro", "Civic", "EGF3365", "Prata", 2020));
        });
    }

    @Test
    void testAdicionarVeiculoComTipoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            est.adicionarVeiculo(new Veiculo("Bicicleta", "Fusca", "EGF3365", "Azul", 1976 ));
        });
    }

    @Test
    void testRemoverVeiculoInexistente() {
        boolean removido = est.removerVeiculo("INEXISTENTE");
        assertFalse(removido);
    }

    @Test
    void testBuscarVeiculoInexistente() {
        Veiculo encontrado = est.buscarVeiculo("INEXISTENTE");
        assertNull(encontrado);
    }

    @Test
    void testListarVeiculosVazio() {
        assertEquals(0, est.getVeiculos().size());
    }

    @Test
    void testListarMultiplosVeiculos() {
        est.adicionarVeiculo(new Veiculo("Carro", "Fusca", "EGF3365", "Azul", 1976));
        est.adicionarVeiculo(new Veiculo("Carro", "Civic", "XYZ9876", "Prata", 2020));
        est.adicionarVeiculo(new Veiculo("Carro", "Corolla", "AAA1111", "Preto" , 2019));

        assertEquals(3, est.getVeiculos().size());
        assertTrue(est.getVeiculos().stream().anyMatch(v -> v.getTipo().equals("Moto")));
    }
}