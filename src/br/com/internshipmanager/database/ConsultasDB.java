package br.com.internshipmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import br.com.internshipmanager.InternshipManager;

public class ConsultasDB {
	
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	
	public static void criaConexao() throws SQLException {
		connection = DriverManager.getConnection(InternshipManager.DATABASE_URL, InternshipManager.USERNAME, InternshipManager.PASSWORD);
		statement = connection.createStatement();
	}
	
	public static String contarRegistros(String tabelaNome){
		
		try {
			criaConexao();
			resultSet = statement.executeQuery(String.format("SELECT count(*) FROM %s", tabelaNome));
			resultSet.next();
			return resultSet.getObject(1).toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		}
		
	}
	
	public static boolean adicionarRegistros(String tabelaNome,String camposNome, String valorNome){
		try {
			criaConexao();
			statement.executeUpdate(String.format("INSERT INTO %s (%s) VALUES (%s)", tabelaNome, camposNome, valorNome));
			JOptionPane.showMessageDialog(null, "Dados inseridos com Sucesso!!!");
			return true;
		} catch (Exception e) {
			String erro = e.getMessage();
			erro = erro.substring(0, erro.indexOf('\n'));
			String errMatr = "ERRO: duplicar valor da chave viola a restrição de unicidade \"fk_cursoaluno\"";
			String errMatrIgual = "ERRO: duplicar valor da chave viola a restrição de unicidade \"unik_matricula\"";
			if(erro.equals(errMatr))
				JOptionPane.showMessageDialog(null, "Aluno já tem matrícula nesse curso!\nMatrícula não adicionada!");
			else if(erro.equals(errMatrIgual))
				JOptionPane.showMessageDialog(null, "Login de matrícula já existente!\nMatrícula não adicionada!");
			else
				JOptionPane.showMessageDialog(null, erro);
		}
		return false;
	}
	
	public static void removeRegistros(String tabelaNome, String campoNome, String valorId, String campoNome2, String valorId2){
		try{
			criaConexao();
			if(valorId2 != null)
				statement.executeUpdate(String.format("DELETE FROM %s WHERE %s = '%s' AND %s = '%s'", tabelaNome, campoNome, valorId, campoNome2, valorId2));
			else
				statement.executeUpdate(String.format("DELETE FROM %s WHERE %s = '%s'", tabelaNome, campoNome, valorId));
			JOptionPane.showMessageDialog(null, "Dados removidos com Sucesso!!!");
		}
		 catch (Exception e) {
			String erro = e.getMessage();
			erro = erro.substring(0, erro.indexOf('\n'));
			String errCurso = "ERRO: atualização ou exclusão em tabela \"cursos\" viola restrição de chave estrangeira \"fk_curso\" em \"cursos_alunos\"";
			String errOrientador = "ERRO: atualização ou exclusão em tabela \"orientadores\" viola restrição de chave estrangeira \"fk_orientador\" em \"empresas_alunos\"";
			String errEmpresa = "ERRO: atualização ou exclusão em tabela \"empresas\" viola restrição de chave estrangeira \"fk_cnpj\" em \"empresas_alunos\"";
			String errSuperv = "ERRO: atualização ou exclusão em tabela \"supervisores\" viola restrição de chave estrangeira \"fk_superv\" em \"empresas_alunos\"";
			String errEmSup = "ERRO: atualização ou exclusão em tabela \"empresas\" viola restrição de chave estrangeira \"fk_cnpjs\" em \"supervisores\"";
			String errAluno = "ERRO: atualização ou exclusão em tabela \"alunos\" viola restrição de chave estrangeira \"fk_aluno\" em \"cursos_alunos\"";
			String errMat = "ERRO: atualização ou exclusão em tabela \"cursos_alunos\" viola restrição de chave estrangeira \"fk_matricula\" em \"empresas_alunos\"";
			if(erro.equals(errCurso))
				JOptionPane.showMessageDialog(null, "Você não pode excluir esse Curso!\nAlgum Aluno está vinculado a ele.");
			else if(erro.equals(errOrientador))
				JOptionPane.showMessageDialog(null, "Você não pode excluir esse Orientador!\nAlgum Estagiário está vinculado a ele.");
			else if(erro.equals(errEmpresa))
				JOptionPane.showMessageDialog(null, "Você não pode excluir essa Empresa!\nAlgum Estagiário está vinculado a ela.");
			else if(erro.equals(errSuperv))
				JOptionPane.showMessageDialog(null, "Você não pode excluir esse Supervisor!\nAlgum Estagiário está vinculado a ele.");
			else if(erro.equals(errEmSup))
				JOptionPane.showMessageDialog(null, "Você não pode excluir essa Empresa!\nAlgum Supervisor está vinculado a ela.");
			else if(erro.equals(errAluno))
				JOptionPane.showMessageDialog(null, "Você não pode excluir esse Aluno!\nEle está matriculado em algum curso.");
			else if(erro.equals(errMat))
				JOptionPane.showMessageDialog(null, "Você não pode excluir essa Matricula!\nEla ainda está vinculado a algum estágio.");
			else
				JOptionPane.showMessageDialog(null, erro);
			System.out.println(erro);
		}
	}
	
