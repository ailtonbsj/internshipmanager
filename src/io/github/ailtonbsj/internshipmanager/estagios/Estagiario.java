package io.github.ailtonbsj.internshipmanager.estagios;

public class Estagiario {
	private String matricula;
	private String cnpj;
	private String nome;
	private String empresa;
	
	public Estagiario(String matricula, String cnpj, String nome, String empresa) {
		setMatricula(matricula);
		setCnpj(cnpj);
		setNome(nome);
		setEmpresa(empresa);
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}
