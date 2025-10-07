package fatec.gov.br.atividades.biblioteca;

public class Livro {

	private String titulo;
	private String autor;
	private String isbn;

	public Livro(String titulo, String autor, String isbn) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}
