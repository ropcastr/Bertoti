package fatec.gov.br.atividades.estacionamento;

import java.time.Year;
import java.util.Objects;

public class Veiculo {
    private final String tipo;
    private final String modelo;
    private final String placa;
    private final String cor;
    private final int ano;

    public Veiculo(String tipo, String modelo, String placa, String cor, int ano) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo não pode ser nulo ou vazio");
        }
        if (cor == null || cor.trim().isEmpty()) {
            throw new IllegalArgumentException("Cor não pode ser nula ou vazia");
        }
        if (ano < 1886 || ano > Year.now().getValue() + 1) { //para permitir inserir até o ano atual + 1
            throw new IllegalArgumentException("Ano inválido");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }
        this.placa = placa.trim().toUpperCase();
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.tipo = tipo;
    }

    // Apenas Getters, sem Setters
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public String getCor() { return cor; }
    public int getAno() { return ano; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        return "Veiculo [tipo=" + tipo + ", modelo=" + modelo + ", placa=" + placa + ", cor=" + cor + ", ano=" + ano + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Veiculo veiculo)) return false;
        return Objects.equals(placa, veiculo.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa);
    }
}