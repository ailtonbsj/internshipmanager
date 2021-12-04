package io.github.ailtonbsj.internshipmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import io.github.ailtonbsj.internshipmanager.alunos.JanelaCriarAtualizarAlunos;
import io.github.ailtonbsj.internshipmanager.alunos.JanelaListarAlunos;
import io.github.ailtonbsj.internshipmanager.cursos.JanelaListaCursos;
import io.github.ailtonbsj.internshipmanager.database.ConsultasDB;
import io.github.ailtonbsj.internshipmanager.database.ConversorDates;
import io.github.ailtonbsj.internshipmanager.empresas.JanelaCriarAtualizarEmpresas;
import io.github.ailtonbsj.internshipmanager.empresas.JanelaListarEmpresas;
import io.github.ailtonbsj.internshipmanager.estagios.Estagiario;
import io.github.ailtonbsj.internshipmanager.estagios.JanelaCriarAtualizarEstagios;
import io.github.ailtonbsj.internshipmanager.estagios.JanelaListarEstagios;
import io.github.ailtonbsj.internshipmanager.modelos.JanelaGerarDocumentos;
import io.github.ailtonbsj.internshipmanager.orientadores.JanelaListaOrientador;

@SuppressWarnings("serial")
public class InternshipManager extends JFrame {

	private JPanel contentPane;
	private JanelaListarAlunos janelaListarAlunos;
	private JanelaListarEmpresas janelaListarEmpresas;
	private JanelaListarEstagios janelaListarEstagios;
	private JanelaCriarAtualizarAlunos janelaCriarAtualizarAlunos;
	private JanelaCriarAtualizarEmpresas janelaCriarAtualizarEmpresas;
	private JanelaCriarAtualizarEstagios janelaCriarAtualizarEstagios;
	private JanelaListaCursos janelaListaCursos;
	private JanelaListaOrientador janelaListaOrientador;
	private JanelaGerarDocumentos janelaGerarDocumentos;
	JButton botaoAtualizarAlunos, botaoExcluirAlunos, botaoAdicionarAlunos, botaoAtualizarEmpresas, botaoExcluirEmpresas, botaoAdicionarEmpresas,
	btAddEstagio, btRefEstagio, btDelEstagio, btGeraDoc;
	
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/internshipdb";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "123";
    private JMenu mnAluno;
    private JMenu mnEmpresa;
    private JMenu mnEstagiandos;
    private JMenu mnOrientadores;
    private JMenu mnCursos;
    private JMenuItem mntmAdicionarAluno;
    private JMenuItem mntmAdicionarEmpresa;