	public static void removeRegistros(String tabelaNome, String campoNome, String valorId){
		removeRegistros(tabelaNome, campoNome, valorId, null, null);
	}
	
	public static void atualizarRegistros(String tabelaNome, String camposEValores, String campoId, String valorId, String campoId2, String valorId2){
		try {
			criaConexao();
			if(campoId2 == null){
				statement.executeUpdate(String.format("UPDATE %s SET %s WHERE %s = '%s'", tabelaNome, camposEValores, campoId, valorId));
			}
			else {
				String consulta = String.format("UPDATE %s SET %s WHERE %s = '%s' AND %s = '%s'", tabelaNome, camposEValores, campoId, valorId, campoId2, valorId2);
				statement.executeUpdate(consulta);
			}
			JOptionPane.showMessageDialog(null, "Dados atualizados com Sucesso!!!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar dados!\n\r\n\r" + e.getMessage());
		}
		
	}
	
	public static void atualizarRegistros(String tabelaNome, String camposEValores, String campoId, String valorId){
		atualizarRegistros(tabelaNome, camposEValores, campoId, valorId, null, null);
	}
	
	public static Statement getStatement(){
		try {
			criaConexao();
		} catch (Exception e) {
		}
		
		return statement;
	}
	
	public static boolean excluirAlunos(String id){
		int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir???", "Alerta", JOptionPane.OK_CANCEL_OPTION);
		if(resposta == 0){
			removeRegistros("alunos", "cpf", id);
			return true;
		}
		return false;
	}
	
	public static boolean excluirEmpresas(String id){
		int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir???", "Alerta", JOptionPane.OK_CANCEL_OPTION);
		if(resposta == 0){
			removeRegistros("empresas", "cnpj", id);
			return true;
		}
		return false;
	}
	
	public static boolean excluirEstagios(String matricula, String cnpj){
		int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir???", "Alerta", JOptionPane.OK_CANCEL_OPTION);
		if(resposta == 0){
			removeRegistros("empresas_alunos", "matricula", matricula,"cnpj",cnpj);
			return true;
		}
		return false;
	}

