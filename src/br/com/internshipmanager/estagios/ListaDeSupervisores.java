package br.com.internshipmanager.estagios;

public class ListaDeSupervisores {
	
	private long id;
	private String nome;
	private String cargo;
	
	public ListaDeSupervisores(long id, String nome, String cargo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
	}
	
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
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	

}
