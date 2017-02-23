package br.com.internshipmanager.orientadores;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import br.com.internshipmanager.InternshipManager;
import br.com.internshipmanager.database.ConsultasDB;
import br.com.internshipmanager.database.GeradorDeIds;
import br.com.internshipmanager.database.ResultSetSQL;

@SuppressWarnings("serial")
public class JanelaListaOrientador extends JInternalFrame {
	
	private JTable table;
	private ResultSetSQL tableModel;
	static final String sql = "SELECT * FROM orientadores";
	InternshipManager janelaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaListaOrientador frame = new JanelaListaOrientador();
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
	public JanelaListaOrientador() {
		
		setTitle("Orientadores");
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setIconifiable(true);
		setBounds(100, 100, 480, 244);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		try {
			tableModel = new ResultSetSQL(InternshipManager.DATABASE_URL,InternshipManager.USERNAME,InternshipManager.PASSWORD,sql);
			table = new JTable();
			scrollPane.setViewportView(table);
			table.setModel(tableModel);
			table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			
		} catch (Exception e) {
		}
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnAdicionarOrientador = new JButton("Adicionar Orientador");
		btnAdicionarOrientador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarOrientador();				
			}
		});
		panel.add(btnAdicionarOrientador);
		
		JButton btnAtualizar = new JButton("Atualizar Orientador");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
        		String valor = linhaSelecionada();
        		if(!valor.equals("")){
        			try {
        				String result = JOptionPane.showInputDialog("Alterar nome do orientador:", tableModel.getValueAt(table.getSelectedRow(), 1).toString());
            			if(result != null && !result.equals("")){
            				ConsultasDB.atualizarRegistros("orientadores", " nome = '"+ result +"' ", "id_orientador", valor);
							tableModel.setQuery(sql);
            			}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
        		}
				
			}
		});
		panel.add(btnAtualizar);
		
		JButton btnExcluir = new JButton("Excluir Orientador");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
        		String valor = linhaSelecionada();
        		if(!valor.equals("")){
        			ConsultasDB.removeRegistros("orientadores", "id_orientador", valor);
        			try {
						tableModel.setQuery(sql);
					} catch (Exception e) {
					}
        			janelaPrincipal.bloquearMenusPrincipais();
        		}
				
			}
		});
		panel.add(btnExcluir);

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
	
	public void adicionarOrientador(){
		long idOrien = GeradorDeIds.getNewId();
		//int idOrien =  Integer.parseInt(ConsultasDB.contarRegistros("orientadores"));
		//idOrien += 1;
		String nomeOrien = JOptionPane.showInputDialog(null, String.format("Codigo do Orientador: %d\nDigite o nome do Orientador:", idOrien));
		if(nomeOrien != null && !nomeOrien.equals("")){
    		try {
    			ConsultasDB.adicionarRegistros("orientadores", "id_orientador,nome", String.format("%d,'%s'", idOrien, nomeOrien));
				tableModel.setQuery(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		janelaPrincipal.bloquearMenusPrincipais();
		}
	}

}
