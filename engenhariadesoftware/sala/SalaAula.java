package sala;

import java.util.List;
import java.util.LinkedList;


public class SalaAula {
	
	private List<Aluno> alunos = new LinkedList<Aluno>();
	
	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
		
	}
	
	public List<Aluno> getAlunos(){
		return alunos;
		
	}
	
	public List<Aluno> buscarAlunoNome(String nome){
		List<Aluno> encontrados = new LinkedList<Aluno>();
		for(Aluno aluno:alunos) {
			if(aluno.getNome().equals(nome)) encontrados.add(aluno);
		}
		return encontrados;
		
	}

}
