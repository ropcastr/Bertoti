package fatec.gov.br.atividades.estacionamento;

import java.sql.SQLException;
import java.util.List;

public class Estacionamento {
    private final Repositorio repositorio;

    public Estacionamento(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        try {
            repositorio.inserirVeiculo(veiculo);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar veículo: " + e.getMessage(), e);
        }
    }

    public boolean removerVeiculo(String placa) {
        try {
            return repositorio.removerVeiculo(placa);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover veículo: " + e.getMessage(), e);
        }
    }

    public Veiculo buscarVeiculo(String placa) {
        try {
            return repositorio.buscarVeiculo(placa);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veículo: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> getVeiculos() {
        try {
            return repositorio.listarVeiculos();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veículos: " + e.getMessage(), e);
        }
    }
}