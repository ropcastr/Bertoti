package fatec.gov.br.atividades.quitanda;

import java.util.ArrayList;
import java.util.List;

public class Quitanda {
    private List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public boolean removerProduto(String nome) {
        return produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    public Produto buscarProduto(String nome) {
        return produtos.stream()
                       .filter(p -> p.getNome().equalsIgnoreCase(nome))
                       .findFirst()
                       .orElse(null);
    }

    public double calcularValorTotal() {
        return produtos.stream()
                       .mapToDouble(p -> p.getPreco() * p.getQuantidade())
                       .sum();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}