package fatec.gov.br.atividades.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//  DAO = Data Access Object
public class BibliotecaDAO {
    //Criar Banco de Dados
    public void CriarDB() throws SQLException{
        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:biblioteca.db")) {
            System.out.println("Conectado com sucesso!");
        }
    }
    //Conectar ao banco de dados
    public Connection conectar() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlite:biblioteca.db");
    }
    //Criar Tabela
    public void CriarTabela() throws SQLException{
        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Livro (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo TEXT NOT NULL," +
                    "autor TEXT NOT NULL," +
                    "isbn TEXT NOT NULL UNIQUE" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("Tabela Livro Criado!");
        }
    }
    //Adicionar livro
    public void addLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livro (titulo,autor,isbn) VALUES (?,?,?)";
        try (Connection conexao = conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getIsbn());
            pstmt.executeUpdate();
            System.out.println("Livro Adicionado!");
        }
    }
    //Deletar Livro
    public boolean deletarLivro(String isbn) throws SQLException {
        String sql = "DELETE FROM livro WHERE isbn = ?";
        try (Connection conexao = conectar();
        PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            int rowsAfected = pstmt.executeUpdate();
            System.out.println("Livro deletado!");
            return rowsAfected > 0;
        }
    }
    //Buscar livro
    public Livro buscarLivro(String isbn) throws SQLException {
        String sql = "SELECT * FROM livro WHERE isbn = ?";
        try (Connection conexao = conectar();
        PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return  new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn")
                );
            }
            System.out.println("Livro não encontrado!");
            return null;
        }
    }
    //listar Livros
    public List<Livro> listarLivro() throws SQLException {
        List <Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro";
        try (Connection conexao = conectar();
        PreparedStatement pstmt = conexao.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                livros.add(new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn")
                ));
            }
        }
        System.out.println("Livros listado!");
        return livros;
    }
}
