package io.github.ailtonbsj.internshipmanager.estagios;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import io.github.ailtonbsj.internshipmanager.database.ConsultasDB;
import io.github.ailtonbsj.internshipmanager.database.ResultSetSQL;

@SuppressWarnings("serial")
public class JanelaListarEstagios extends JInternalFrame {
	
	private ResultSetSQL tableModel;
	static final String sql = "SELECT alunos.nome AS aluno,alunos.nascimento,cursos_alunos.cpf,empresas_alunos.matricula,empresas_alunos.cnpj,empresas.nome AS empresa,orientadores.nome AS orientador, empresas_alunos.data_inicio,empresas_alunos.data_fim,empresas_alunos.horario FROM empresas_alunos,cursos_alunos,alunos,empresas, orientadores WHERE (empresas_alunos.matricula = cursos_alunos.matricula AND cursos_alunos.cpf = alunos.cpf AND empresas.cnpj = empresas_alunos.cnpj AND empresas_alunos.id_orientador = orientadores.id_orientador)";
	private JTable table;
	private JTextField textFieldFiltro;

	public JanelaListarEstagios() {
		setResizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Listar Est\u00E1gios");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 810, 374);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 0, 3, 0));
		panel.setPreferredSize(new Dimension(10, 36));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(5, 0));
		
		JLabel lblFiltrarAlunos = new JLabel("Filtrar Estágios:");
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
            String filtro = textFieldFiltro.getText().toLowerCase();
            String filtroUp = filtro.toUpperCase();

                  
            sql1 += String.format(" AND ("
            		+ "alunos.nome LIKE '%%%s%%' OR alunos.nome LIKE '%%%s%%' OR "
            		+ "to_char(nascimento, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR to_char(nascimento, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR "
            		+ "cursos_alunos.cpf LIKE '%%%s%%' OR cursos_alunos.cpf LIKE '%%%s%%' OR "
            		+ "empresas_alunos.matricula LIKE '%%%s%%' OR empresas_alunos.matricula LIKE '%%%s%%' OR "
            		+ "empresas_alunos.cnpj LIKE '%%%s%%' OR empresas_alunos.cnpj LIKE '%%%s%%' OR "
            		+ "empresas.nome LIKE '%%%s%%' OR empresas.nome LIKE '%%%s%%' OR "
            		+ "orientadores.nome LIKE '%%%s%%' OR orientadores.nome LIKE '%%%s%%' OR "
            		+ "to_char(data_inicio, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR to_char(data_inicio, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR "
            		+ "to_char(data_fim, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' OR to_char(data_fim, 'yyyy/MM/dd hh:mm:ss') LIKE '%%%s%%' )",
            		filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro, filtroUp, filtro,filtroUp,
            		filtro, filtroUp);
        }
        try {
			tableModel.setQuery(sql1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
    }
	
	public Estagiario linhaSelecionada(){
		Estagiario selecionado = new Estagiario(null,null,null,null);
		
		if(table.getSelectedRow() != -1){
            selecionado.setMatricula(tableModel.getValueAt(table.getSelectedRow(), 3).toString());
            selecionado.setCnpj(tableModel.getValueAt(table.getSelectedRow(), 4).toString());
            selecionado.setNome(tableModel.getValueAt(table.getSelectedRow(), 0).toString());
            selecionado.setEmpresa(tableModel.getValueAt(table.getSelectedRow(), 5).toString());
            return selecionado;
        }
        else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
		return null;
	}
}
