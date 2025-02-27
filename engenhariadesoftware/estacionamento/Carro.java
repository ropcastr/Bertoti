package estacionamento;

public class Carro {
	private String placa;
    private String marca;
    private String modelo;
    private String corCarro;

    public Carro(String placa, String marca, String modelo, String corCarro) {
    	this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.corCarro = corCarro;
    }
    
    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCorCarro() {
        return corCarro;
    }

    public void setCorCarro(String corCarro) {
        this.corCarro = corCarro;
    }

}