	public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InternshipManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InternshipManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InternshipManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InternshipManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("resource")
			public void run() {

					
					File arqConf = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
					arqConf = new File(arqConf.getParent(),"conf.ini");
					try {
						new BufferedReader(new FileReader(arqConf));
					} catch (FileNotFoundException e) {
						try {
							PostgreeBkpAndRestore.criaBanco();
							ConsultasDB.criaTabelas();
							//IOException | InterruptedException
						} catch (Exception e1) {
							System.out.println(e1.getMessage());
						}
					}

					InternshipManager frame = new InternshipManager();
					frame.recebeJanelaPrincipal(frame);
					frame.setVisible(true);
					frame.setExtendedState(MAXIMIZED_BOTH);
			}
		});
	}
	
	public InternshipManager() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				File arqConf = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				arqConf = new File(arqConf.getParent(),"conf.ini");
				BufferedReader bfr;
				try {
					bfr = new BufferedReader(new FileReader(arqConf));
					Date ultimoBkp = ConversorDates.StringToDate(bfr.readLine(), "dd/MM/yyyy");
					Date dataAgora = new Date();
					Calendar ultimoBkpCld = Calendar.getInstance();
					Calendar dataAgoraCld = Calendar.getInstance();
					ultimoBkpCld.setTime(ultimoBkp);
					dataAgoraCld.setTime(dataAgora);
					long ult = (((ultimoBkpCld.getTimeInMillis()/1000)/60)/60)/24;
					long now = (((dataAgoraCld.getTimeInMillis()/1000)/60)/60)/24;
					if((now-ult) > 5) PostgreeBkpAndRestore.realizaBackup();
					else System.exit(0);
				} catch (FileNotFoundException e) {
					PostgreeBkpAndRestore.salvarArquivoConf();
				} catch (ParseException e) {
				} catch (IOException e) {
				} catch (InterruptedException e) {
				}
			}
		});
		setTitle("Internship Manager 1.0");
		setBounds(100, 100, 826, 466);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnAluno = new JMenu("Alunos");
		mnAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListarAlunos.atualizarTabela();
				janelaListarAlunos.setVisible(true);
			}
		});
		menuBar.add(mnAluno);
		
		JMenuItem mntmListarAlunos = new JMenuItem("Listar Alunos");
		mnAluno.add(mntmListarAlunos);
		
		mntmAdicionarAluno = new JMenuItem("Adicionar Aluno");
		mntmAdicionarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdicionarAluno();
			}
		});
		mnAluno.add(mntmAdicionarAluno);
		mntmListarAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListarAlunos.atualizarTabela();
				janelaListarAlunos.setVisible(true);
			}
		});
		
		janelaListarAlunos = new JanelaListarAlunos();
		janelaListarEmpresas = new JanelaListarEmpresas();
		janelaListarEstagios = new JanelaListarEstagios();
		janelaCriarAtualizarAlunos = new JanelaCriarAtualizarAlunos();
		janelaCriarAtualizarEmpresas = new JanelaCriarAtualizarEmpresas();
		janelaCriarAtualizarEstagios = new JanelaCriarAtualizarEstagios();
		janelaGerarDocumentos = new JanelaGerarDocumentos();
		janelaListaCursos = new JanelaListaCursos();
		janelaListaOrientador = new JanelaListaOrientador();
		botaoAtualizarAlunos = new JButton("Atualizar Alunos");
		botaoExcluirAlunos = new JButton("Excluir Aluno");
		botaoAdicionarAlunos = new JButton("Adicionar Aluno");
		botaoAtualizarEmpresas = new JButton("Atualizar Empresas");
		botaoExcluirEmpresas = new JButton("Excluir Empresas");
		botaoAdicionarEmpresas = new JButton("Adicionar Empresas");
		btAddEstagio = new JButton("Novo Est�gio");
		btRefEstagio = new JButton("Atualizar Est�gio");
		btDelEstagio = new JButton("Remover Est�gio");
		btGeraDoc = new JButton("Gerar Documentos");
		
		botaoAtualizarAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selecao = janelaListarAlunos.linhaSelecionada();
				if(!selecao.equals("")){
					janelaCriarAtualizarAlunos.setVisible(true);
					janelaCriarAtualizarAlunos.atualizarCampos(selecao);
					janelaListarAlunos.setVisible(false);
				}
			}
		});
		
		botaoExcluirAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selecao = janelaListarAlunos.linhaSelecionada();
				if(!selecao.equals("")){
					ConsultasDB.excluirAlunos(selecao);
					janelaListarAlunos.atualizarTabela();
				}
			}
		});
		
		botaoAdicionarAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdicionarAluno();
			}
		});
		
		botaoAtualizarEmpresas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selecao = janelaListarEmpresas.linhaSelecionada();
				if(!selecao.equals("")){
					janelaCriarAtualizarEmpresas.setVisible(true);
					janelaCriarAtualizarEmpresas.atualizarCampos(selecao);
					janelaListarEmpresas.setVisible(false);
				}
			}
		});

		botaoExcluirEmpresas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selecao = janelaListarEmpresas.linhaSelecionada();
				if(!selecao.equals("")){
					ConsultasDB.excluirEmpresas(selecao);
					janelaListarEmpresas.atualizarTabela();
				}
			}
		});
		
		botaoAdicionarEmpresas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdicionarEmpresa();
			}
		});
		
		btAddEstagio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdicionarEstagiario();
			}
		});
		
		btRefEstagio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Estagiario selecao = janelaListarEstagios.linhaSelecionada();
				if(selecao != null){
					janelaCriarAtualizarEstagios.setVisible(true);
					janelaCriarAtualizarEstagios.atualizarCampos(selecao);
					janelaListarEstagios.setVisible(false);
				}
			}
		});
		
		btDelEstagio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Estagiario selecao = janelaListarEstagios.linhaSelecionada();
				if(selecao != null){
					ConsultasDB.excluirEstagios(selecao.getMatricula(),selecao.getCnpj());
					janelaListarEstagios.atualizarTabela();
				}
			}
		});
		
		btGeraDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Estagiario selecao = janelaListarEstagios.linhaSelecionada();
				if(selecao != null){
					janelaGerarDocumentos.atualizaDadosEstagio(selecao);
					janelaGerarDocumentos.setVisible(true);	
				}
			}
		});
		
		mnEmpresa = new JMenu("Empresas");
		mnEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListarEmpresas.atualizarTabela();
				janelaListarEmpresas.setVisible(true);
			}
		});
		menuBar.add(mnEmpresa);
		
		JMenuItem mntmListarEmpresas = new JMenuItem("Listar Empresas");
		mntmListarEmpresas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListarEmpresas.atualizarTabela();
				janelaListarEmpresas.setVisible(true);
			}
		});
		mnEmpresa.add(mntmListarEmpresas);
		
		mntmAdicionarEmpresa = new JMenuItem("Adicionar Empresa");
		mntmAdicionarEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdicionarEmpresa();
			}
		});
		mnEmpresa.add(mntmAdicionarEmpresa);
		
		mnEstagiandos = new JMenu("Estagi\u00E1rios");
		mnEstagiandos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListarEstagios.atualizarTabela();
				janelaListarEstagios.setVisible(true);
			}
		});
		menuBar.add(mnEstagiandos);
		
		JMenuItem mntmListarEstagirios = new JMenuItem("Listar Estagi\u00E1rios");
		mntmListarEstagirios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListarEstagios.atualizarTabela();
				janelaListarEstagios.setVisible(true);
			}
		});
		mnEstagiandos.add(mntmListarEstagirios);
		
		JMenuItem mntmAdicionarEstagirio = new JMenuItem("Adicionar Estagi\u00E1rio");
		mntmAdicionarEstagirio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdicionarEstagiario();
			}
		});
		mnEstagiandos.add(mntmAdicionarEstagirio);
		
		mnOrientadores = new JMenu("Orientadores");
		mnOrientadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListaOrientador.setVisible(true);
			}
		});
		menuBar.add(mnOrientadores);
		
		JMenuItem mntmListarOrientadores = new JMenuItem("Listar Orientadores");
		mntmListarOrientadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListaOrientador.setVisible(true);
			}
		});
		mnOrientadores.add(mntmListarOrientadores);
		
		JMenuItem mntmAdicionarOrientador = new JMenuItem("Adicionar Orientador");
		mntmAdicionarOrientador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				janelaListaOrientador.adicionarOrientador();
			}
		});
		mnOrientadores.add(mntmAdicionarOrientador);
		
		mnCursos = new JMenu("Cursos");
		mnCursos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaListaCursos.setVisible(true);
			}
		});
		menuBar.add(mnCursos);
		
		JMenuItem mntmListaDeCursos = new JMenuItem("Lista de Cursos");
		mntmListaDeCursos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				janelaListaCursos.setVisible(true);
			}
		});
		mnCursos.add(mntmListaDeCursos);
		
		JMenuItem mntmAdicionarCurso = new JMenuItem("Adicionar Curso");
		mntmAdicionarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				janelaListaCursos.adicionarCurso();
			}
		});
		mnCursos.add(mntmAdicionarCurso);
		
		JMenu mnAjuda = new JMenu("Configura\u00E7\u00F5es");
		menuBar.add(mnAjuda);
		
		JMenuItem mntmOpesDeBackup = new JMenuItem("Restaurar Dados");
		mntmOpesDeBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File pasta = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				pasta = new File(pasta.getParent(),"backups");
				JFileChooser seletor = new JFileChooser(pasta);
				if(seletor.showOpenDialog(null) == 0){
					try {
						PostgreeBkpAndRestore.criaBanco();
						PostgreeBkpAndRestore.realizaRestore(seletor.getSelectedFile());
						if(JOptionPane.showConfirmDialog(null,"Backup realizado com sucesso!","Alerta",JOptionPane.OK_CANCEL_OPTION) == 0 || true){
							System.exit(0);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
					}
				}
			}
		});
		mnAjuda.add(mntmOpesDeBackup);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Software desenvolvido por:\n\nJos� Ailton B. da Silva\nEmail:ailton.ifce@gmail.com\n\nHermesson Douglas Mota\nEmail: hermesson@gmail.com\n\n");
			}
		});
		mnAjuda.add(mntmSobre);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JBackground desktopPane = new JBackground();
		desktopPane.setBackground(Color.WHITE);
		desktopPane.add(janelaListarAlunos);
		desktopPane.add(janelaListarEmpresas);
		desktopPane.add(janelaCriarAtualizarAlunos);
		desktopPane.add(janelaCriarAtualizarEmpresas);
		desktopPane.add(janelaListaCursos);
		desktopPane.add(janelaListaOrientador);
		desktopPane.add(janelaListarEstagios);
		desktopPane.add(janelaCriarAtualizarEstagios);
		desktopPane.add(janelaGerarDocumentos);
		JPanel painelDeBotoes = new JPanel(new GridLayout(1,3));
		janelaListarAlunos.getContentPane().add(painelDeBotoes, BorderLayout.SOUTH);
		painelDeBotoes.add(botaoAdicionarAlunos);
		painelDeBotoes.add(botaoAtualizarAlunos);
		painelDeBotoes.add(botaoExcluirAlunos);
		
		JPanel painelDeBotoesEmpresas = new JPanel(new GridLayout(1,3));
		janelaListarEmpresas.getContentPane().add(painelDeBotoesEmpresas, BorderLayout.SOUTH);
		painelDeBotoesEmpresas.add(botaoAdicionarEmpresas);
		painelDeBotoesEmpresas.add(botaoAtualizarEmpresas);
		painelDeBotoesEmpresas.add(botaoExcluirEmpresas);
		
		JPanel painelDeBotoesEstagio = new JPanel(new GridLayout(1,4));
		janelaListarEstagios.getContentPane().add(painelDeBotoesEstagio, BorderLayout.SOUTH);
		painelDeBotoesEstagio.add(btAddEstagio);
		painelDeBotoesEstagio.add(btRefEstagio);
		painelDeBotoesEstagio.add(btDelEstagio);
		painelDeBotoesEstagio.add(btGeraDoc);
		
		contentPane.add(desktopPane, BorderLayout.CENTER);
		bloquearMenusPrincipais();
	}

	public void bloquearMenusPrincipais(){
		String cursos = ConsultasDB.contarRegistros("cursos");
		String orientadores = ConsultasDB.contarRegistros("orientadores");
		if(cursos.equals("0") || orientadores.equals("0")){
			mnAluno.setEnabled(false);
			mnEmpresa.setEnabled(false);
			mnEstagiandos.setEnabled(false);
		}
		else {
			mnAluno.setEnabled(true);
			mnEmpresa.setEnabled(true);
			mnEstagiandos.setEnabled(true);
		}
	}
	
	public void recebeJanelaPrincipal(InternshipManager a){
		janelaListaCursos.recebeJanelaPrincipal(a);
		janelaListaOrientador.recebeJanelaPrincipal(a);
	}

	public void AdicionarAluno(){
		janelaCriarAtualizarAlunos.limparCampos();
		janelaCriarAtualizarAlunos.setVisible(true);
		janelaListarAlunos.setVisible(false);
	}
	public void AdicionarEmpresa(){
		janelaCriarAtualizarEmpresas.limparCampos();
		janelaCriarAtualizarEmpresas.setVisible(true);
		janelaListarEmpresas.setVisible(false);
	}
	public void AdicionarEstagiario(){
		janelaCriarAtualizarEstagios.limparCampos();
		janelaCriarAtualizarEstagios.setVisible(true);
		janelaListarEstagios.setVisible(false);
	}
}