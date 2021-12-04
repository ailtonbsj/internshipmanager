package io.github.ailtonbsj.internshipmanager.alunos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import io.github.ailtonbsj.internshipmanager.InternshipManager;
import io.github.ailtonbsj.internshipmanager.UpCaseField;
import io.github.ailtonbsj.internshipmanager.cursos.ListaDeCursos;
import io.github.ailtonbsj.internshipmanager.database.Constantes;
import io.github.ailtonbsj.internshipmanager.database.ConsultasDB;
import io.github.ailtonbsj.internshipmanager.database.ConversorDates;
import io.github.ailtonbsj.internshipmanager.database.ResultSetSQL;

@SuppressWarnings("serial")
public class JanelaCriarAtualizarAlunos extends JInternalFrame {
	private JTextField textFieldNome;
	private JTextField textFieldRG;
	private JTextField textFieldEndereco;
	private JTextField textFieldBairro;
	private JTextField textFieldCidade;
	private JTextField textFieldTelefone;
	private JTextField textFieldCelular;
	private JTextField textFieldMae;
	private JTextField textFieldPai;
	private JFormattedTextField ftfcpf;
	private JFormattedTextField ftfNascimento;
	private JFormattedTextField formattedTextFieldEmail;
	private JFormattedTextField formattedTextFieldCep;
	private JComboBox<String> comboBoxUf;
	private JTextField textFieldMatricula;
	private JTextField textFieldSemestre;
	private JTable table;
	private String cpf;
	private JComboBox<String> comboBoxCursos;
	private ArrayList<ListaDeCursos> lista;
	private String uf;
	private String pai;
	private String mae;
	private String celular;
	private String telefone;
	private String cep;
	private String email;
	private String cidade;
	private String bairro;
	private String endereco;
	private String nascimento;
	private String nome;
	private String rg;
	private JPanel panelCursos;
	private ResultSetSQL tableModel;
	private String sql;
	private JButton btnAdicionar;
	private JButton btnExcluirCurso;
	private JButton btnRemoverAluno;
	private JButton btnNovoAluno;
	private JButton btnSalvar;
	
