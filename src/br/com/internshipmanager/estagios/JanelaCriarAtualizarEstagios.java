package br.com.internshipmanager.estagios;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import br.com.internshipmanager.InternshipManager;
import br.com.internshipmanager.UpCaseField;
import br.com.internshipmanager.database.ConsultasDB;
import br.com.internshipmanager.database.ConversorDates;
import br.com.internshipmanager.database.ResultSetSQL;
import br.com.internshipmanager.orientadores.ListaDeOrientadores;

@SuppressWarnings("serial")
public class JanelaCriarAtualizarEstagios extends JInternalFrame {
	
	private ResultSetSQL tableModelAluno;
	private ResultSetSQL tableModelEmpresa;
	static final String sqlAluno = "SELECT nome,alunos.cpf,matricula,nascimento,endereco,bairro,cidade,email,telefone,celular FROM alunos,cursos_alunos WHERE (alunos.cpf = cursos_alunos.cpf)";
	static final String sqlEmpresa = "SELECT nome,nome_fantasia,cnpj,endereco,bairro,cidade,telefone,ramo FROM empresas";
	
	private JTextField tfFiltroAluno;
	private JTable tbAluno;
	private JTextField tfFiltroEmpresa;
	private JTable tbEmpresa;
	private JTextField tfAtividades;
	private JLabel lbAluno;
	private JLabel lbEmpresa;
	
	private String matricula;
	private String cnpj;
	private String nome;
	private String empresa;
	private String dataInicio;
	private String dataFim;
	private String horaInicio;
	private String horaFim;
	private String orientadorNome;
	private long orientadorId;
	private String atividades;
	private String dataCadastro;
	private String setor;
	private String horario;
	private long supervisorId;
	private boolean novoUsuario = true;
	private JComboBox<String> cbOrientador;
	private ArrayList<ListaDeOrientadores> listaOrientadores;
	private ArrayList<ListaDeSupervisores> listaSupervisores;
	private JFormattedTextField tfDataInicio;
	private JFormattedTextField tfDataFim;
	private JTextField tfHorario;
	private JTextField tfSetor;
	private JTextField tfDataCadastro;
	private JScrollPane spAluno;
	private JScrollPane spEmpresa;
	private JButton btSalvar;
	private JLabel lblSupervisor;
	private JComboBox<String> cbxSupervisor;
	private JFormattedTextField tfHoraInicio;
	private JFormattedTextField tfHoraFim;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCriarAtualizarEstagios frame = new JanelaCriarAtualizarEstagios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaCriarAtualizarEstagios() {
		setResizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setIconifiable(true);
		setTitle("Est\u00E1gios");
		setBounds(100, 100, 904, 479);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Aluno", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 888, 153);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Filtro:");
		lblNewLabel.setBounds(10, 21, 46, 14);
		panel.add(lblNewLabel);
		
