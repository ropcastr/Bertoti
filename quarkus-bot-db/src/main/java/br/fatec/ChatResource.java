package br.fatec;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

@Path("/chat")
public class ChatResource {

    @ConfigProperty(name = "quarkus.langchain4j.ollama.chat-model.model-id")
    String modelId;

    @Inject
    AIService aiService;

    //Histórico simples por sessão (tem como ser melhorado)
    private static final Map<String, List<String>> historyMap = new ConcurrentHashMap<>();
    //Nome do banco por sessão
    private static final Map<String, String> bancoMap = new ConcurrentHashMap<>();

    public static class ChatRequest {
        public String message;
        public String sessionId; //para manter o contexto
    }

    public static class ChatResponse {
        public String response;
        public ChatResponse(String response) { this.response = response; }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ChatResponse chat(ChatRequest request) {
        String sessionId = request.sessionId != null ? request.sessionId : "default";
        historyMap.putIfAbsent(sessionId, new ArrayList<>());
        List<String> history = historyMap.get(sessionId);
        //Saudação inicial se for a primeira mensagem
        if (history.size() == 0) {
            history.add("IA: Olá! Sou Tenebra, sua assistente para bancos de dados SQLite. Digite 'ajuda' para ver dicas de uso e exemplos de comandos. Você pode pedir: 'Crie um banco chamado Teste', 'Adicione dados na tabela Clientes', 'Liste os dados da tabela', etc.");
        }
        history.add("Usuário: " + request.message);
        //Detecta nome do banco solicitado
        String nomeBanco = detectaNomeBanco(request.message, bancoMap.get(sessionId));
        //Só atualiza o bancoMap se nomeBanco não for null
        if (nomeBanco != null) {
            bancoMap.put(sessionId, nomeBanco);
        }
        //Verifica se o arquivo do banco existe (só se nomeBanco != null)
        String bancoStatus = "";
        if (nomeBanco != null) {
            java.io.File dbFile = new java.io.File("./" + nomeBanco);
            bancoStatus = dbFile.exists() ? "Banco " + nomeBanco + " já existe." : "Banco " + nomeBanco + " será criado ao executar comandos.";
            System.out.println(bancoStatus);
        }
        //Envia histório para IA
        String prompt = String.join("\n", history) + "\nUsuário (ação atual): " + request.message + "\nIA: Execute a ação solicitada imediatamente, gerando os comandos SQL necessários para criar tabelas e inserir dados, se aplicável. Não inclua blocos <think> ou raciocínio interno, apenas a resposta final.";
        String resposta;
        if (request.message.trim().equalsIgnoreCase("ajuda")) {
            resposta = """
                        <b>Dicas de uso:</b><br>
                        - O nome do banco deve ter <b>pelo menos 3 caracteres</b> e não pode ser palavras genéricas como 'com', 'dados', 'tabela'.<br>
                        - Exemplos válidos: <code>Teste</code>, <code>Clientes2025</code>, <code>dadosEmpresa</code><br>
                        <br>
                        <b>Comandos que você pode usar:</b><br>
                        • Criar banco: <code>Crie um banco chamado Vendas</code><br>
                        • Apagar banco: <code>Apague o banco Vendas</code><br>
                        • Criar tabela: <code>Crie tabela Contatos com Nome, Email, Telefone</code><br>
                        • Adicionar dados: <code>Adicione: Nome: Ana, Email: ana@fatec.br</code><br>
                        • Listar: <code>Mostre todos os contatos</code> ou <code>Liste tabelas</code><br>
                        • Buscar: <code>Procure por João</code><br>
                        • Cruzar: <code>Junte Clientes com Pedidos por ID</code><br>
                        <br>
                        Fale naturalmente: "bota um dado", "tira isso", "mostra tudo" — eu entendo!
                        """;
        } else if (request.message.toLowerCase().contains("liste bancos") || request.message.toLowerCase().contains("quais bancos")) {
            java.io.File dir = new java.io.File("./");
            String[] dbs = dir.list((d, name) -> name.endsWith(".db"));
            if (dbs != null && dbs.length > 0) {
                resposta = "Bancos disponíveis: " + String.join(", ", dbs);
            } else {
                resposta = "Nenhum banco de dados (.db) encontrado no diretório.";
            }
        } else if (request.message.toLowerCase().contains("apague o banco") || request.message.toLowerCase().contains("exclua o banco") || request.message.toLowerCase().contains("delete o banco")) {
            if (nomeBanco == null) {
                resposta = "Por favor, especifique o nome do banco a ser apagado, como 'Apague o banco MeuBanco'.";
            } else {
                java.io.File file = new java.io.File("./" + nomeBanco);
                if (file.exists()) {
                    if (file.delete()) {
                        bancoMap.remove(sessionId); //Remove o banco da sessão
                        resposta = "Banco " + nomeBanco + " foi excluído com sucesso.";
                    } else {
                        resposta = "Erro ao excluir o banco " + nomeBanco + ". Verifique permissões.";
                    }
                } else {
                    resposta = "Banco " + nomeBanco + " não existe.";
                }
            }
        } else if (request.message.toLowerCase().contains("liste tabelas") || request.message.toLowerCase().contains("quais tabelas")) {
            if (nomeBanco == null) {
                resposta = "Por favor, especifique o nome do banco para listar as tabelas, como 'Liste tabelas no banco MeuBanco'.";
            } else {
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:./" + nomeBanco);
                    Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");
                    List<String> tables = new ArrayList<>();
                    while (rs.next()) {
                        tables.add(rs.getString("name"));
                    }
                    if (!tables.isEmpty()) {
                        resposta = "Tabelas no banco " + nomeBanco + ": " + String.join(", ", tables);
                    } else {
                        resposta = "Nenhuma tabela encontrada no banco " + nomeBanco + ".";
                    }
                } catch (Exception e) {
                    resposta = "Erro ao listar tabelas no banco " + nomeBanco + ": " + e.getMessage();
                }
            }
        } else {
            if (nomeBanco == null) {
                resposta = "Por favor, especifique o nome do banco para realizar a ação, como 'Crie um banco chamado MeuBanco' ou use um banco existente do histórico.";
            } else {
                resposta = aiService.input(prompt);
                System.out.println("Resposta da IA: " + resposta);
                //Remove todos os blocos <think>...</think> da IA da resposta
                resposta = resposta.replaceAll("<think>[\\s\\S]*?</think>", "").trim();
                history.add("IA: " + resposta);
                //Detecta comandos SQL e executa
                String resultadoExecucao = executarComandosSQL(resposta, nomeBanco);
                if (!resultadoExecucao.isEmpty()) {
                    resposta += "\n\n[Resultado da execução SQL:]\n" + resultadoExecucao;
                }
                //Adiciona status do banco à resposta apenas se for um comando de criação
                if (request.message.toLowerCase().contains("crie um banco")) {
                    resposta = bancoStatus + "<br><br>" + resposta;
                }
            }
            //Formata para HTML: quebra de linhas
            resposta = resposta.replace("\n", "<br>");
        }
        return new ChatResponse(resposta);
    }

