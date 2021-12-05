package io.github.ailtonbsj.internshipmanager.orientadores;

public class ListaDeOrientadores {
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

	public ListaDeOrientadores(long id, String nome){
		setId(id);
		setNome(nome);
	}

}