		tfFiltroAluno = new JTextField();
		tfFiltroAluno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				atualizarTabelaAluno();
			}
		});
		tfFiltroAluno.setBounds(44, 18, 199, 20);
		panel.add(tfFiltroAluno);
		tfFiltroAluno.setColumns(10);
		
		spAluno = new JScrollPane();
		spAluno.setBounds(10, 46, 868, 78);
		panel.add(spAluno);
		
		tbAluno = new JTable();
		tbAluno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				matricula = tableModelAluno.getValueAt(tbAluno.getSelectedRow(), 2).toString();
				nome = tableModelAluno.getValueAt(tbAluno.getSelectedRow(), 0).toString();
				lbAluno.setText(nome + " ( " + matricula + " )");
				liberaBotoes();
			}
		});

		try {
            tableModelAluno = new ResultSetSQL(InternshipManager.DATABASE_URL,InternshipManager.USERNAME,InternshipManager.PASSWORD,sqlAluno);
            tbAluno.setModel(tableModelAluno);
            tbAluno.getColumnModel().getColumn(0).setPreferredWidth(250);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
		
		tbAluno.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		spAluno.setViewportView(tbAluno);
		
		JLabel lblAlunoSelecionado = new JLabel("Aluno Selecionado:");
		lblAlunoSelecionado.setBounds(10, 128, 101, 14);
		panel.add(lblAlunoSelecionado);
		
		lbAluno = new JLabel("");
		lbAluno.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbAluno.setBounds(106, 128, 432, 14);
		panel.add(lbAluno);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Empresa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 153, 888, 153);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Filtro:");
		label.setBounds(10, 24, 46, 14);
		panel_1.add(label);
		
		tfFiltroEmpresa = new JTextField();
		tfFiltroEmpresa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				atualizarTabelaEmpresa();
			}
		});
		tfFiltroEmpresa.setColumns(10);
		tfFiltroEmpresa.setBounds(44, 21, 201, 20);
		panel_1.add(tfFiltroEmpresa);
		
		spEmpresa = new JScrollPane();
		spEmpresa.setBounds(10, 49, 868, 77);
		panel_1.add(spEmpresa);
		
		tbEmpresa = new JTable();
		tbEmpresa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				cnpj = tableModelEmpresa.getValueAt(tbEmpresa.getSelectedRow(), 2).toString();
				empresa = tableModelEmpresa.getValueAt(tbEmpresa.getSelectedRow(), 0).toString();
				lbEmpresa.setText(empresa + " ( "+ cnpj + " )");
				liberaBotoes();
				preencheListaDeSupervisores(cnpj);
			}
		});

		try {
            tableModelEmpresa = new ResultSetSQL(InternshipManager.DATABASE_URL,InternshipManager.USERNAME,InternshipManager.PASSWORD,sqlEmpresa);
            tbEmpresa.setModel(tableModelEmpresa);
            tbEmpresa.getColumnModel().getColumn(0).setPreferredWidth(250);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
		
		tbEmpresa.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		spEmpresa.setViewportView(tbEmpresa);
		
		JLabel lblEmpresaSelecionada = new JLabel("Empresa Selecionada:");
		lblEmpresaSelecionada.setBounds(10, 131, 115, 14);
		panel_1.add(lblEmpresaSelecionada);
		
		lbEmpresa = new JLabel("");
		lbEmpresa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbEmpresa.setBounds(120, 131, 418, 14);
		panel_1.add(lbEmpresa);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(0, 309, 888, 100);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblDataDeInicio = new JLabel("Data de Inicio");
		lblDataDeInicio.setBounds(10, 21, 77, 14);
		panel_2.add(lblDataDeInicio);
		
		try {
			MaskFormatter maskDataIni = new MaskFormatter("##/##/####");
			maskDataIni.setPlaceholderCharacter('_');
			tfDataInicio = new JFormattedTextField(maskDataIni);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		tfDataInicio.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				liberaBotoes();
			}
		});
		tfDataInicio.setBounds(80, 18, 85, 20);
		panel_2.add(tfDataInicio);
		
		JLabel lblDataDeFim = new JLabel("Data de Fim");
		lblDataDeFim.setBounds(175, 21, 66, 14);
		panel_2.add(lblDataDeFim);
		
		try {
			MaskFormatter maskDatafim = new MaskFormatter("##/##/####");
			maskDatafim.setPlaceholderCharacter('_');
			tfDataFim = new JFormattedTextField(maskDatafim);
			tfDataFim.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent arg0) {
					liberaBotoes();
				}
			});
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		tfDataFim.setBounds(239, 18, 94, 20);
		panel_2.add(tfDataFim);
		
		JLabel lblDataDeCadastro = new JLabel("Data de Cadastro");
		lblDataDeCadastro.setBounds(343, 21, 91, 14);
		panel_2.add(lblDataDeCadastro);
		
		tfDataCadastro = new JTextField();
		tfDataCadastro.setEnabled(false);
		tfDataCadastro.setBounds(433, 18, 106, 20);
		panel_2.add(tfDataCadastro);
		tfDataCadastro.setColumns(10);
		
		JLabel lblOrientador = new JLabel("Orientador");
		lblOrientador.setBounds(10, 49, 60, 14);
		panel_2.add(lblOrientador);
		
		cbOrientador = new JComboBox<String>();
		cbOrientador.setBounds(68, 46, 143, 20);
		panel_2.add(cbOrientador);
		
		JLabel lblNewLabel_1 = new JLabel("Setor");
		lblNewLabel_1.setBounds(549, 21, 46, 14);
		panel_2.add(lblNewLabel_1);
		
		tfSetor = new JTextField();
		tfSetor.setDocument(new UpCaseField());
		tfSetor.setBounds(578, 18, 139, 20);
		panel_2.add(tfSetor);
		tfSetor.setColumns(10);
		
		JLabel lblHorrio = new JLabel("Horas p/dia");
		lblHorrio.setBounds(727, 21, 60, 14);
		panel_2.add(lblHorrio);
		
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
		tfHorario = new JTextField();
		tfHorario.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				liberaBotoes();
			}
		});
		tfHorario.setDocument(doc1);
		tfHorario.setBounds(793, 18, 85, 20);
		panel_2.add(tfHorario);
		tfHorario.setColumns(10);
		
		JLabel lblAtividades = new JLabel("Atividades");
		lblAtividades.setBounds(10, 74, 66, 14);
		panel_2.add(lblAtividades);
		
		tfAtividades = new JTextField();
		tfAtividades.setDocument(new UpCaseField());
		tfAtividades.setBounds(68, 71, 810, 20);
		panel_2.add(tfAtividades);
		tfAtividades.setColumns(10);
		
		lblSupervisor = new JLabel("Supervisor");
		lblSupervisor.setBounds(228, 49, 66, 14);
		panel_2.add(lblSupervisor);
		
		cbxSupervisor = new JComboBox<String>();
		cbxSupervisor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				liberaBotoes();
			}
		});
		cbxSupervisor.setBounds(288, 46, 175, 20);
		panel_2.add(cbxSupervisor);
		
		JLabel lblHorrioInicial = new JLabel("Hor\u00E1rio Inicial");
		lblHorrioInicial.setBounds(473, 49, 67, 14);
		panel_2.add(lblHorrioInicial);
		
		JLabel lblHorrioFinal = new JLabel("Hor\u00E1rio Final");
		lblHorrioFinal.setBounds(681, 49, 66, 14);
		panel_2.add(lblHorrioFinal);
		
		try {
			MaskFormatter maskHoraIni = new MaskFormatter("##:##:##");
			maskHoraIni.setPlaceholderCharacter('_');
			tfHoraInicio = new JFormattedTextField(maskHoraIni);
			tfHoraInicio.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent e) {
					liberaBotoes();
				}
			});
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		tfHoraInicio.setBounds(549, 46, 112, 20);
		panel_2.add(tfHoraInicio);
		tfHoraInicio.setColumns(10);
		
		try {
			MaskFormatter maskhorafim = new MaskFormatter("##:##:##");
			maskhorafim.setPlaceholderCharacter('_');
			tfHoraFim = new JFormattedTextField(maskhorafim);
			tfHoraFim.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent e) {
					liberaBotoes();
				}
			});
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		tfHoraFim.setBounds(747, 46, 131, 20);
		panel_2.add(tfHoraFim);
		tfHoraFim.setColumns(10);
		
		btSalvar = new JButton("Salvar Informa\u00E7\u00F5es");
		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(novoUsuario){
					inserirEstagiario();
				}
				else {
					alterarEstagiario();
				}
			}
		});
		btSalvar.setBounds(747, 413, 131, 34);
		getContentPane().add(btSalvar);

	}
	
	public void atualizarTabelaAluno(){
        String sql1;
        sql1 = sqlAluno;
         
        try {
        	
            if(!tfFiltroAluno.getText().isEmpty()){
                String filtro = tfFiltroAluno.getText();
                String filtroUp = filtro.toUpperCase();
                sql1 += String.format(" AND (nome LIKE '%%%s%%' OR nome LIKE '%%%s%%' OR to_char(nascimento, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR to_char(nascimento, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR "
                		+ "alunos.cpf LIKE '%%%s%%' OR alunos.cpf LIKE '%%%s%%' OR matricula LIKE '%%%s%%' OR matricula LIKE '%%%s%%' OR endereco LIKE '%%%s%%' OR endereco LIKE '%%%s%%' OR "
                		+ "bairro LIKE '%%%s%%' OR bairro LIKE '%%%s%%' OR cidade LIKE '%%%s%%' OR cidade LIKE '%%%s%%' OR email LIKE '%%%s%%' OR email LIKE '%%%s%%' OR "
                		+ "telefone LIKE '%%%s%%' OR telefone LIKE '%%%s%%' OR celular LIKE '%%%s%%' OR celular LIKE '%%%s%%')",
                		filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp);
            }        	
			tableModelAluno.setQuery(sql1);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        tbAluno.getColumnModel().getColumn(0).setPreferredWidth(250);
	}
	
	public void atualizarTabelaEmpresa(){
        String sql1;
        sql1 = sqlEmpresa;
         
        if(!tfFiltroEmpresa.getText().isEmpty()){
            String filtro = tfFiltroEmpresa.getText();
            String filtroUp = filtro.toUpperCase();
            sql1 += String.format(" WHERE nome LIKE '%%%s%%' OR nome LIKE '%%%s%%' OR nome_fantasia LIKE '%%%s%%' OR nome_fantasia LIKE '%%%s%%' OR "
            		+ "cnpj LIKE '%%%s%%' OR cnpj LIKE '%%%s%%' OR ramo LIKE '%%%s%%' OR ramo LIKE '%%%s%%' OR endereco LIKE '%%%s%%' OR endereco LIKE '%%%s%%' OR "
            		+ "bairro LIKE '%%%s%%' OR bairro LIKE '%%%s%%' OR cidade LIKE '%%%s%%' OR cidade LIKE '%%%s%%' OR "
            		+ "telefone LIKE '%%%s%%' OR telefone LIKE '%%%s%%'",
            		filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp);
        }
        try {
			tableModelEmpresa.setQuery(sql1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        tbEmpresa.getColumnModel().getColumn(0).setPreferredWidth(250);
	}
	
	public void preencheListaDeOrientadores() {
		listaOrientadores = new ArrayList<ListaDeOrientadores>();

		try {
			Statement busca = ConsultasDB.getStatement();
			ResultSet resultado = busca.executeQuery("SELECT * FROM orientadores");
			int cont = 0;
			while (resultado.next()) {
				listaOrientadores.add(cont, new ListaDeOrientadores(resultado.getLong(1),
						resultado.getString(2)));
				cont++;
			}

			cbOrientador.removeAllItems();
			for (int i = 0; i < cont; i++) {
				cbOrientador.addItem(listaOrientadores.get(i).getNome());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void preencheListaDeSupervisores(String cnpj) {
		listaSupervisores = new ArrayList<ListaDeSupervisores>();

		try {

			Statement busca = ConsultasDB.getStatement();
			ResultSet resultado = busca.executeQuery("SELECT id_supervisor,nome,cargo FROM supervisores WHERE cnpj = '"+ cnpj +"'");
			int cont = 0;
			while (resultado.next()) {
				listaSupervisores.add(cont, new ListaDeSupervisores(
						resultado.getLong(1),
						resultado.getString(2),
						resultado.getString(3)));
				cont++;
			}

			cbxSupervisor.removeAllItems();
			int selecionado = -1;
			for (int i = 0; i < cont; i++) {
				cbxSupervisor.addItem(listaSupervisores.get(i).getNome());
				if(listaSupervisores.get(i).getId() == supervisorId) selecionado = i;
				else selecionado = 0;
			}
			cbxSupervisor.setSelectedIndex(selecionado);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void atualizarCampos(Estagiario estagiario){
		novoUsuario = false;
		matricula = estagiario.getMatricula();
		cnpj = estagiario.getCnpj();
		nome = estagiario.getNome();
		empresa = estagiario.getEmpresa();
		
		Statement busca = ConsultasDB.getStatement();
		try {
			ResultSet resultado = busca.executeQuery("SELECT empresas_alunos.*,orientadores.nome AS nome_orientador FROM empresas_alunos,orientadores WHERE empresas_alunos.id_orientador = orientadores.id_orientador AND "
					+ "empresas_alunos.matricula = '"+ matricula +"' AND empresas_alunos.cnpj = '"+ cnpj +"'");
			resultado.next();
			
			dataInicio = (resultado.getObject("data_inicio") == null) ? "" : ConversorDates.DateBaseToDateCommon(resultado.getObject("data_inicio").toString());
			dataFim = (resultado.getObject("data_fim") == null) ? "" : ConversorDates.DateBaseToDateCommon(resultado.getObject("data_fim").toString());
			orientadorNome = (resultado.getObject("nome_orientador") == null) ? "" : resultado.getObject("nome_orientador").toString();
			atividades = (resultado.getObject("atividades") == null) ? "" : resultado.getObject("atividades").toString();
			dataCadastro = (resultado.getObject("data_cadastro") == null) ? "" : ConversorDates.DateBaseToDateCommon(resultado.getObject("data_cadastro").toString());
			setor = (resultado.getObject("setor_estagio") == null) ? "" : resultado.getObject("setor_estagio").toString();
			horario = (resultado.getObject("horario") == null) ? "" : resultado.getObject("horario").toString();
			
			horaInicio = (resultado.getObject("horario_inicio") == null) ? "" : resultado.getObject("horario_inicio").toString();
			horaFim = (resultado.getObject("horario_fim") == null) ? "" : resultado.getObject("horario_fim").toString();
			
			supervisorId = ((resultado.getObject("supervisor") == null) ? -1 : (long) resultado.getObject("supervisor"));
			
			tbAluno.setVisible(false);
			tbEmpresa.setVisible(false);
			lbAluno.setText(nome + " ( " + matricula + " )");
			lbEmpresa.setText(empresa + " ( " + cnpj + " ) ");
			tfDataInicio.setText(dataInicio);
			tfDataFim.setText(dataFim);
			tfHoraInicio.setText(horaInicio);
			tfHoraFim.setText(horaFim);
			tfAtividades.setText(atividades);
			tfDataCadastro.setText(dataCadastro);
			tfSetor.setText(setor);
			tfHorario.setText(horario);
			preencheListaDeOrientadores();
			preencheListaDeSupervisores(cnpj);
			cbOrientador.setSelectedItem(orientadorNome);
			
		} catch (Exception e) {
		}
	}
	
	public void limparCampos(){
		novoUsuario = true;
		cnpj = null;
		matricula = null;
		supervisorId = -1;
		tbAluno.setVisible(true);
		tbEmpresa.setVisible(true);
		lbAluno.setText("");
		lbEmpresa.setText("");
		tfDataInicio.setText("");
		tfDataFim.setText("");
		tfAtividades.setText("");
		tfDataCadastro.setText(ConversorDates.DateToString(new Date(), "dd/MM/yyyy"));
		tfSetor.setText("");
		tfHorario.setText("");
		preencheListaDeOrientadores();
		cbOrientador.setSelectedIndex(0);
		btSalvar.setEnabled(false);
		atualizarTabelaAluno();
		atualizarTabelaEmpresa();
	}

	
	public void liberaBotoes() {
		if (
				!lbAluno.getText().isEmpty() &&
				!lbEmpresa.getText().isEmpty() &&
				!tfDataInicio.getText().replace("_", "").replace("/", "").isEmpty() &&
				!tfDataFim.getText().replace("_", "").replace("/", "").isEmpty() &&
				!tfHoraFim.getText().replace("_", "").replace(":", "").isEmpty() &&
				!tfHoraInicio.getText().replace("_", "").replace(":", "").isEmpty() &&
				!tfHorario.getText().isEmpty() &&
				(cbxSupervisor.getSelectedIndex() != -1)
			) {
			btSalvar.setEnabled(true);
		} else {
			btSalvar.setEnabled(false);
		}
	}

	public void inserirEstagiario(){
		boolean result = false;
		captaDadosDosCampos();
		
		String superv;
		if(supervisorId == -1)
			superv = "NULL";
		else
			superv = "" + supervisorId;
		String valores = String.format("'%s','%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s',%s,'%s','%s'", cnpj,matricula,dataInicio,dataFim,orientadorId,atividades,dataCadastro,setor,horario,superv,horaInicio,horaFim);
		result = ConsultasDB.adicionarRegistros("empresas_alunos", "cnpj,matricula,data_inicio,data_fim,id_orientador,atividades,data_cadastro,setor_estagio,horario,supervisor,horario_inicio,horario_fim", valores);
		if(result){
			atualizarCampos(new Estagiario(matricula, cnpj, nome, empresa));
		}
	}
	
	public void alterarEstagiario(){
		captaDadosDosCampos();
		String camposEValores = String
				.format("data_inicio =  '%s', data_fim = '%s', id_orientador = '%s', atividades = '%s', data_cadastro = '%s', setor_estagio = '%s', horario = '%s', horario_inicio = '%s', horario_fim = '%s'",
						dataInicio, dataFim,orientadorId,atividades,dataCadastro,setor,horario, horaInicio, horaFim);
		ConsultasDB.atualizarRegistros("empresas_alunos", camposEValores, "matricula", matricula, "cnpj", cnpj);
	}

	public void captaDadosDosCampos(){
		dataInicio = ConversorDates.DateCommonToDateBase(tfDataInicio.getText());
		dataFim = ConversorDates.DateCommonToDateBase(tfDataFim.getText());
		dataCadastro = ConversorDates.DateCommonToDateBase(tfDataCadastro.getText());
		horaInicio = tfHoraInicio.getText();
		horaFim = tfHoraFim.getText();
		//orientadorNome = cbOrientador.getItemAt(cbOrientador.getSelectedIndex());
		orientadorId = listaOrientadores.get(cbOrientador.getSelectedIndex()).getId();
		setor = tfSetor.getText();
		horario = tfHorario.getText();
		atividades = tfAtividades.getText();
		if(cbxSupervisor.getSelectedIndex() != -1)
			supervisorId = listaSupervisores.get(cbxSupervisor.getSelectedIndex()).getId();
		else
			supervisorId = -1;
	}
}