    //Detecta nome do banco na mensagem do usuário (várias variações e sinônimos, pode ser incrementado)
    private String detectaNomeBanco(String mensagem, String bancoAtual) {
        String[] padroes = {
            "banco(?: de dados)? chamado ([a-zA-Z0-9_\\-]+)",
            "Nome:\\s*['\"]?([a-zA-Z0-9_\\-]+)['\"]?",
            "banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "no banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "em ([a-zA-Z0-9_\\-]+)",
            "usando ([a-zA-Z0-9_\\-]+)",
            "usar o banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "troque para o banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "acessar o banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "utilizar o banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "utilize o banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "adicionar (?:no|ao|para o|em) banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "inserir (?:no|ao|para o|em) banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "remover (?:do|do banco|do banco de dados|de) ([a-zA-Z0-9_\\-]+)",
            "deletar (?:do|do banco|do banco de dados|de) ([a-zA-Z0-9_\\-]+)",
            "excluir (?:do|do banco|do banco de dados|de) ([a-zA-Z0-9_\\-]+)",
            "listar (?:do|do banco|do banco de dados|no|no banco|no banco de dados|em) ([a-zA-Z0-9_\\-]+)",
            "buscar (?:no|no banco|no banco de dados|do|do banco|do banco de dados|em) ([a-zA-Z0-9_\\-]+)",
            "procurar (?:no|no banco|no banco de dados|do|do banco|do banco de dados|em) ([a-zA-Z0-9_\\-]+)",
            "consultar (?:o|no|no banco|no banco de dados|do|do banco|do banco de dados|em) ([a-zA-Z0-9_\\-]+)",
            "crie um banco(?: de dados)? (?:com o nome|chamado)? ([a-zA-Z0-9_\\-]+)",
            "popule este banco(?: de dados)? (?:com|usando)? ([a-zA-Z0-9_\\-]+)",
            "liste os (?:usuarios|dados) do banco(?: de dados)? ([a-zA-Z0-9_\\-]+)",
            "contido no banco(?: de dados)? ([a-zA-Z0-9_\\-]+)"
        };
        String[] palavrasInvalidas = {"com", "os", "dados", "tabela", "colunas", "seguinte", "nome", "email", "telefone", "Usuarios", "ser", "exibindo", "que", "apenas", "cada", "um", "deles", "respectivo", "telefone", "contido", "na", "tabela"};
        String nomeEncontrado = null;
        for (String padrao : padroes) {
            Pattern p = Pattern.compile(padrao, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(mensagem);
            if (m.find()) {
                String nome = m.group(1).trim();
                boolean invalido = false;
                if (nome.length() < 3) {
                    invalido = true;
                } else {
                    for (String palavra : palavrasInvalidas) {
                        if (nome.equalsIgnoreCase(palavra)) {
                            invalido = true;
                            break;
                        }
                    }
                    //Validação extra contra path traversal
                    if (nome.contains(".") || nome.contains("/") || nome.contains("\\")) {
                        invalido = true;
                    }
                }
                if (!invalido) {
                    nomeEncontrado = nome;
                    break;
                }
            }
        }
        if (nomeEncontrado != null) {
            String novoBanco = nomeEncontrado + ".db";
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:./" + novoBanco)) {
                conn.setAutoCommit(true);
                System.out.println("Banco selecionado/criado: " + novoBanco);
                return novoBanco;
            } catch (Exception e) {
                System.err.println("Erro ao verificar/criar banco " + novoBanco + ": " + e.getMessage());
                return null; // Evita usar banco inválido
            }
        }
        //Sem fallback para database.db; usa bancoAtual se existir, senão null
        String bancoUsado = bancoAtual != null ? bancoAtual : null;
        if (bancoUsado == null) {
            System.out.println("Nome do banco não detectado e nenhum anterior disponível.");
        } else {
            System.out.println("Nome do banco não detectado, usando anterior: " + bancoUsado);
        }
        return bancoUsado;
    }

