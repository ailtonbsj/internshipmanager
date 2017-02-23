package br.com.internshipmanager.cursos;

public class ListaDeCursos {
	private long id;
	private String nome;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public ListaDeCursos(long id,String nome) {
		setId(id);
		setNome(nome);
	}
}
