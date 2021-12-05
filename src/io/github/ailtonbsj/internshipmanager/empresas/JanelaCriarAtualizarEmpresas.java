package io.github.ailtonbsj.internshipmanager.empresas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import io.github.ailtonbsj.internshipmanager.UpCaseField;
import io.github.ailtonbsj.internshipmanager.database.Constantes;
import io.github.ailtonbsj.internshipmanager.database.ConsultasDB;
import io.github.ailtonbsj.internshipmanager.database.GeradorDeIds;
import io.github.ailtonbsj.internshipmanager.database.ResultSetSQL;

@SuppressWarnings("serial")
public class JanelaCriarAtualizarEmpresas extends JInternalFrame {
	private JTextField textFieldNome;
	private JTextField textFieldEndereco;
	private JTextField textFieldBairro;
	private JTextField textFieldCidade;
	private JTextField textFieldTelefone;
	private JTextField textFieldFantasia;
	private JFormattedTextField formattedTextFieldCNPJ;
	private JFormattedTextField formattedTextFieldEmail;
	private JFormattedTextField formattedTextFieldCep;
	private JComboBox<String> comboBoxUf;
	private JButton btnRemoverEmpresa;
	private JButton btnNovoEmpresa;
	private JButton btnSalvar;
	private JTextField textFieldRamo;
	private JTextField textFieldAtividades;
	
	private String cnpj;
	private String nome;
	private String nomeFantasia;
	private String endereco;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	private String email;
	private String telefone;
	private String ramo;
	private String atividades;
	private JTextField tfNome;
	private JTextField tfCargo;
	private JTable table;
	private String sql;
	private ResultSetSQL tableModel;
	private JButton btnAdicionarSupervisor;
	private JButton btnExcluirSupervisor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCriarAtualizarEmpresas frame = new JanelaCriarAtualizarEmpresas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JanelaCriarAtualizarEmpresas() {
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
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 797, 458);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JPanel panelInfPessoal = new JPanel();
		panelInfPessoal.setBounds(10, 11, 765, 185);
		panel_2.add(panelInfPessoal);
		panelInfPessoal.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados da Empresa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		textFieldNome.setBounds(72, 24, 391, 20);
		panelInfPessoal.add(textFieldNome);
		textFieldNome.setColumns(10);

		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(475, 24, 46, 14);
		panelInfPessoal.add(lblCnpj);