	public char[] modelocpf = "___.___.___-__".toCharArray();
	public int indexModelocpf = 0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCriarAtualizarAlunos frame = new JanelaCriarAtualizarAlunos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JanelaCriarAtualizarAlunos() {
		PlainDocument doc1 = new PlainDocument();
		doc1.setDocumentFilter(new DocumentFilter() {
		    @Override
		    public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) 
		        throws BadLocationException 
		    {
		        fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // remove non-digits
		    } 
		    @Override
		    public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr) 
		        throws BadLocationException 
		    {
		        fb.replace(off, len, str.replaceAll("\\D++", ""), attr);  // remove non-digits
		    }
		});
		PlainDocument doc2 = new PlainDocument();
		doc2.setDocumentFilter(new DocumentFilter() {
		    @Override
		    public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) 
		        throws BadLocationException 
		    {
		        fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // remove non-digits
		    } 
		    @Override
		    public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr) 
		        throws BadLocationException 
		    {
		        fb.replace(off, len, str.replaceAll("\\D++", ""), attr);  // remove non-digits
		    }
		});
		PlainDocument doc3 = new PlainDocument();
		doc3.setDocumentFilter(new DocumentFilter() {
		    @Override
		    public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) 
		        throws BadLocationException 
		    {
		        fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // remove non-digits
		    } 
		    @Override
		    public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr) 
		        throws BadLocationException 
		    {
		        fb.replace(off, len, str.replaceAll("\\D++", ""), attr);  // remove non-digits
		    }
		});
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 739, 461);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JPanel panelInfPessoal = new JPanel();
		panelInfPessoal.setBounds(10, 11, 707, 178);
		panel_2.add(panelInfPessoal);
		panelInfPessoal.setBorder(new TitledBorder(null,
				"Informa\u00E7\u00F5es Pessoais", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelInfPessoal.setLayout(null);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 24, 44, 14);
		panelInfPessoal.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setDocument(new UpCaseField());
		textFieldNome.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				liberaBotoes();
			}
		});
		lblNome.setLabelFor(textFieldNome);
		textFieldNome.setBounds(47, 21, 391, 20);
		panelInfPessoal.add(textFieldNome);
		textFieldNome.setColumns(10);

		JLabel lblcpf = new JLabel("cpf");
		lblcpf.setBounds(448, 24, 46, 14);
		panelInfPessoal.add(lblcpf);
		
		try {
			MaskFormatter maskcpf = new MaskFormatter("###.###.###-##");
			maskcpf.setPlaceholderCharacter('_');
			ftfcpf = new JFormattedTextField(maskcpf);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		ftfcpf.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				liberaBotoes();
			}
		});

		lblcpf.setLabelFor(ftfcpf);
		ftfcpf.setBounds(483, 21, 216, 20);
		panelInfPessoal.add(ftfcpf);

		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setBounds(448, 74, 111, 14);
		panelInfPessoal.add(lblDataDeNascimento);
		
		try {
			MaskFormatter maskData = new MaskFormatter("##/##/####");
			maskData.setPlaceholderCharacter('_');
			ftfNascimento = new JFormattedTextField(maskData);
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		ftfNascimento.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				liberaBotoes();
			}
		});
		ftfNascimento.setBounds(557, 71, 142, 20);
		panelInfPessoal.add(ftfNascimento);
		lblDataDeNascimento.setLabelFor(ftfNascimento);

		JLabel lblRg = new JLabel("RG");
		lblRg.setBounds(448, 49, 46, 14);
		panelInfPessoal.add(lblRg);

		textFieldRG = new JTextField();
		lblRg.setLabelFor(textFieldRG);
		textFieldRG.setBounds(483, 46, 216, 20);
		panelInfPessoal.add(textFieldRG);
		textFieldRG.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(10, 99, 53, 14);
		panelInfPessoal.add(lblEndereo);

		textFieldEndereco = new JTextField();
		textFieldEndereco.setDocument(new UpCaseField());
		lblEndereo.setLabelFor(textFieldEndereco);
		textFieldEndereco.setBounds(67, 96, 371, 20);
		panelInfPessoal.add(textFieldEndereco);
		textFieldEndereco.setColumns(10);

		JLabel lblMe = new JLabel("M\u00E3e");
		lblMe.setBounds(10, 49, 46, 14);
		panelInfPessoal.add(lblMe);

		textFieldMae = new JTextField();
		textFieldMae.setDocument(new UpCaseField());
		lblMe.setLabelFor(textFieldMae);
		textFieldMae.setBounds(47, 46, 391, 20);
		panelInfPessoal.add(textFieldMae);
		textFieldMae.setColumns(10);

		JLabel lblPai = new JLabel("Pai");
		lblPai.setBounds(10, 74, 46, 14);
		panelInfPessoal.add(lblPai);

		textFieldPai = new JTextField();
		textFieldPai.setDocument(new UpCaseField());
		lblPai.setLabelFor(textFieldPai);
		textFieldPai.setBounds(47, 71, 391, 20);
		panelInfPessoal.add(textFieldPai);
		textFieldPai.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(448, 99, 46, 14);
		panelInfPessoal.add(lblBairro);

		textFieldBairro = new JTextField();
		textFieldBairro.setDocument(new UpCaseField());
		lblBairro.setLabelFor(textFieldBairro);
		textFieldBairro.setBounds(483, 96, 215, 20);
		panelInfPessoal.add(textFieldBairro);
		textFieldBairro.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(10, 124, 46, 14);
		panelInfPessoal.add(lblCidade);

		textFieldCidade = new JTextField();
		textFieldCidade.setDocument(new UpCaseField());
		lblCidade.setLabelFor(textFieldCidade);
		textFieldCidade.setBounds(66, 121, 153, 20);
		panelInfPessoal.add(textFieldCidade);
		textFieldCidade.setColumns(10);

		JLabel lblCep = new JLabel("CEP");
		lblCep.setBounds(229, 124, 46, 14);
		panelInfPessoal.add(lblCep);

		formattedTextFieldCep = new JFormattedTextField();
		formattedTextFieldCep.setDocument(doc1);
		lblCep.setLabelFor(formattedTextFieldCep);
		formattedTextFieldCep.setBounds(259, 121, 179, 20);
		panelInfPessoal.add(formattedTextFieldCep);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(10, 149, 46, 14);
		panelInfPessoal.add(lblEmail);

		formattedTextFieldEmail = new JFormattedTextField();
		lblEmail.setLabelFor(formattedTextFieldEmail);
		formattedTextFieldEmail.setBounds(47, 146, 227, 20);
		panelInfPessoal.add(formattedTextFieldEmail);

		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(284, 149, 46, 14);
		panelInfPessoal.add(lblCelular);

		textFieldCelular = new JTextField();
		textFieldCelular.setDocument(doc2);
		lblCelular.setLabelFor(textFieldCelular);
		textFieldCelular.setBounds(323, 146, 115, 20);
		panelInfPessoal.add(textFieldCelular);
		textFieldCelular.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(448, 149, 46, 14);
		panelInfPessoal.add(lblTelefone);

		textFieldTelefone = new JTextField();
		textFieldTelefone.setDocument(doc3);
		lblTelefone.setLabelFor(textFieldTelefone);
		textFieldTelefone.setBounds(497, 146, 202, 20);
		panelInfPessoal.add(textFieldTelefone);
		textFieldTelefone.setColumns(10);

		JLabel lblUf = new JLabel("UF");
		lblUf.setBounds(448, 124, 46, 14);
		panelInfPessoal.add(lblUf);

		comboBoxUf = new JComboBox<String>();
		comboBoxUf
				.setModel(new DefaultComboBoxModel<String>(Constantes.estados));

		lblUf.setLabelFor(comboBoxUf);
		comboBoxUf.setBounds(483, 121, 214, 20);
		panelInfPessoal.add(comboBoxUf);

		panelCursos = new JPanel();
		panelCursos.setBounds(10, 189, 707, 222);
		panel_2.add(panelCursos);
		panelCursos.setBorder(new TitledBorder(null, "Cursos",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCursos.setLayout(new BorderLayout(0, 0));

		JPanel panelAddCurso = new JPanel();
		panelAddCurso.setPreferredSize(new Dimension(10, 70));
		panelCursos.add(panelAddCurso, BorderLayout.SOUTH);
		panelAddCurso.setLayout(null);

		JLabel lblCurso = new JLabel("Curso");
		lblCurso.setBounds(10, 45, 46, 14);
		panelAddCurso.add(lblCurso);

		comboBoxCursos = new JComboBox<String>();
		comboBoxCursos.setBounds(53, 42, 238, 20);
		panelAddCurso.add(comboBoxCursos);

		JLabel lblMatricula = new JLabel("Matricula");
		lblMatricula.setBounds(302, 45, 46, 14);
		panelAddCurso.add(lblMatricula);

		textFieldMatricula = new JTextField();
		textFieldMatricula.setBounds(358, 42, 175, 20);
		panelAddCurso.add(textFieldMatricula);
		textFieldMatricula.setColumns(10);

		JLabel lblSemestre = new JLabel("Semestre");
		lblSemestre.setBounds(543, 45, 46, 14);
		panelAddCurso.add(lblSemestre);

		textFieldSemestre = new JTextField();
		textFieldSemestre.setBounds(599, 42, 86, 20);
		panelAddCurso.add(textFieldSemestre);
		textFieldSemestre.setColumns(10);

		btnAdicionar = new JButton("Adicionar Curso");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String matriculaCurso = textFieldMatricula.getText();
				String semestre = textFieldSemestre.getText();
				if (!matriculaCurso.equals("") && !semestre.equals("")) {
					ListaDeCursos opcao = lista.get(comboBoxCursos
							.getSelectedIndex());
					long idCurso = opcao.getId();
					ConsultasDB.adicionarRegistros("cursos_alunos",
							"cpf, id_curso, matricula, semestre", "'" + cpf
									+ "','" + idCurso + "','" + matriculaCurso
									+ "','" + semestre + "'");
					try {
						tableModel.setQuery(sql);
						textFieldSemestre.setText("");
						textFieldMatricula.setText("");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Preencha os campos de Matr�cula e Semestre");
				}
			}
		});
		btnAdicionar.setBounds(10, 11, 152, 23);
		panelAddCurso.add(btnAdicionar);

		btnExcluirCurso = new JButton("Excluir Curso");
		btnExcluirCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String valor1 = linhaSelecionada(0);
				if (!valor1.equals("")) {
					String valor2 = linhaSelecionada(2);
					ConsultasDB.removeRegistros("cursos_alunos", "id_curso",
							valor1, "matricula", valor2);
					try {
						tableModel.setQuery(sql);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnExcluirCurso.setBounds(172, 11, 152, 23);
		panelAddCurso.add(btnExcluirCurso);

		JScrollPane scrollPane = new JScrollPane();
		panelCursos.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();

		scrollPane.setViewportView(table);

		JPanel panelBotoes = new JPanel();
		getContentPane().add(panelBotoes, BorderLayout.NORTH);
		panelBotoes.setLayout(new GridLayout(1, 3, 15, 0));

		btnNovoAluno = new JButton("Novo Aluno");
		btnNovoAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparCampos();
			}
		});
		panelBotoes.add(btnNovoAluno);

		btnRemoverAluno = new JButton("Remover Aluno");
		btnRemoverAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ConsultasDB.excluirAlunos(cpf)) {
					setVisible(false);
				}
			}
		});
		panelBotoes.add(btnRemoverAluno);

		btnSalvar = new JButton("Salvar Altera\u00E7\u00F5es");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cpf == null) {
					if(JanelaCriarAtualizarAlunos.cpf(ftfcpf.getText().replace("-", "").replace(".", "")))	inserirUsuario();
					else JOptionPane.showMessageDialog(null, "cpf Inv�lido");
				} else {
					atualizaUsuario();
				}
			}
		});
		panelBotoes.add(btnSalvar);

	}

	public void setEnablePaneCursos(boolean v) {
		table.setEnabled(v);
		if (!v) {
			table.setModel(new DefaultTableModel(new Object[][] {},
					new String[] {}));
		}
		comboBoxCursos.setEnabled(v);
		textFieldMatricula.setEnabled(v);
		textFieldSemestre.setEnabled(v);
		btnAdicionar.setEnabled(v);
		btnExcluirCurso.setEnabled(v);
	}

	public void limparCampos() {
		cpf = null;

		ftfcpf.setText("");
		textFieldRG.setText("");
		textFieldNome.setText("");
		ftfNascimento.setText("");
		textFieldEndereco.setText("");
		textFieldBairro.setText("");
		textFieldCidade.setText("");
		formattedTextFieldEmail.setText("");
		formattedTextFieldCep.setText("");
		textFieldTelefone.setText("");
		textFieldCelular.setText("");
		textFieldMae.setText("");
		textFieldPai.setText("");
		preencheListaDeCursos();
		comboBoxUf.setSelectedItem("CE");

		ftfcpf.setEditable(true);
		btnNovoAluno.setEnabled(false);
		btnRemoverAluno.setEnabled(false);
		btnSalvar.setEnabled(false);
		setEnablePaneCursos(false);
	}

	public void atualizarCampos(String reg) {
		try {
			Statement busca = ConsultasDB.getStatement();
			ResultSet resultado = busca
					.executeQuery("SELECT * FROM alunos WHERE cpf = '" + reg
							+ "'");
			resultado.next();

			cpf = (resultado.getObject("cpf") == null) ? "" : resultado
					.getObject("cpf").toString();
			rg = (resultado.getObject("rg") == null) ? "" : resultado
					.getObject("rg").toString();
			nome = (resultado.getObject("nome") == null) ? "" : resultado
					.getObject("nome").toString();
			nascimento = (resultado.getObject("nascimento") == null) ? ""
					: resultado.getObject("nascimento").toString();
			nascimento = ConversorDates.DateBaseToDateCommon(nascimento);
			endereco = (resultado.getObject("endereco") == null) ? ""
					: resultado.getObject("endereco").toString();
			bairro = (resultado.getObject("bairro") == null) ? "" : resultado
					.getObject("bairro").toString();
			cidade = (resultado.getObject("cidade") == null) ? "" : resultado
					.getObject("cidade").toString();
			email = (resultado.getObject("email") == null) ? "" : resultado
					.getObject("email").toString();
			cep = (resultado.getObject("cep") == null) ? "" : resultado
					.getObject("cep").toString();
			telefone = (resultado.getObject("telefone") == null) ? ""
					: resultado.getObject("telefone").toString();
			celular = (resultado.getObject("celular") == null) ? "" : resultado
					.getObject("celular").toString();
			mae = (resultado.getObject("mae") == null) ? "" : resultado
					.getObject("mae").toString();
			pai = (resultado.getObject("pai") == null) ? "" : resultado
					.getObject("pai").toString();
			uf = (resultado.getObject("uf") == null) ? "" : resultado
					.getObject("uf").toString();

			ftfcpf.setText(cpf);
			textFieldRG.setText(rg);
			textFieldNome.setText(nome);
			ftfNascimento.setText(nascimento);
			textFieldEndereco.setText(endereco);
			textFieldBairro.setText(bairro);
			textFieldCidade.setText(cidade);
			formattedTextFieldEmail.setText(email);
			formattedTextFieldCep.setText(cep);
			textFieldTelefone.setText(telefone);
			textFieldCelular.setText(celular);
			textFieldMae.setText(mae);
			textFieldPai.setText(pai);
			preencheListaDeCursos();
			comboBoxUf.setSelectedItem(uf);

			sql = "SELECT cursos.id_curso, curso, matricula, semestre FROM cursos_alunos, cursos, alunos WHERE cursos_alunos.id_curso = cursos.id_curso AND cursos_alunos.cpf = alunos.cpf AND cursos_alunos.cpf = '"
					+ reg + "'";
			tableModel = new ResultSetSQL(InternshipManager.DATABASE_URL,
					InternshipManager.USERNAME, InternshipManager.PASSWORD, sql);
			table.setModel(tableModel);
			table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

			ftfcpf.setEditable(false);
			btnNovoAluno.setEnabled(true);
			btnRemoverAluno.setEnabled(true);
			btnSalvar.setEnabled(true);
			setEnablePaneCursos(true);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}

	public void preencheListaDeCursos() {
		lista = new ArrayList<ListaDeCursos>();

		try {

			Statement busca = ConsultasDB.getStatement();
			ResultSet resultado = busca.executeQuery("SELECT * FROM cursos");
			int cont = 0;
			while (resultado.next()) {
				lista.add(cont, new ListaDeCursos(resultado.getLong(1),
						resultado.getString(2)));
				cont++;
			}

			comboBoxCursos.removeAllItems();
			for (int i = 0; i < cont; i++) {
				comboBoxCursos.addItem(lista.get(i).getNome());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void liberaBotoes() {
		
		if(
				!textFieldNome.getText().isEmpty() &&
				!ftfcpf.getText().replace("_", "").replace(".","").replace("-","").isEmpty() &&
				!ftfNascimento.getText().replace("/","").replace("_", "").isEmpty()
		)
		btnSalvar.setEnabled(true);
		else  btnSalvar.setEnabled(false);
	}

	public void captaDadosDosCampos(){
		cpf = ftfcpf.getText().replace(".", "").replace("-", "");
		rg = (textFieldRG.getText().isEmpty()) ? "NULL" : "'" + textFieldRG.getText() + "'";
		nome = textFieldNome.getText();
		nascimento = ConversorDates.DateCommonToDateBase(ftfNascimento.getText());
		endereco = textFieldEndereco.getText();
		bairro = textFieldBairro.getText();
		cidade = textFieldCidade.getText();
		email = formattedTextFieldEmail.getText();
		cep = formattedTextFieldCep.getText();
		telefone = textFieldTelefone.getText();
		celular = textFieldCelular.getText();
		mae = textFieldMae.getText();
		pai = textFieldPai.getText();
		uf = comboBoxUf.getItemAt(comboBoxUf.getSelectedIndex());
	}
	
	public void inserirUsuario(){
		boolean result = false;
		captaDadosDosCampos();
		String valores = String.format("'%s',%s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'", cpf,rg, nome, nascimento, endereco, bairro, cidade, email,cep, telefone, celular, mae, pai, uf);
		result = ConsultasDB.adicionarRegistros("alunos", "cpf, rg, nome, nascimento, endereco, bairro, cidade, email,cep, telefone, celular, mae, pai, uf", valores);
		if(result){
			atualizarCampos(cpf);
		}
	}
	
	public void atualizaUsuario() {
		captaDadosDosCampos();
		String camposEValores = String
				.format("rg =  '%s', nome = '%s', nascimento = '%s', endereco = '%s', bairro = '%s', cidade = '%s', email = '%s', cep = '%s', telefone = '%s', celular = '%s', mae = '%s', pai = '%s', uf = '%s'",
						rg, nome, nascimento, endereco, bairro, cidade, email,cep, telefone, celular, mae, pai, uf);
		ConsultasDB.atualizarRegistros("alunos", camposEValores, "cpf", cpf);

	}

	public String linhaSelecionada(int posicao) {

		if (table.getSelectedRow() != -1) {
			String chavePrimaria = tableModel.getValueAt(
					table.getSelectedRow(), posicao).toString();
			return chavePrimaria;
		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma Linha");
		}
		return "";
	}
	
	public static boolean cpf(String cpf){
		String str;
		int[] verificador = new int[2];
		str = cpf.replace("-", "");
		str = str.replace(".", "");
		
		if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
		        cpf.equals("22222222222") || cpf.equals("33333333333") ||
		        cpf.equals("44444444444") || cpf.equals("55555555555") ||
		        cpf.equals("66666666666") || cpf.equals("77777777777") ||
		        cpf.equals("88888888888") || cpf.equals("99999999999") ||
		       (cpf.length() != 11))
		       return(false);

		if(str.length() == 11){
			char[] num = str.toCharArray();
			int  valor = 0;
			for(int i = 0, y = 10 ; i<=9 && y >= 2; i++, y--){
				valor = Character.getNumericValue(num[i])*y+valor;
				//System.out.print(num[i]);
			}
			if((valor % 11) < 2){
				verificador[0] = 0;
			}
			else{
				verificador[0] = 11 - (valor % 11);
			}
			int[] num2 = new int[10];
			for(int i = 0; i <= 8; i++){
				num2[i] = Character.getNumericValue(num[i]);
			}
			num2[9] = verificador[0];
			//System.out.println(Arrays.toString(num2));
			int valor2 = 0;
			for(int i = 0, y = 11 ; i <= 10 && y >= 2; i++, y--){
				valor2 = Character.getNumericValue(num[i])*y+valor2;
				//System.out.println(valor2);
			}
			if((valor2 % 11) < 2){
				verificador[1] = 0;
			}
			else{
				verificador[1] = 11 - (valor2 % 11);
			}
			if(verificador[0] == Character.getNumericValue(num[9]) && verificador[1] == Character.getNumericValue(num[10])){
				return true;
			}
			else{
				return false;
			}
		}
		
		else{
			
			return false;
		
		}		
	}
}
