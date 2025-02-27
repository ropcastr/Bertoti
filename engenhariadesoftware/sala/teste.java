package sala;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;


class teste {

	@Test
	void test() {
		SalaAula sala = new SalaAula();
		sala.addAluno(new Aluno("Joao", "123"));
		
		assertEquals(sala.getAlunos().size(), 1);
		
		List<Aluno> alunosEncontrados = sala.buscarAlunoNome("Joao");
		
		assertEquals(alunosEncontrados.get(0).getRa(), "123");
	}

}
