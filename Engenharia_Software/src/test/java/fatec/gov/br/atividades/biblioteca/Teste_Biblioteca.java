package fatec.gov.br.atividades.biblioteca;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Teste_Biblioteca {
    	@Test
	void test() {

		Biblioteca bib = new Biblioteca();
		bib.addLivro(new Livro("Senhor de Aneis", "Tolkien", "123"));
		bib.addLivro(new Livro("Java", "autor", "321"));

		List<Livro> busca = bib.buscarLivroTitulo("Java");
		assertEquals(busca.get(0).getAutor(), "autor");

		Livro liv = bib.buscarLivroIsbn("123");
		assertEquals(liv.getTitulo(), "Senhor de Aneis");









	}
}