	public static void criaTabelas(){
		try {
			ConsultasDB.criaConexao();
			statement.executeUpdate("CREATE TABLE alunos (   cpf character varying(11) NOT NULL,   rg character varying(15),   nome character varying(50) NOT NULL,   nascimento date NOT NULL,   endereco character varying(55),   bairro character varying(35),   cidade character varying(40),   email character varying(60),   cep character varying(9),   telefone character varying(12),   celular character varying(12),   mae character varying(50),   pai character varying(50),   uf character(2),   CONSTRAINT pk_cpf PRIMARY KEY (cpf),   CONSTRAINT key_rg UNIQUE (rg) ) WITH (   OIDS=FALSE ); ALTER TABLE alunos   OWNER TO postgres; CREATE TABLE cursos (   id_curso bigint NOT NULL,   curso character varying(60),   CONSTRAINT pk_curso PRIMARY KEY (id_curso) ) WITH (   OIDS=FALSE ); ALTER TABLE cursos   OWNER TO postgres; CREATE TABLE cursos_alunos (   cpf character varying(11) NOT NULL,   id_curso bigint NOT NULL,   matricula character varying(60) NOT NULL,   semestre integer,   CONSTRAINT fk_cursoaluno PRIMARY KEY (cpf, id_curso),   CONSTRAINT fk_aluno FOREIGN KEY (cpf)       REFERENCES alunos (cpf) MATCH SIMPLE       ON UPDATE RESTRICT ON DELETE RESTRICT,   CONSTRAINT fk_curso FOREIGN KEY (id_curso)       REFERENCES cursos (id_curso) MATCH SIMPLE       ON UPDATE RESTRICT ON DELETE RESTRICT,   CONSTRAINT unik_matricula UNIQUE (matricula) ) WITH (   OIDS=FALSE ); ALTER TABLE cursos_alunos   OWNER TO postgres; CREATE TABLE empresas (   cnpj character varying(15) NOT NULL,   nome character varying(50) NOT NULL,   nome_fantasia character varying(50) NOT NULL,   endereco character varying(55),   bairro character varying(35),   cidade character varying(40),   uf character(2),   cep character varying(9),   email character varying(60),   telefone character varying(12),   ramo character varying(45),   atividades character varying(100),   CONSTRAINT pk_cnpj PRIMARY KEY (cnpj) ) WITH (   OIDS=FALSE ); ALTER TABLE empresas   OWNER TO postgres; CREATE TABLE orientadores (   id_orientador bigint NOT NULL,   nome character varying(50),   CONSTRAINT fk_ori PRIMARY KEY (id_orientador) ) WITH (   OIDS=FALSE ); ALTER TABLE orientadores   OWNER TO postgres; CREATE TABLE supervisores (   id_supervisor bigint NOT NULL,   cnpj character varying(15) NOT NULL,   nome character varying(50) NOT NULL,   cargo character varying(50),   CONSTRAINT pk_sup PRIMARY KEY (id_supervisor),   CONSTRAINT fk_cnpjs FOREIGN KEY (cnpj)       REFERENCES empresas (cnpj) MATCH SIMPLE       ON UPDATE RESTRICT ON DELETE RESTRICT ) WITH (   OIDS=FALSE ); ALTER TABLE supervisores   OWNER TO postgres; CREATE TABLE empresas_alunos (   cnpj character varying(15) NOT NULL,   matricula character varying(60) NOT NULL,   data_inicio date,   data_fim date,   id_orientador bigint,   atividades character varying(100),   data_cadastro date,   setor_estagio character varying(60),   horario integer,   supervisor bigint,   horario_inicio time without time zone,   horario_fim time without time zone,   CONSTRAINT pk_empalu PRIMARY KEY (cnpj, matricula),   CONSTRAINT fk_cnpj FOREIGN KEY (cnpj)       REFERENCES empresas (cnpj) MATCH SIMPLE       ON UPDATE RESTRICT ON DELETE RESTRICT,   CONSTRAINT fk_matricula FOREIGN KEY (matricula)       REFERENCES cursos_alunos (matricula) MATCH SIMPLE       ON UPDATE RESTRICT ON DELETE RESTRICT,   CONSTRAINT fk_orientador FOREIGN KEY (id_orientador)       REFERENCES orientadores (id_orientador) MATCH SIMPLE       ON UPDATE RESTRICT ON DELETE RESTRICT,   CONSTRAINT fk_superv FOREIGN KEY (supervisor)       REFERENCES supervisores (id_supervisor) MATCH SIMPLE       ON UPDATE RESTRICT ON DELETE RESTRICT ) WITH (   OIDS=FALSE ); ALTER TABLE empresas_alunos   OWNER TO postgres;");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
