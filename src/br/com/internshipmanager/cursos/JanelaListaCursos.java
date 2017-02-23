package br.com.internshipmanager.cursos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.internshipmanager.InternshipManager;
import br.com.internshipmanager.database.ConsultasDB;
import br.com.internshipmanager.database.GeradorDeIds;
import br.com.internshipmanager.database.ResultSetSQL;

@SuppressWarnings("serial")
public class JanelaListaCursos extends JInternalFrame {
	
	private JTable table;
	private ResultSetSQL tableModel;
	static final String sql = "SELECT * FROM cursos";
	private InternshipManager janelaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaListaCursos frame = new JanelaListaCursos();
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
	public JanelaListaCursos() {
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setIconifiable(true);
		setTitle("Cursos");
		setBounds(100, 100, 484, 214);
		
		try {
            tableModel = new ResultSetSQL(InternshipManager.DATABASE_URL,InternshipManager.USERNAME,InternshipManager.PASSWORD,sql);
            
            JScrollPane scrollPane = new JScrollPane();
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            
            table = new JTable();
            scrollPane.setViewportView(table);
            table.setModel(tableModel);
            table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            
            JPanel panel = new JPanel();
            getContentPane().add(panel, BorderLayout.SOUTH);
            panel.setLayout(new GridLayout(0, 3, 0, 0));
            
            JButton btnAdicionarCurso = new JButton("Adicionar Curso");
            btnAdicionarCurso.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {            		
            		adicionarCurso();
            	}
            });
            panel.add(btnAdicionarCurso);
            
            JButton btnAtualizarCurso = new JButton("Atualizar Curso");
            btnAtualizarCurso.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		String valor = linhaSelecionada();
            		if(!valor.equals("")){
            			try {
            				String result = JOptionPane.showInputDialog("Alterar nome do curso:", tableModel.getValueAt(table.getSelectedRow(), 1).toString());
                			if(result != null && !result.equals("")){
                				ConsultasDB.atualizarRegistros("cursos", " curso = '"+ result +"' ", "id_curso", valor);
    							tableModel.setQuery(sql);
                			}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
            		}
            	}
            });
            panel.add(btnAtualizarCurso);
            
            JButton btnExcluirCurso = new JButton("Excluir Curso");
            btnExcluirCurso.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		String valor = linhaSelecionada();
            		if(!valor.equals("")){
            			ConsultasDB.removeRegistros("cursos", "id_curso", valor);
            			try {
							tableModel.setQuery(sql);
						}catch (Exception e) {
							e.printStackTrace();
						}
            			janelaPrincipal.bloquearMenusPrincipais();
            		}	
            	}
            });
            panel.add(btnExcluirCurso);
            //table.getColumnModel().getColumn(0).setPreferredWidth(250);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

	}
	
	public String linhaSelecionada(){
		
		if(table.getSelectedRow() != -1){
            String chavePrimaria = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
            return chavePrimaria;
        }
        else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
		return "";
	}

	
	public void recebeJanelaPrincipal(InternshipManager a){
		janelaPrincipal = a;
	}
	
	public void adicionarCurso(){
		long idCurso = GeradorDeIds.getNewId();
		//int idCurso =  Integer.parseInt(ConsultasDB.contarRegistros("cursos"));
		//idCurso += 1;
		String nomeCurso = JOptionPane.showInputDialog(null, String.format("Codigo do Curso: %d\nDigite o nome do Curso:", idCurso));
		if(nomeCurso != null && !nomeCurso.equals("")){
    		try {
    			ConsultasDB.adicionarRegistros("cursos", "id_curso,curso", String.format("%d,'%s'", idCurso, nomeCurso));
				tableModel.setQuery(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		janelaPrincipal.bloquearMenusPrincipais();
		}
	}

}
