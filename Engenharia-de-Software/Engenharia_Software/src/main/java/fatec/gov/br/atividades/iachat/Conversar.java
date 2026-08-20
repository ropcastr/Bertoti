package fatec.gov.br.atividades.iachat;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;

import java.util.Scanner;

public class Conversar {

    public static void main(String[] args) {

        //Configura o endereço do servidor Ollama local
        final String OLLAMA_URL = "http://localhost:11434";

        //Define o modelo que será usado para responder
        final String MODEL_NAME = "qwen3:8b";

        System.out.println("🔹 Iniciando cliente de IA com o modelo '" + MODEL_NAME + "' ...");

        try {
            //Cria o cliente Ollama apontando para o servidor local definido anteriormente
            Ollama ollama = new Ollama(OLLAMA_URL);

            //Faz o download ou verifica se já existe o modelo definido
            ollama.pullModel(MODEL_NAME);

            //Cria um "builder" (montador) para configurar as mensagens e o modelo
            OllamaChatRequestBuilder builder = OllamaChatRequestBuilder
                    .builder()
                    .withModel(MODEL_NAME);

            //Define a mensagem de sistema: comportamento padrão da IA
            builder.withMessage(
                    OllamaChatMessageRole.SYSTEM,
                    "Você é um especialista em assuntos gerais. Seja educado, claro e objetivo nas respostas."
            );

            //try-with-resources fecha automaticamente o Scanner ao final
            try (Scanner scanner = new Scanner(System.in)) {

                System.out.println("\n💬 Pergunte qualquer coisa para a IA (ou digite 'sair' para encerrar)\n");

                //Cria um ‘loop’ infinito até o usuário digitar 'sair'
                while (true) {
                    System.out.print("\t⊂(◉‿◉)つ -> ");  //Prompt de entrada
                    String input = scanner.nextLine().trim(); //Lê a linha digitada

                    //Verifica se o usuário deseja sair
                    if (input.equalsIgnoreCase("sair")) {
                        System.out.println("\n\t(ʘ‿ʘ)╯ Até logo!");
                        break; //Sai do ‘loop’ caso usuário digite sair
                    }

                    //Adiciona a pergunta do usuário ao histórico
                    builder.withMessage(OllamaChatMessageRole.USER, input);

                    //Constrói a requisição de chat com base nas mensagens acumuladas
                    OllamaChatRequest request = builder.build();

                    try {
                        //Envia a requisição ao servidor e obtém o resultado
                        OllamaChatResult chatResult = ollama.chat(request, null);

                        //Extrai o texto de resposta da IA
                        String resposta = chatResult
                                .getResponseModel()
                                .getMessage()
                                .getResponse();

                        //Mostra a resposta no console
                        System.out.println("\n🤖 IA: " + resposta + "\n---");

                        //Adiciona a resposta do assistente ao histórico para manter contexto
                        builder.withMessage(OllamaChatMessageRole.ASSISTANT, resposta);

                    } catch (Exception e) {
                        //Tratamento de erro caso a requisição falhe
                        System.err.println("⚠️ Erro ao comunicar com o modelo: " + e.getMessage());
                    }
                }

            }

        } catch (Exception e) {
            //Tratamento de erro para falhas de inicialização (modelo, conexão, etc.)
            System.err.println("❌ Erro ao iniciar o cliente Ollama: " + e.getMessage());
        }

    }
}
