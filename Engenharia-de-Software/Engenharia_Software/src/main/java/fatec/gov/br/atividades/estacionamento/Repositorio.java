package fatec.gov.br.atividades.estacionamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    public static final String URL = "jdbc:sqlite:estacionamento.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQLite não encontrado: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void criarTabelaVeiculo() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS veiculo (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "tipo TEXT NOT NULL," +
                     "modelo TEXT NOT NULL," +
                     "placa TEXT NOT NULL UNIQUE," +
                     "cor TEXT NOT NULL," +
                     "ano INTEGER NOT NULL" +
                     ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada/verificada com sucesso.");
        }
    }

    public void inserirVeiculo(Veiculo veiculo) throws SQLException {
    String sql = "INSERT INTO veiculo(tipo, modelo, placa, cor, ano) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, veiculo.getTipo());
        pstmt.setString(2, veiculo.getModelo());
        pstmt.setString(3, veiculo.getPlaca());
        pstmt.setString(4, veiculo.getCor());
        pstmt.setInt(5, veiculo.getAno());

        pstmt.executeUpdate();
        System.out.println("Veículo inserido com sucesso: " + veiculo);
    } catch (SQLException e) {
        if (e.getErrorCode() == 19 && e.getMessage().contains("UNIQUE constraint failed")) {
            throw new SQLException("Já existe um veículo com a placa " + veiculo.getPlaca(), e);
        }
        throw e;
        }
    }

    public Veiculo buscarVeiculo(String placa) throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE placa = ? COLLATE NOCASE";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, placa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Veiculo(
                        rs.getString("tipo"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("cor"),
                        rs.getInt("ano")
                    );
                }
                return null;
            }
        }
    }

    public boolean removerVeiculo(String placa) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE placa = ? COLLATE NOCASE";
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, placa);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Remoção de veículo com placa " + placa + ": " + (rowsAffected > 0 ? "Sucesso" : "Não encontrado"));
            return rowsAffected > 0;
        }
    }

    public List<Veiculo> listarVeiculos() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculo";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                veiculos.add(new Veiculo(
                    rs.getString("tipo"),
                    rs.getString("modelo"),
                    rs.getString("placa"),
                    rs.getString("cor"),
                    rs.getInt("ano")
                ));
            }
            System.out.println("Total de veículos encontrados: " + veiculos.size());
        }
        return veiculos;
    }
}