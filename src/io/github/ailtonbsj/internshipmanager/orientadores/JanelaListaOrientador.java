package io.github.ailtonbsj.internshipmanager.orientadores;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import io.github.ailtonbsj.internshipmanager.InternshipManager;
import io.github.ailtonbsj.internshipmanager.database.ConsultasDB;
import io.github.ailtonbsj.internshipmanager.database.GeradorDeIds;
import io.github.ailtonbsj.internshipmanager.database.ResultSetSQL;

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
			@Override
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
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setResizable(true);
		setIconifiable(true);
		setBounds(100, 100, 636, 271);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		try {
			tableModel = ConsultasDB.busca(sql);
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				adicionarOrientador();
			}
		});
		panel.add(btnAdicionarOrientador);

		JButton btnAtualizar = new JButton("Atualizar Orientador");
		btnAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				String valor = linhaSelecionada();
				if (!valor.equals("")) {
					try {
						String result = JOptionPane.showInputDialog("Alterar nome do orientador:",
								tableModel.getValueAt(table.getSelectedRow(), 1).toString());
						if (result != null && !result.equals("")) {
							ConsultasDB.atualizarRegistros("orientadores", " nome = '" + result + "' ", "id_orientador",
									valor);
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String valor = linhaSelecionada();
				if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja exluir?") == 0) {
					if (!valor.equals("")) {
						ConsultasDB.removeRegistros("orientadores", "id_orientador", valor);
						try {
							tableModel.setQuery(sql);
						} catch (Exception e) {
						}
						janelaPrincipal.bloquearMenusPrincipais();
					}
				}
			}
		});
		panel.add(btnExcluir);

	}

	public String linhaSelecionada() {

		if (table.getSelectedRow() != -1) {
			String chavePrimaria = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
			return chavePrimaria;
		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma Linha");
		}
		return "";
	}

	public void recebeJanelaPrincipal(InternshipManager a) {
		janelaPrincipal = a;
	}

	public void adicionarOrientador() {
		long idOrien = GeradorDeIds.getNewId();
		String nomeOrien = JOptionPane.showInputDialog(null,
				String.format("Codigo do Orientador: %d\nDigite o nome do Orientador:", idOrien));
		if (nomeOrien != null && !nomeOrien.equals("")) {
			try {
				ConsultasDB.adicionarRegistros("orientadores", "id_orientador,nome",
						String.format("%d,'%s'", idOrien, nomeOrien));
				tableModel.setQuery(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			janelaPrincipal.bloquearMenusPrincipais();
		}
	}

}
