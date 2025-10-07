package fatec.gov.br.atividades.estacionamento;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Repositorio repositorio = new Repositorio();
        Estacionamento estacionamento = new Estacionamento(repositorio);
        Scanner scanner = new Scanner(System.in);

        try {
            repositorio.criarTabelaVeiculo();
            System.out.println("Banco de dados inicializado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Estacionamento ===");
            System.out.println("1. Adicionar veículo");
            System.out.println("2. Buscar veículo por placa");
            System.out.println("3. Remover veículo por placa");
            System.out.println("4. Listar todos os veículos");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Por favor, digite um número entre 1 e 5.");
                continue;
            }

            switch (opcao) {
                case 1: //adiciona o veículo
                    try {
                        System.out.print("Digite a placa (ex.: ABC1234): ");
                        String placa = scanner.nextLine();
                        System.out.print("Digite o modelo: ");
                        String modelo = scanner.nextLine();
                        System.out.print("Digite a cor: ");
                        String cor = scanner.nextLine();
                        System.out.print("Digite o ano: ");
                        int ano = Integer.parseInt(scanner.nextLine());
                        System.out.print("Digite o tipo (ex.: Carro, Moto, Onibus, Caminhonete): ");
                        String tipo = scanner.nextLine();

                        Veiculo veiculo = new Veiculo(tipo, modelo, placa, cor, ano);
                        estacionamento.adicionarVeiculo(veiculo);
                        System.out.println("Veículo adicionado com sucesso: " + veiculo);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro: Ano deve ser um número válido.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.out.println("Erro ao adicionar veículo: " + e.getMessage());
                    }
                    break;

                case 2: //busca veículo
                    try {
                        System.out.print("Digite a placa para busca: ");
                        String placaBusca = scanner.nextLine();
                        Veiculo veiculo = estacionamento.buscarVeiculo(placaBusca);
                        if (veiculo != null) {
                            System.out.println("Veículo encontrado: " + veiculo);
                        } else {
                            System.out.println("Veículo com placa " + placaBusca + " não encontrado.");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Erro ao buscar veículo: " + e.getMessage());
                    }
                    break;

                case 3: //remove veículo
                    try {
                        System.out.print("Digite a placa para remover: ");
                        String placaRemover = scanner.nextLine();
                        boolean removido = estacionamento.removerVeiculo(placaRemover);
                        if (removido) {
                            System.out.println("Veículo com placa " + placaRemover + " removido com sucesso.");
                        } else {
                            System.out.println("Veículo com placa " + placaRemover + " não encontrado.");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Erro ao remover veículo: " + e.getMessage());
                    }
                    break;

                case 4: //Lista os veículos
                    try {
                        List<Veiculo> veiculos = estacionamento.getVeiculos();
                        if (veiculos.isEmpty()) {
                            System.out.println("Nenhum veículo cadastrado.");
                        } else {
                            System.out.println("Veículos cadastrados:");
                            for (Veiculo v : veiculos) {
                                System.out.println(v);
                            }
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Erro ao listar veículos: " + e.getMessage());
                    }
                    break;

                case 5: //para sair
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Por favor, escolha entre 1 e 5.");
            }
        }
    }
}