		try {
			MaskFormatter maskCnpj = new MaskFormatter("##.###.###/####-##");
			maskCnpj.setPlaceholderCharacter('_');
			formattedTextFieldCNPJ = new JFormattedTextField(maskCnpj);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		formattedTextFieldCNPJ.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				liberaBotoes();
			}
		});

		lblCnpj.setLabelFor(formattedTextFieldCNPJ);
		formattedTextFieldCNPJ.setBounds(537, 24, 216, 20);
		panelInfPessoal.add(formattedTextFieldCNPJ);

		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(10, 80, 76, 14);
		panelInfPessoal.add(lblEndereo);

		textFieldEndereco = new JTextField();
		textFieldEndereco.setDocument(new UpCaseField());
		lblEndereo.setLabelFor(textFieldEndereco);
		textFieldEndereco.setBounds(92, 80, 371, 20);
		panelInfPessoal.add(textFieldEndereco);
		textFieldEndereco.setColumns(10);

		JLabel lblFantasia = new JLabel("Nome Fantasia");
		lblFantasia.setBounds(10, 52, 106, 14);
		panelInfPessoal.add(lblFantasia);

		textFieldFantasia = new JTextField();
		textFieldFantasia.setDocument(new UpCaseField());
		lblFantasia.setLabelFor(textFieldFantasia);
		textFieldFantasia.setBounds(123, 52, 340, 20);
		panelInfPessoal.add(textFieldFantasia);
		textFieldFantasia.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(475, 55, 46, 14);
		panelInfPessoal.add(lblBairro);

		textFieldBairro = new JTextField();
		textFieldBairro.setDocument(new UpCaseField());
		lblBairro.setLabelFor(textFieldBairro);
		textFieldBairro.setBounds(537, 52, 215, 20);
		panelInfPessoal.add(textFieldBairro);
		textFieldBairro.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(475, 79, 60, 14);
		panelInfPessoal.add(lblCidade);

		textFieldCidade = new JTextField();
		textFieldCidade.setDocument(new UpCaseField());
		lblCidade.setLabelFor(textFieldCidade);
		textFieldCidade.setBounds(537, 80, 216, 20);
		panelInfPessoal.add(textFieldCidade);
		textFieldCidade.setColumns(10);

		JLabel lblCep = new JLabel("CEP");
		lblCep.setBounds(245, 108, 36, 14);
		panelInfPessoal.add(lblCep);

		formattedTextFieldCep = new JFormattedTextField();
		formattedTextFieldCep.setDocument(doc2);
		lblCep.setLabelFor(formattedTextFieldCep);
		formattedTextFieldCep.setBounds(284, 107, 179, 20);
		panelInfPessoal.add(formattedTextFieldCep);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(10, 108, 46, 14);
		panelInfPessoal.add(lblEmail);

		formattedTextFieldEmail = new JFormattedTextField();
		lblEmail.setLabelFor(formattedTextFieldEmail);
		formattedTextFieldEmail.setBounds(62, 105, 172, 20);
		panelInfPessoal.add(formattedTextFieldEmail);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 133, 68, 14);
		panelInfPessoal.add(lblTelefone);

		textFieldTelefone = new JTextField();
		textFieldTelefone.setDocument(doc1);
		lblTelefone.setLabelFor(textFieldTelefone);
		textFieldTelefone.setBounds(81, 131, 202, 20);
		panelInfPessoal.add(textFieldTelefone);
		textFieldTelefone.setColumns(10);

		JLabel lblUf = new JLabel("UF");
		lblUf.setBounds(475, 107, 46, 14);
		panelInfPessoal.add(lblUf);

		comboBoxUf = new JComboBox<String>();
		comboBoxUf
				.setModel(new DefaultComboBoxModel<String>(Constantes.estados));

		lblUf.setLabelFor(comboBoxUf);
		comboBoxUf.setBounds(537, 107, 216, 20);
		panelInfPessoal.add(comboBoxUf);
		
		textFieldRamo = new JTextField();
		textFieldRamo.setDocument(new UpCaseField());
		textFieldRamo.setBounds(344, 133, 409, 20);
		panelInfPessoal.add(textFieldRamo);
		textFieldRamo.setColumns(10);
		
		JLabel lblRamo = new JLabel("Ramo");
		lblRamo.setLabelFor(textFieldRamo);
		lblRamo.setBounds(301, 133, 46, 14);
		panelInfPessoal.add(lblRamo);
		
		JLabel lblAtividades = new JLabel("Atividades");
		lblAtividades.setBounds(10, 158, 86, 14);
		panelInfPessoal.add(lblAtividades);
		
		textFieldAtividades = new JTextField();
		textFieldAtividades.setDocument(new UpCaseField());
		lblAtividades.setLabelFor(textFieldAtividades);
		textFieldAtividades.setBounds(92, 158, 661, 20);
		panelInfPessoal.add(textFieldAtividades);
		textFieldAtividades.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Supervisores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 204, 765, 194);
		panel_2.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 70));
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(null);
		
		btnAdicionarSupervisor = new JButton("Adicionar Supervisor");
		btnAdicionarSupervisor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = tfNome.getText();
				String cargo = tfCargo.getText();
				if (!nome.equals("") && !cargo.equals("")) {
					ConsultasDB.adicionarRegistros("supervisores", "id_supervisor,cnpj,nome,cargo", "'"+ GeradorDeIds.getNewId() +"','"+ cnpj +"','"+ nome +"','"+ cargo +"'");
					try {
						tableModel.setQuery(sql);
						tfCargo.setText("");
						tfNome.setText("");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Preencha o nome e o cargo do supervisor!");
				}
			}
		});
		btnAdicionarSupervisor.setBounds(10, 11, 203, 23);
		panel_1.add(btnAdicionarSupervisor);
		
		btnExcluirSupervisor = new JButton("Excluir Supervisor");
		btnExcluirSupervisor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valor1 = linhaSelecionada(0);
				if (!valor1.equals("")) {
					ConsultasDB.removeRegistros("supervisores", "id_supervisor", valor1);
					try {
						tableModel.setQuery(sql);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnExcluirSupervisor.setBounds(225, 11, 174, 23);
		panel_1.add(btnExcluirSupervisor);
		
		JLabel lblNomeDoSupervisor = new JLabel("Nome");
		lblNomeDoSupervisor.setBounds(10, 48, 46, 14);
		panel_1.add(lblNomeDoSupervisor);
		
		tfNome = new JTextField();
		tfNome.setDocument(new UpCaseField());
		tfNome.setBounds(54, 46, 265, 20);
		panel_1.add(tfNome);
		tfNome.setColumns(10);
		
		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(329, 48, 46, 14);
		panel_1.add(lblCargo);
		
		tfCargo = new JTextField();
		tfCargo.setDocument(new UpCaseField());
		tfCargo.setBounds(384, 46, 359, 20);
		panel_1.add(tfCargo);
		tfCargo.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panelBotoes = new JPanel();
		getContentPane().add(panelBotoes, BorderLayout.NORTH);
		panelBotoes.setLayout(new GridLayout(1, 3, 15, 0));

		btnNovoEmpresa = new JButton("Nova Empresa");
		btnNovoEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparCampos();
			}
		});
		panelBotoes.add(btnNovoEmpresa);

		btnRemoverEmpresa = new JButton("Remover Empresa");
		btnRemoverEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ConsultasDB.excluirEmpresas(cnpj)) {
					setVisible(false);
				}
			}
		});
		panelBotoes.add(btnRemoverEmpresa);

		btnSalvar = new JButton("Salvar Altera\u00E7\u00F5es");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cnpj == null) {
					inserirEmpresa();
				} else {
					atualizaEmpresa();
				}
			}
		});
		panelBotoes.add(btnSalvar);

	}
	
	public void atualizarCampos(String reg) {
		try {
			Statement busca = ConsultasDB.getStatement();
			ResultSet resultado = busca.executeQuery("SELECT * FROM empresas WHERE cnpj = '" + reg	+ "'");
			resultado.next();
			
			cnpj = (resultado.getObject("cnpj") == null) ? "" : resultado
					.getObject("cnpj").toString();
			nome = (resultado.getObject("nome") == null) ? "" : resultado
					.getObject("nome").toString();
			nomeFantasia = (resultado.getObject("nome_fantasia") == null) ? "" : resultado
					.getObject("nome_fantasia").toString();
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
			uf = (resultado.getObject("uf") == null) ? "" : resultado
					.getObject("uf").toString();
			ramo = (resultado.getObject("ramo") == null) ? "" : resultado
					.getObject("ramo").toString();
			atividades = (resultado.getObject("atividades") == null) ? "" : resultado
					.getObject("atividades").toString();
			
			formattedTextFieldCNPJ.setText(cnpj);
			textFieldNome.setText(nome);
			textFieldFantasia.setText(nomeFantasia);
			textFieldEndereco.setText(endereco);
			textFieldBairro.setText(bairro);
			textFieldCidade.setText(cidade);
			formattedTextFieldCep.setText(cep);
			formattedTextFieldEmail.setText(email);
			textFieldRamo.setText(ramo);
			textFieldAtividades.setText(atividades);
			textFieldTelefone.setText(telefone);
			comboBoxUf.setSelectedItem(uf);
			
			sql = "SELECT id_supervisor,nome,cargo FROM supervisores WHERE cnpj = '"+ reg +"'";
			tableModel = ConsultasDB.busca(sql);
			table.setModel(tableModel);
			table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			
			formattedTextFieldCNPJ.setEditable(false);
			btnNovoEmpresa.setEnabled(true);
			btnRemoverEmpresa.setEnabled(true);
			btnSalvar.setEnabled(true);
			setEnablePaneSupervisores(true);
			
		} catch (SQLException e) {
		}
	}
	
	public void inserirEmpresa(){
		boolean result = false;
		captaDadosDosCampos();
		String valores = String.format("'%s','%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'", cnpj, nome, nomeFantasia, endereco, bairro, cidade, uf,cep,email, telefone,ramo,atividades);
		result = ConsultasDB.adicionarRegistros("empresas", "cnpj, nome, nome_fantasia, endereco, bairro, cidade, uf,cep,email, telefone,ramo,atividades", valores);
		if(result){
			atualizarCampos(cnpj);
		}		
	}
	
	public void atualizaEmpresa(){
		captaDadosDosCampos();
		
		String camposEValores = String
				.format("nome = '%s', endereco = '%s', bairro = '%s', cidade = '%s', email = '%s', cep = '%s', telefone = '%s', uf = '%s', nome_fantasia = '%s', ramo = '%s', atividades = '%s'",
						nome, endereco, bairro, cidade, email, cep, telefone, uf, nomeFantasia, ramo, atividades);
		ConsultasDB.atualizarRegistros("empresas", camposEValores, "cnpj", cnpj);
	}
	
	public void captaDadosDosCampos(){
		cnpj = formattedTextFieldCNPJ.getText().replace("_", "").replace(".", "").replace("-", "").replace("/", "");
		nome = textFieldNome.getText();
		nomeFantasia = textFieldFantasia.getText();
		endereco = textFieldEndereco.getText();
		bairro = textFieldBairro.getText();
		cidade = textFieldCidade.getText();
		email = formattedTextFieldEmail.getText();
		cep = formattedTextFieldCep.getText();
		telefone = textFieldTelefone.getText();
		uf = comboBoxUf.getItemAt(comboBoxUf.getSelectedIndex());
		ramo = textFieldRamo.getText();
		atividades = textFieldAtividades.getText();
	}
	
	public void limparCampos() {
		cnpj = null;

		formattedTextFieldCNPJ.setText("");
		textFieldNome.setText("");
		textFieldEndereco.setText("");
		textFieldBairro.setText("");
		textFieldCidade.setText("");
		formattedTextFieldEmail.setText("");
		formattedTextFieldCep.setText("");
		textFieldTelefone.setText("");
		comboBoxUf.setSelectedItem("CE");
		textFieldFantasia.setText("");
		textFieldRamo.setText("");
		textFieldAtividades.setText("");

		formattedTextFieldCNPJ.setEditable(true);
		btnNovoEmpresa.setEnabled(false);
		btnRemoverEmpresa.setEnabled(false);
		btnSalvar.setEnabled(false);
		setEnablePaneSupervisores(false);
	}
	
	public void setEnablePaneSupervisores(boolean v) {
		table.setEnabled(v);
		if (!v) {
			table.setModel(new DefaultTableModel(new Object[][] {},
					new String[] {}));
		}
		tfCargo.setEnabled(v);
		tfNome.setEnabled(v);
		btnAdicionarSupervisor.setEnabled(v);
		btnExcluirSupervisor.setEnabled(v);
	}
	
	public void liberaBotoes() {
		if (!formattedTextFieldCNPJ.getText().replace("_", "").replace(".", "").replace("-", "").replace("/", "").isEmpty()
				&& !textFieldNome.getText().isEmpty()) {
			btnSalvar.setEnabled(true);
		} else {
			btnSalvar.setEnabled(false);
		}
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
}