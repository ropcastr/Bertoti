package fatec.gov.br.atividades.iachat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fatec.gov.br.atividades.estacionamento.Veiculo;
import fatec.gov.br.atividades.estacionamento.Estacionamento;
import fatec.gov.br.atividades.estacionamento.Repositorio;
import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessage;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;


public class EstacionamentoIA {
    private static List<Veiculo> ultimaListaExibida = new ArrayList<>();
    private static final String MODEL = "llama3:8b";
    private static final String OLLAMA_URL = "http://localhost:11434/";
    private static final int REQUEST_TIMEOUT_SECONDS = 30;

    private static final String SYSTEM_PROMPT = """
    Sua única identidade é a de um interpretador de comandos para um sistema de estacionamento.
    Sua única função é traduzir a linguagem do usuário para o formato JSON especificado.
    Você é proibido de responder perguntas, manter conversas ou gerar texto que não seja o JSON de resposta.

    REGRA DE OURO: Se um comando do usuário não se encaixa clara e inequivocamente em uma das ações, sua única resposta permitida é {"action": "none", "params": {}, "message": "Comando não reconhecido. Peça 'ajuda' para ver os comandos disponíveis."}.

    O formato de resposta obrigatório é um JSON válido com:
    - "action": "add", "remove", "list", "help" ou "none".
    - "params": Objeto com parâmetros.
    - "message": Mensagem amigável sobre a ação.

    # Regras Detalhadas por Ação
    ## Regras para "add":
    - A ação "add" SÓ PODE ser acionada se o usuário usar verbos como "cadastrar", "adicionar", "inserir".
    - Comandos como "Listar" ou "Buscar" ou "Procurar" NUNCA devem ser interpretados como "add".
    - Comandos como "Remover" ou "Excluir" ou "Deletar" ou "Tirar" NUNCA devem ser interpretados como "add".
    - Requer "tipo", "modelo", "placa", "cor" e "ano". Se algo faltar, use action "none".
    ## Regras para "remove":
    - A ação "remove" SÓ PODE ser acionada se o usuário usar verbos como "remover", "excluir", "deletar", "tirar".
    - Comandos como "Listar placa" ou "Buscar placa" ou "Procurar placa" NUNCA devem ser interpretados como "remove".
    - Comandos como "Adicionar" ou "Cadastrar" ou "Inserir" NUNCA devem ser interpretados como "remove".
    - Extraia a "placa" ou o "indice". Sempre priorize a "placa".
    ## Regras para "list":
    - Pode ser geral ou com filtro. Para filtros, sempre gere "tipoFiltro" e "filtro".
    - REGRA DE FILTRO IMPORTANTE: SEMPRE converta o valor do filtro para o singular (ex: 'carros' vira 'carro', 'motos' vira 'moto', 'caminhonetes' vira 'caminhonete'). **Para cores, sempre converta para o masculino singular (ex: 'vermelhas' ou 'vermelha' viram 'vermelho').**
    - Comandos como "Remover" ou "Excluir" ou "Deletar" ou "Tirar" NUNCA devem ser interpretados como "list".
    - Comandos como "Adicionar" ou "Cadastrar" ou "Inserir" NUNCA devem ser interpretados como "list".
    ## Regras para "help":
    - Se o usuário pedir ajuda, use a action 'help'.

    # Exemplos de Execução
    
    ## Ações de Adicionar/Cadastrar/Inserir
    - Input: "adicionar veiculo tipo=Carro, modelo=Fusca, placa=ABC1234, cor=Azul, ano=1990"
      Resposta: {"action": "add", "params": {"tipo": "Carro", "modelo": "Fusca", "placa": "ABC1234", "cor": "Azul", "ano": 1990}, "message": "Veículo adicionado!"}
    
    ## Ações de Remover/Excluir/Deletar
    - Input: "excluir a placa xyz9876"
      Resposta: {"action": "remove", "params": {"placa": "xyz9876"}, "message": "Removendo veículo com placa xyz9876."}
    
    ## Ações de Listar/Buscar/Procurar
    - Input: "listar todos"
      Resposta: {"action": "list", "params": {}, "message": "Listando todos os veículos."}
    - Input: "listar veiculos"
      Resposta: {"action": "list", "params": {}, "message": "Listando todos os veículos."}
    - Input: "buscar veiculo"
      Resposta: {"action": "list", "params": {}, "message": "Listando todos os veículos."}
    - Input: "listar carros"
      Resposta: {"action": "list", "params": {"tipoFiltro": "tipo", "filtro": "carro"}, "message": "Listando veículos do tipo carro."}
    - Input: "buscar por caminhonetes"
      Resposta: {"action": "list", "params": {"tipoFiltro": "tipo", "filtro": "caminhonete"}, "message": "Listando veículos do tipo caminhonete."}
    - Input: "me mostre os veículos vermelhos"
      Resposta: {"action": "list", "params": {"tipoFiltro": "cor", "filtro": "vermelha"}, "message": "Listando veículos da cor vermelha."}
    - Input: "listar ano 2012"
      Resposta: {"action": "list", "params": {"tipoFiltro": "ano", "filtro": "2012"}, "message": "Listando veículos do ano 2012."}
    - Input: "procurar pelo modelo Corolla"
      Resposta: {"action": "list", "params": {"tipoFiltro": "modelo", "filtro": "corolla"}, "message": "Listando veículos do modelo Corolla."}
    - Input: "listar modelo Civic"
      Resposta: {"action": "list", "params": {"tipoFiltro": "modelo", "filtro": "civic"}, "message": "Listando veículos do modelo Civic."}
    - Input: "Buscar Carros"
      Resposta: {"action": "list", "params": {"tipoFiltro": "tipo", "filtro": "carro"}, "message": "Listando veículos do tipo carro."}
    - Input: "listar XRE300"
      Resposta: {"action": "list", "params": {"tipoFiltro": "modelo", "filtro": "xre300"}, "message": "Listando veículos do modelo XRE300."}
    - Input: "buscar pela placa DBZ1980"
      Resposta: {"action": "list", "params": {"tipoFiltro": "placa", "filtro": "DBZ1980"}, "message": "Buscando veículo com placa DBZ1980."}
    - Input: "Listar placa SFK2222"
      Resposta: {"action": "list", "params": {"tipoFiltro": "placa", "filtro": "SFK2222"}, "message": "Buscando veículo com placa SFK2222."}
    - Input: "buscar por Corolla"
      Resposta: {"action": "list", "params": {"tipoFiltro": "modelo", "filtro": "corolla"}, "message": "Listando veículos do modelo Corolla."}
    - Input: "procurar por cor vermelha"
      Resposta: {"action": "list", "params": {"tipoFiltro": "cor", "filtro": "vermelho"}, "message": "Listando veículos da cor vermelha."}
    
    ## Ação de Ajuda
    - Input: "ajuda"
      Resposta: {"action": "help", "params": {}, "message": "Comandos: adicionar, remover, listar [filtro]. Filtros: tipo, cor, ano, modelo, placa."}
    
    ## Comandos Inválidos ou Fora de Escopo
    - Input: "quanto é 5 + 5?"
      Resposta: {"action": "none", "params": {}, "message": "Comando não reconhecido. Peça 'ajuda' para ver os comandos disponíveis."}
    """;

