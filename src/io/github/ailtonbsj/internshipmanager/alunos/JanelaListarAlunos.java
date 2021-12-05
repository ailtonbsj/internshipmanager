package io.github.ailtonbsj.internshipmanager.alunos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import io.github.ailtonbsj.internshipmanager.database.ConsultasDB;
import io.github.ailtonbsj.internshipmanager.database.ResultSetSQL;

@SuppressWarnings("serial")
public class JanelaListarAlunos extends JInternalFrame {

	private ResultSetSQL tableModel;
	static final String sql = "SELECT nome,nascimento,cpf,rg,endereco,bairro,cidade,uf,email,cep,telefone,celular,mae,pai FROM alunos";
	private JTable table;
	private JTextField textFieldFiltro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JanelaListarAlunos frame = new JanelaListarAlunos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JanelaListarAlunos() {
		setResizable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Listar Alunos");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 810, 374);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 0, 3, 0));
		panel.setPreferredSize(new Dimension(10, 36));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(5, 0));

		JLabel lblFiltrarAlunos = new JLabel("Filtrar Alunos:");
		panel.add(lblFiltrarAlunos, BorderLayout.WEST);

		textFieldFiltro = new JTextField();
		textFieldFiltro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				try {
		            atualizarTabela();
		        }
		        catch (Exception e) {
		            JOptionPane.showMessageDialog(null, e.getMessage());
		        }
			}
		});
		panel.add(textFieldFiltro, BorderLayout.CENTER);
		textFieldFiltro.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		try {
            tableModel = ConsultasDB.busca(sql);
            table.setModel(tableModel);
            table.getColumnModel().getColumn(0).setPreferredWidth(250);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}

	public void atualizarTabela() {
        String sql1;
        sql1 = sql;

        if(!textFieldFiltro.getText().isEmpty()){
            String filtro = textFieldFiltro.getText();

            sql1 += String.format(" WHERE nome LIKE '%%%s%%' OR to_char(nascimento, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR cpf LIKE '%%%s%%' OR rg LIKE '%%%s%%'"
            		+ " OR endereco LIKE '%%%s%%' OR bairro LIKE '%%%s%%' OR cidade LIKE '%%%s%%' OR uf like '%%%s%%' OR email LIKE '%%%s%%' OR cep LIKE '%%%s%%' OR telefone LIKE '%%%s%%'"
            		+ " OR celular LIKE '%%%s%%' OR mae LIKE '%%%s%%' OR pai LIKE '%%%s%%'",
            		filtro, filtro, filtro, filtro, filtro, filtro, filtro,filtro, filtro, filtro, filtro, filtro, filtro, filtro);
        }
        try {
			tableModel.setQuery(sql1);
		} catch (Exception e) {
		}
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
    }

	public String linhaSelecionada(){

		if(table.getSelectedRow() != -1){
            String chavePrimaria = tableModel.getValueAt(table.getSelectedRow(), 2).toString();
            return chavePrimaria;
        }
        else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
		return "";
	}
}
