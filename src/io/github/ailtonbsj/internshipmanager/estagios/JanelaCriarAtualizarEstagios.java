package io.github.ailtonbsj.internshipmanager.estagios;

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

import io.github.ailtonbsj.internshipmanager.UpCaseField;
import io.github.ailtonbsj.internshipmanager.database.ConsultasDB;
import io.github.ailtonbsj.internshipmanager.database.ConversorDates;
import io.github.ailtonbsj.internshipmanager.database.ResultSetSQL;
import io.github.ailtonbsj.internshipmanager.orientadores.ListaDeOrientadores;

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
		setBounds(100, 100, 994, 479);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Aluno", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(3, 0, 980, 153);
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
		tfFiltroAluno.setBounds(59, 19, 324, 20);
		panel.add(tfFiltroAluno);
		tfFiltroAluno.setColumns(10);
		
		spAluno = new JScrollPane();
		spAluno.setBounds(10, 46, 958, 78);
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
            tableModelAluno = ConsultasDB.busca(sqlAluno);
            tbAluno.setModel(tableModelAluno);
            tbAluno.getColumnModel().getColumn(0).setPreferredWidth(250);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
		
		tbAluno.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		spAluno.setViewportView(tbAluno);
		
		JLabel lblAlunoSelecionado = new JLabel("Aluno Selecionado:");
		lblAlunoSelecionado.setBounds(10, 128, 145, 14);
		panel.add(lblAlunoSelecionado);
		
		lbAluno = new JLabel("");
		lbAluno.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbAluno.setBounds(153, 129, 815, 14);
		panel.add(lbAluno);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Empresa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(3, 153, 980, 153);
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
		tfFiltroEmpresa.setBounds(59, 22, 324, 20);
		panel_1.add(tfFiltroEmpresa);
		
		spEmpresa = new JScrollPane();
		spEmpresa.setBounds(10, 49, 958, 77);
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
            tableModelEmpresa = ConsultasDB.busca(sqlEmpresa);
            tbEmpresa.setModel(tableModelEmpresa);
            tbEmpresa.getColumnModel().getColumn(0).setPreferredWidth(250);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
		
		tbEmpresa.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		spEmpresa.setViewportView(tbEmpresa);
		
		JLabel lblEmpresaSelecionada = new JLabel("Empresa Selecionada:");
		lblEmpresaSelecionada.setBounds(10, 131, 157, 14);
		panel_1.add(lblEmpresaSelecionada);
		
		lbEmpresa = new JLabel("");
		lbEmpresa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbEmpresa.setBounds(174, 132, 794, 14);
		panel_1.add(lbEmpresa);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(3, 309, 980, 100);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblDataDeInicio = new JLabel("Data de Inicio");
		lblDataDeInicio.setBounds(10, 21, 106, 14);
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
		tfDataInicio.setBounds(115, 19, 85, 20);
		panel_2.add(tfDataInicio);
		
		JLabel lblDataDeFim = new JLabel("Data de Fim");
		lblDataDeFim.setBounds(207, 21, 85, 14);
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
		
		tfDataFim.setBounds(299, 19, 94, 20);
		panel_2.add(tfDataFim);
		
		JLabel lblDataDeCadastro = new JLabel("Data de Cadastro");
		lblDataDeCadastro.setBounds(411, 21, 131, 14);
		panel_2.add(lblDataDeCadastro);
		
		tfDataCadastro = new JTextField();
		tfDataCadastro.setEnabled(false);
		tfDataCadastro.setBounds(543, 19, 94, 20);
		panel_2.add(tfDataCadastro);
		tfDataCadastro.setColumns(10);
		
		JLabel lblOrientador = new JLabel("Orientador");
		lblOrientador.setBounds(10, 49, 85, 14);
		panel_2.add(lblOrientador);
		
		cbOrientador = new JComboBox<String>();
		cbOrientador.setBounds(100, 46, 143, 20);
		panel_2.add(cbOrientador);
		
		JLabel lblNewLabel_1 = new JLabel("Setor");
		lblNewLabel_1.setBounds(655, 21, 46, 14);
		panel_2.add(lblNewLabel_1);
		
		tfSetor = new JTextField();
		tfSetor.setDocument(new UpCaseField());
		tfSetor.setBounds(701, 21, 267, 20);
		panel_2.add(tfSetor);
		tfSetor.setColumns(10);
		
		JLabel lblHorrio = new JLabel("Horas p/dia");
		lblHorrio.setBounds(816, 74, 85, 14);
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
		tfHorario.setBounds(907, 72, 61, 20);
		panel_2.add(tfHorario);
		tfHorario.setColumns(10);
		
		JLabel lblAtividades = new JLabel("Atividades");
		lblAtividades.setBounds(10, 74, 74, 14);
		panel_2.add(lblAtividades);
		
		tfAtividades = new JTextField();
		tfAtividades.setDocument(new UpCaseField());
		tfAtividades.setBounds(100, 74, 710, 20);
		panel_2.add(tfAtividades);
		tfAtividades.setColumns(10);
		
		lblSupervisor = new JLabel("Supervisor");
		lblSupervisor.setBounds(249, 49, 84, 14);
		panel_2.add(lblSupervisor);
		
		cbxSupervisor = new JComboBox<String>();
		cbxSupervisor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				liberaBotoes();
			}
		});
		cbxSupervisor.setBounds(330, 46, 175, 20);
		panel_2.add(cbxSupervisor);
		
		JLabel lblHorrioInicial = new JLabel("Hor\u00E1rio Inicial");
		lblHorrioInicial.setBounds(512, 49, 106, 14);
		panel_2.add(lblHorrioInicial);
		
		JLabel lblHorrioFinal = new JLabel("Hor\u00E1rio Final");
		lblHorrioFinal.setBounds(737, 49, 94, 14);
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
		
		tfHoraInicio.setBounds(618, 47, 112, 20);
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
		
		tfHoraFim.setBounds(837, 49, 131, 20);
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
		btSalvar.setBounds(780, 410, 192, 34);
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