    public static void main(String[] args) {
        try {
            Ollama ollama = new Ollama(OLLAMA_URL);
            ollama.pullModel(MODEL);
            OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(MODEL);

            Repositorio repositorio = new Repositorio();
            Estacionamento estacionamento = new Estacionamento(repositorio);
            repositorio.criarTabelaVeiculo();

            Scanner scanner = new Scanner(System.in);
            List<OllamaChatMessage> chatHistory = new ArrayList<>();
            chatHistory.add(new OllamaChatMessage(OllamaChatMessageRole.SYSTEM, SYSTEM_PROMPT));

            System.out.println("Converse com a IA para gerenciar o estacionamento (diga 'sair' para encerrar):\n");

            while (true) {
                System.out.print("\t ⊂(◉‿◉)つ -> ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("sair")) break;

                OllamaChatRequest request = builder.withMessages(chatHistory)
                    .withMessage(OllamaChatMessageRole.USER, input)
                    .build();

                try {
                    OllamaChatResult chatResult = ollama.chat(request, null);
                    String iaResponse = chatResult.getResponseModel().getMessage().getResponse().trim();
                    System.out.println("Resposta bruta da IA: " + iaResponse);

                    try {
                        Gson gson = new GsonBuilder().setLenient().create();
                        JsonObject json = gson.fromJson(iaResponse, JsonObject.class);
                        String action = json.get("action").getAsString();
                        JsonObject params = json.get("params").getAsJsonObject();
                        String message = json.get("message").getAsString();

                        System.out.println("Processando ação: " + action);
                        String executionResult = executeAction(estacionamento, action, params, input);

                        //corrigir mensagem para remoção bem-sucedida
                        if (action.equals("none") && executionResult.equals("Veículo removido.") && input.toLowerCase().contains("remover")) {
                            Pattern pattern = Pattern.compile("placa\\s*=\\s*(\\w+)");
                            Matcher matcher = pattern.matcher(input.toLowerCase());
                            if (matcher.find()) {
                                message = "Removendo veículo com placa " + matcher.group(1) + ".";
                            }
                        }

                        if (executionResult.startsWith("Erro")) {
                            System.out.println("\t OPS! Ocorreu um erro: " + executionResult);
                        } else {
                            //caso não haja erro, continua como antes.
                            System.out.println("\t IA: " + message);
                            if (action.equals("list") && !executionResult.isEmpty()) {
                                System.out.println(executionResult);
                            }
                        }

                        if (!action.equals("list") && !executionResult.isEmpty() && !executionResult.startsWith("Erro")) {
                            chatHistory.add(new OllamaChatMessage(OllamaChatMessageRole.SYSTEM, "Resultado da ação: " + executionResult));
                        }
                    } catch (JsonParseException e) {
                        System.out.println("\t IA: Resposta inválida da IA: " + iaResponse + ". Erro: " + e.getMessage() + ". Tente novamente.");
                    }
                } catch (Exception e) {
                    System.out.println("\t IA: Erro ao processar o comando: " + e.getClass().getName() + ": " + e.getMessage());
                }
            }
            System.out.println("\nSessão encerrada.\n \t ⸝(ʘ‿ʘ)╯ até mais!");
            scanner.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private static String executeAction(Estacionamento estacionamento, String action, JsonObject params, String input) {
        try {
            switch (action) {
                case "add":
                    try {
                        String tipo = params.get("tipo").getAsString();
                        String modelo = params.get("modelo").getAsString();
                        String placa = params.get("placa").getAsString();
                        String cor = params.get("cor").getAsString();
                        int ano = params.get("ano").getAsInt();

                        Veiculo novoVeiculo = new Veiculo(tipo, modelo, placa, cor, ano);
                        estacionamento.adicionarVeiculo(novoVeiculo);
                        return "Veículo adicionado: " + novoVeiculo;
                    } catch (IllegalArgumentException e) {
                        //para capturar erros de validação do construtor do Veiculo
                        return "Erro de validação: " + e.getMessage();
                    } catch (Exception e) {
                        //para capturar outros erros (JSON mal formado, placa duplicada)
                        return "Erro ao adicionar veículo: " + e.getMessage();
                    }

                case "remove":
                    String placaRemove = null;
                    if (params.has("indice")) {
                        int indice = params.get("indice").getAsInt() - 1;
                        if (ultimaListaExibida == null || indice < 0 || indice >= ultimaListaExibida.size()) {
                            return "Índice inválido ou lista não encontrada.";
                        }
                        placaRemove = ultimaListaExibida.get(indice).getPlaca();
                    } else if (params.has("placa")) {
                        placaRemove = params.get("placa").getAsString();
                    } else {
                        Pattern pattern = Pattern.compile("placa\\s*=?\\s*(\\w+)");
                        Matcher matcher = pattern.matcher(input.toLowerCase());
                        if (matcher.find()) {
                            placaRemove = matcher.group(1);
                        }
                    }
                    if (placaRemove != null && estacionamento.removerVeiculo(placaRemove)) {
                        return "Veículo removido.";
                    }
                    return placaRemove == null ? "Placa ou índice não fornecido." : "Veículo não encontrado.";

                case "query":
                    if (!params.has("placa")) {
                        return "Forneça a placa do veículo a buscar.";
                    }
                    String placaQuery = params.get("placa").getAsString();
                    Veiculo veiculo = estacionamento.buscarVeiculo(placaQuery);
                    return veiculo != null ? veiculo.toString() : "Veículo não encontrado.";

                case "list":
                    String filtro = params.has("filtro") ? params.get("filtro").getAsString() : null;

                    //para extrair o filtro caso a IA não consiga
                    if (filtro == null || filtro.trim().isEmpty()) {
                        String possivelFiltro = input.toLowerCase()
                                                    .replace("listar", "")
                                                    .replace("buscar", "")
                                                    .replace("procurar", "")
                                                    .replace("veiculos", "")
                                                    .replace("veiculo", "")
                                                    .replace("do tipo", "")
                                                    .replace("pelo modelo", "")
                                                    .replace("da cor", "")
                                                    .replace("por", "")
                                                    .replace("placa", "")
                                                    .replace("modelo", "")
                                                    .replace("tipo", "")
                                                    .replace("cor", "")
                                                    .replace("ano", "")
                                                    .trim();
                        if (!possivelFiltro.isEmpty()) {
                            filtro = possivelFiltro;
                        }
                    }

                    if (filtro == null || filtro.trim().isEmpty()) {
                        List<Veiculo> todosVeiculos = estacionamento.getVeiculos();
                        ultimaListaExibida = new ArrayList<>(todosVeiculos);
                        if (todosVeiculos.isEmpty()) return "Nenhum veículo encontrado.";
                        StringBuilder listaCompleta = new StringBuilder("Lista de veículos:\n");
                        for (int i = 0; i < todosVeiculos.size(); i++) {
                            listaCompleta.append(i + 1).append(": ").append(todosVeiculos.get(i)).append("\n");
                        }
                        return listaCompleta.toString();
                    }

                    String filtroNormalizado = normalize(filtro);

                    List<Veiculo> todosOsVeiculos = estacionamento.getVeiculos();
                    List<Veiculo> veiculosFiltrados = todosOsVeiculos.stream()
                        .filter(v -> {
                            String corNormalizada = normalize(v.getCor());

                            //para tratar cores com gênero (ex: "vermelho", "preto")
                            //se o filtro termina com "o", a busca vai procurar pela raiz da palavra.
                            //filtro "vermelho" -> raiz "vermelh". Assim, encontra "vermelho" e "vermelha".
                            if (filtroNormalizado.length() > 2 && filtroNormalizado.endsWith("o")) {
                                String raizCor = filtroNormalizado.substring(0, filtroNormalizado.length() - 1);
                                if (corNormalizada.startsWith(raizCor)) {
                                    return true; //se a cor corresponde pela raiz, já retorna verdadeiro.
                                }
                            }

                            //caso não seja uma cor com gênero ou se a lógica acima não encontrou,
                            //continua com a verificação normal para todos os campos.
                            return normalize(v.getTipo()).contains(filtroNormalizado) ||
                                normalize(v.getModelo()).contains(filtroNormalizado) ||
                                corNormalizada.contains(filtroNormalizado) || //verificação padrão para "azul", "verde", etc.
                                normalize(v.getPlaca()).contains(filtroNormalizado) ||
                                normalize(String.valueOf(v.getAno())).contains(filtroNormalizado);
                        })
                        .toList();

                    ultimaListaExibida = new ArrayList<>(veiculosFiltrados);

                    if (veiculosFiltrados.isEmpty()) {
                        return "Nenhum veículo encontrado para o filtro '" + filtro + "'.";
                    }

                    StringBuilder listaFiltrada = new StringBuilder("Lista de veículos filtrados:\n");
                    for (int i = 0; i < veiculosFiltrados.size(); i++) {
                        listaFiltrada.append(i + 1).append(": ").append(veiculosFiltrados.get(i)).append("\n");
                    }
                    return listaFiltrada.toString();

                case "help":
                    //a ação de ajuda não executa nada no backend.
                    //a mensagem útil já está no JSON da IA e será impressa no loop principal.
                    return "";

                case "none":
                    if (input.toLowerCase().contains("remover") || input.toLowerCase().contains("excluir")) {
                        Pattern pattern = Pattern.compile("placa\\s*=?\\s*(\\w+)");
                        Matcher matcher = pattern.matcher(input.toLowerCase());
                        if (matcher.find()) {
                            String placaToRemove = matcher.group(1); // Nome diferente para evitar conflito
                            if (estacionamento.removerVeiculo(placaToRemove)) {
                                return "Veículo removido.";
                            }
                            return "Veículo não encontrado.";
                        }
                    }
                    return "Placa ou índice não fornecido.";

                default:
                    return "Ação inválida.";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    private static String normalize(String input) {
    if (input == null) return null;
    //para converter para minúsculas, remove acentos e espaços extras
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase().trim();
}

}