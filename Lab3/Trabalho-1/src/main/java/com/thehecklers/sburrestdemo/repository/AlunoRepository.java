package com.thehecklers.sburrestdemo.repository;

import com.thehecklers.sburrestdemo.model.Aluno;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório DAO para persistência da entidade Aluno utilizando Spring JDBC (JdbcTemplate).
 * Executa instruções SQL explícitas e mapeia os resultados do ResultSet para objetos Java.
 */
@Repository
public class AlunoRepository {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper: Converte cada linha retornada da tabela SQL em uma instância da classe Aluno
    private final RowMapper<Aluno> alunoRowMapper = (rs, rowNum) -> new Aluno(
            rs.getString("id"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("curso")
    );

    public AlunoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. SELECT * - Listar todos os alunos
    public List<Aluno> findAll() {
        String sql = "SELECT id, nome, email, curso FROM aluno";
        return jdbcTemplate.query(sql, alunoRowMapper);
    }

    // 2. SELECT WHERE id = ? - Buscar por Chave Primária
    public Optional<Aluno> findById(String id) {
        String sql = "SELECT id, nome, email, curso FROM aluno WHERE id = ?";
        List<Aluno> results = jdbcTemplate.query(sql, alunoRowMapper, id);
        return results.stream().findFirst();
    }

    // 3. INSERT / UPDATE - Salva ou atualiza os dados do aluno no banco
    public Aluno save(Aluno aluno) {
        aluno.ensureId();
        if (existsById(aluno.getId())) {
            // Executa UPDATE SQL caso o registro já exista
            String sql = "UPDATE aluno SET nome = ?, email = ?, curso = ? WHERE id = ?";
            jdbcTemplate.update(sql, aluno.getNome(), aluno.getEmail(), aluno.getCurso(), aluno.getId());
        } else {
            // Executa INSERT SQL para novo aluno
            String sql = "INSERT INTO aluno (id, nome, email, curso) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getCurso());
        }
        return aluno;
    }

    // 4. SELECT COUNT(*) - Verifica se o registro existe
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    // 5. DELETE FROM aluno WHERE id = ? - Remove o aluno por ID
    public boolean deleteById(String id) {
        String sql = "DELETE FROM aluno WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    // 6. SELECT COUNT(*) - Conta total de linhas na tabela
    public long count() {
        String sql = "SELECT COUNT(*) FROM aluno";
        Long total = jdbcTemplate.queryForObject(sql, Long.class);
        return total != null ? total : 0L;
    }

    // 7. Inserção em lote
    public void saveAll(Iterable<Aluno> alunos) {
        for (Aluno aluno : alunos) {
            save(aluno);
        }
    }

    // 8. DELETE FROM aluno - Limpa toda a tabela (utilizado no isolamento dos testes)
    public void deleteAll() {
        String sql = "DELETE FROM aluno";
        jdbcTemplate.update(sql);
    }
}
