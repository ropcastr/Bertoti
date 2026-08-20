package br.fatec;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.QueryParam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/db")
public class DatabaseResource {

    //Endpoint genérico para consultas dinâmicas (apenas SELECT para segurança)
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> queryDb(@QueryParam("db") String dbName, @QueryParam("sql") String sql) {
        if (dbName == null || dbName.isEmpty() || sql == null || sql.isEmpty()) {
            return new ArrayList<>(); // Retorna vazio se params faltando
        }
        if (!dbName.endsWith(".db")) {
            dbName += ".db";
        }
        //Validação básica: só permite SELECT para evitar ops destrutivas via API
        if (!sql.trim().toLowerCase().startsWith("select")) {
            return new ArrayList<>(); // Ou lance exceção: throw new BadRequestException("Apenas consultas SELECT são permitidas.");
        }
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:./" + dbName);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= cols; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (Exception e) {
            System.err.println("Erro na query: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

}