    private String executarComandosSQL(String texto, String nomeBanco) {
        if (nomeBanco == null) {
            return "Erro: Nome do banco não especificado.";
        }
        StringBuilder resultado = new StringBuilder();
        //Detecta blocos ```sql ... ```
        Pattern pattern = Pattern.compile("(?s)```sql(.*?)```", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(texto);
        boolean found = false;
        while (matcher.find()) {
            found = true;
            String sql = matcher.group(1).trim();
            //Detecta nome do banco no SQL, se houver
            String nomeDetectado = detectaNomeBanco(sql, nomeBanco);
            //Adiciona IF NOT EXISTS apenas se não estiver presente
            if (sql.toLowerCase().startsWith("create table") && !sql.toLowerCase().contains("if not exists")) {
                sql = sql.replaceFirst("(?i)create table", "CREATE TABLE IF NOT EXISTS");
            }
            resultado.append(executaSQL(sql, nomeDetectado));
        }
        //Se não encontrou bloco sql, verifica se a resposta é um comando SQL puro
        String lower = texto.trim().toLowerCase();
        if (!found && (lower.startsWith("create table") || lower.startsWith("insert") || lower.startsWith("select") || lower.startsWith("update") || lower.startsWith("delete") || lower.startsWith("drop"))) {
            //Detecta nome do banco no SQL puro
            String nomeDetectado = detectaNomeBanco(texto.trim(), nomeBanco);
            String sql = texto.trim();
            //Adiciona IF NOT EXISTS apenas se não estiver presente
            if (lower.startsWith("create table") && !lower.contains("if not exists")) {
                sql = sql.replaceFirst("(?i)create table", "CREATE TABLE IF NOT EXISTS");
            }
            resultado.append(executaSQL(sql, nomeDetectado));
        }
        return resultado.toString();
    }

    private String executaSQL(String sql, String nomeBanco) {
        if (nomeBanco == null) {
            return "Erro: Nome do banco não especificado para execução SQL.";
        }
        //Remove lines with CREATE DATABASE and USE (not valid in SQLite)
        String[] lines = sql.split("\n");
        StringBuilder filteredSQL = new StringBuilder();
        for (String line : lines) {
            String l = line.trim().toLowerCase();
            if (l.startsWith("create database") || l.startsWith("use ")) continue;
            filteredSQL.append(line).append("\n");
        }
        sql = filteredSQL.toString().trim();
        StringBuilder resultado = new StringBuilder();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:./" + nomeBanco);
            Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(true);
            //Validação para INSERT: não permitir valores nulos/vazios em PK, UN, FK
            if (sql.toLowerCase().startsWith("insert")) {
                // Detecta tabela e colunas
                Pattern p = Pattern.compile("insert into ([a-zA-Z0-9_\\-]+) *\\((.*?)\\) *values *\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                Matcher m = p.matcher(sql.replaceAll("\n", " "));
                if (m.find()) {
                    String tabela = m.group(1).trim();
                    String colunas = m.group(2).trim();
                    String valores = m.group(3).trim();
                    //Busca colunas PK, UN, FK na tabela
                    List<String> obrigatorias = new ArrayList<>();
                    try (ResultSet rs = conn.getMetaData().getColumns(null, null, tabela, null)) {
                        while (rs.next()) {
                            String coluna = rs.getString("COLUMN_NAME");
                            String tipo = rs.getString("TYPE_NAME");
                            int nullable = rs.getInt("NULLABLE");
                            // Busca PK
                            try (ResultSet pk = conn.getMetaData().getPrimaryKeys(null, null, tabela)) {
                                while (pk.next()) {
                                    if (pk.getString("COLUMN_NAME").equals(coluna)) obrigatorias.add(coluna);
                                }
                            }
                            //Busca UN
                            try (ResultSet idx = conn.getMetaData().getIndexInfo(null, null, tabela, true, false)) {
                                while (idx.next()) {
                                    if (idx.getString("COLUMN_NAME") != null && idx.getString("COLUMN_NAME").equals(coluna)) obrigatorias.add(coluna);
                                }
                            }
                            //Busca FK
                            try (ResultSet fk = conn.getMetaData().getImportedKeys(null, null, tabela)) {
                                while (fk.next()) {
                                    if (fk.getString("FKCOLUMN_NAME").equals(coluna)) obrigatorias.add(coluna);
                                }
                            }
                            //Se coluna é NOT NULL
                            if (nullable == 0) obrigatorias.add(coluna);
                        }
                    }
                    // Valida valores
                    String[] cols = colunas.split(",");
                    String[] vals = valores.split(",");
                    for (int i = 0; i < cols.length; i++) {
                        String col = cols[i].trim();
                        String val = vals[i].trim().replace("'", "");
                        if (obrigatorias.contains(col) && (val.isEmpty() || val.equalsIgnoreCase("null"))) {
                            return "Erro: Não é permitido inserir valor nulo ou vazio na coluna obrigatória '" + col + "' (PK, UN ou FK).";
                        }
                    }
                }
            }
            if (sql.toLowerCase().startsWith("select")) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    int cols = rs.getMetaData().getColumnCount();
                    resultado.append("<table border='1'><tr>");
                    for (int i = 1; i <= cols; i++) {
                        resultado.append("<th>").append(rs.getMetaData().getColumnName(i)).append("</th>");
                    }
                    resultado.append("</tr>");
                    while (rs.next()) {
                        resultado.append("<tr>");
                        for (int i = 1; i <= cols; i++) {
                            resultado.append("<td>").append(rs.getString(i)).append("</td>");
                        }
                        resultado.append("</tr>");
                    }
                    resultado.append("</table>");
                }
            } else {
                int count = stmt.executeUpdate(sql);
                resultado.append("Comando executado: ").append(sql).append("\nLinhas afetadas: ").append(count).append("\n\n");
            }
        } catch (Exception e) {
            resultado.append("Erro ao executar: ").append(sql).append("\n").append(e.getMessage()).append("\n");
        }
        return resultado.toString();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String info() {
        return "Use POST /chat com JSON: { 'message': 'sua pergunta', 'sessionId': 'opcional' } para conversar com a IA.";
    }

    @GET
    @Path("/welcome")
    @Produces(MediaType.APPLICATION_JSON)
    public ChatResponse welcome() {
        String resposta = "Olá! Sou Tenebra, a sua assistente para bancos de dados SQLite.<br>\nDigite <b>'ajuda'</b> para ver dicas de uso e exemplos de comandos.";
        return new ChatResponse(resposta);
    }

    @GET
    @Path("/model")
    @Produces(MediaType.TEXT_PLAIN)
    public String getModel() {
        return modelId;
    }
}