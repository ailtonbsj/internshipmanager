package br.com.internshipmanager.modelos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import br.com.internshipmanager.InternshipManager;
import br.com.internshipmanager.database.ConsultasDB;
import br.com.internshipmanager.database.ConversorDates;
import br.com.internshipmanager.estagios.Estagiario;


@SuppressWarnings("serial")
public class JanelaGerarDocumentos extends JInternalFrame {
	
	private JPanel pnLista;
	private File pastaModelo;
	private File pastaDocumentos;
	private JButton btGerar;
	private JPanel panel_1;
	private JPanel panel_2;
	private String matricula;
	private String cnpj;
	private JLabel lbAluno;
	private JLabel lbMatricula;
	private JLabel lbEmpresa;
	private JLabel lbCnpj;
	private Calendar cld;
	private String[] semanas = {"Domingo"," "," "," "," "," ","Sábado"};
	
	private int contClick = 0;
	private JComboBox<Object> cbxMes;
	private JLabel lblAno;
	private JTextField tfAno;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaGerarDocumentos frame = new JanelaGerarDocumentos();
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
	public JanelaGerarDocumentos() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setIconifiable(true);
		getContentPane().setSize(new Dimension(100, 0));
		setTitle("Gerar Documentos");
		setBounds(100, 100, 308, 433);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 50));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);
		
		btGerar = new JButton("Gerar");
		btGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contClick++;
				if(contClick <= 1){
					btGerar.setEnabled(false);
					gerarDocumentos();
				} 
			}
		});
		btGerar.setBounds(173, 11, 110, 28);
		panel.add(btGerar);
		
		pnLista = new JPanel();
		FlowLayout fl_pnLista = (FlowLayout) pnLista.getLayout();
		fl_pnLista.setAlignment(FlowLayout.LEFT);
		fl_pnLista.setHgap(50);
		getContentPane().add(pnLista, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		
		panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 120));
		getContentPane().add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);
		
		JLabel lblAluno = new JLabel("Aluno");
		lblAluno.setBounds(10, 3, 46, 14);
		panel_2.add(lblAluno);
		
		JLabel lblMatrcula = new JLabel("Matr\u00EDcula");
		lblMatrcula.setBounds(10, 22, 46, 14);
		panel_2.add(lblMatrcula);
		
		JLabel lblEmpresa = new JLabel("Empresa");
		lblEmpresa.setBounds(10, 41, 46, 14);
		panel_2.add(lblEmpresa);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(10, 60, 46, 14);
		panel_2.add(lblCnpj);
		
		lbAluno = new JLabel("Aluno");
		lbAluno.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbAluno.setBounds(66, 3, 216, 14);
		panel_2.add(lbAluno);
		
		lbMatricula = new JLabel("Matr\u00EDcula");
		lbMatricula.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbMatricula.setBounds(66, 20, 216, 14);
		panel_2.add(lbMatricula);
		
		lbEmpresa = new JLabel("Empresa");
		lbEmpresa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbEmpresa.setBounds(66, 37, 216, 14);
		panel_2.add(lbEmpresa);
		
		lbCnpj = new JLabel("CNPJ");
		lbCnpj.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbCnpj.setBounds(66, 54, 216, 14);
		panel_2.add(lbCnpj);
		
		JLabel lblMs = new JLabel("M\u00EAs");
		lblMs.setBounds(10, 79, 46, 14);
		panel_2.add(lblMs);
		
		cbxMes = new JComboBox<Object>();
		cbxMes.setModel(new DefaultComboBoxModel<Object>(new String[] {"Janeiro", "Fevereiro", "Mar\u00E7o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}));
		cbxMes.setSelectedIndex(0);
		cbxMes.setBounds(66, 71, 86, 20);
		panel_2.add(cbxMes);
		
		lblAno = new JLabel("Ano");
		lblAno.setBounds(10, 98, 46, 14);
		panel_2.add(lblAno);
		
		tfAno = new JTextField();
		tfAno.setBounds(66, 95, 86, 20);
		panel_2.add(tfAno);
		tfAno.setColumns(10);
		
		pastaModelo = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		pastaDocumentos = new File(pastaModelo.getParent(),"documentos");
		pastaModelo = new File(pastaModelo.getParent(),"modelos");
		atualizaLista();
	}
	
	public void atualizaLista(){
		pnLista.removeAll();
		for(String valor : pastaModelo.list()){
			pnLista.add(new JCheckBox(valor, true));
		}
		pnLista.revalidate();
		pnLista.repaint();
	}
	
	public void gerarDocumentos(){
		for(Component contn : pnLista.getComponents()){
			JCheckBox checks = (JCheckBox) contn;
			if(checks.isSelected()){
				File documento = new File(pastaModelo,checks.getText());
				mudaDadosArquivo(matricula,cnpj,documento);
			}
		}
		try {
			Runtime.getRuntime().exec("explorer " + pastaDocumentos);
		} catch (IOException e) {
		}
		btGerar.setEnabled(true);
		contClick = 0;
	}
	
	public void mudaDadosArquivo(String matricula, String cnpj, File arquivo){
		String sql = "SELECT orientadores.nome AS orientador,empresas_alunos.cnpj,empresas_alunos.matricula,empresas_alunos.data_inicio,empresas_alunos.horario_inicio,empresas_alunos.horario_fim,empresas_alunos.data_fim,empresas_alunos.atividades AS ativAluno,empresas_alunos.data_cadastro,empresas_alunos.setor_estagio,empresas_alunos.horario,empresas.nome AS empresa,empresas.nome_fantasia,empresas.endereco AS endEmpresa,empresas.bairro AS bairroEmpresa,empresas.cidade AS cidadeEmpresa,empresas.uf AS ufEmpresa,empresas.cep AS cepEmpresa,empresas.email AS emailEmpresa,empresas.telefone AS telefoneEmpresa,empresas.ramo,empresas.atividades AS ativEmpresa,cursos_alunos.semestre,cursos_alunos.cpf,cursos.curso,alunos.rg,alunos.nome AS aluno,alunos.nascimento,alunos.endereco AS endAluno,alunos.bairro AS bairroAluno,alunos.cidade AS cidadeAluno,alunos.email AS emailAluno,alunos.cep AS cepAluno,alunos.telefone AS telefoneAluno,alunos.celular AS celularAluno,alunos.mae,alunos.pai,alunos.uf AS ufAluno,supervisores.nome AS supervisor,supervisores.cargo AS cargo_supervisor FROM empresas_alunos,empresas,cursos_alunos,cursos,alunos,orientadores,supervisores WHERE orientadores.id_orientador = empresas_alunos.id_orientador AND alunos.cpf = cursos_alunos.cpf AND cursos.id_curso = cursos_alunos.id_curso AND cursos_alunos.matricula = empresas_alunos.matricula AND empresas.cnpj = empresas_alunos.cnpj AND empresas_alunos.supervisor = supervisores.id_supervisor AND empresas_alunos.cnpj = '"+ cnpj +"' AND empresas_alunos.matricula = '"+ matricula +"'";
		
		try {
			File modeloAtual = new File(pastaDocumentos,arquivo.getName());
			copyFile(arquivo, modeloAtual);
			FileInputStream fis = new FileInputStream(modeloAtual);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			HWPFDocument doc = new HWPFDocument(fs);
						
			Statement busca = ConsultasDB.getStatement();
			ResultSet resultado = busca.executeQuery(sql);
			resultado.next();
			
			String horarioSemana = new String("");
			String cadastroMoreOne = new String("");

			ResultSetMetaData rsMetaData = resultado.getMetaData();
			
			for(int i=1; i<=rsMetaData.getColumnCount(); i++){
				String coluna = rsMetaData.getColumnName(i);
				String valor = (resultado.getObject(coluna) == null) ? "" : resultado.getObject(coluna).toString();
				
				if(coluna.equals("horario")){
					horarioSemana = "" + (Integer.parseInt(valor)*5);
				}
				else if(coluna.equals("data_cadastro")){
					Date dc = ConversorDates.DateBaseToDate(valor);
					Calendar cld = Calendar.getInstance();
					cld.setTime(dc);
					cld.set(Calendar.YEAR, cld.get(Calendar.YEAR)+1);
					dc = cld.getTime();
					cadastroMoreOne = ConversorDates.DateToString(dc, "dd/MM/yyyy");
				}

				if(		coluna.equals("data_inicio") ||
						coluna.equals("data_fim") ||
						coluna.equals("data_cadastro") ||
						coluna.equals("nascimento")){
					valor = ConversorDates.DateBaseToDateCommon(valor);					
				}
				
				doc = replaceText(doc,"#"+ coluna.toUpperCase() +"#",valor);
				
			}
			int anoNow = Integer.parseInt(tfAno.getText());
			cld.set(anoNow, cbxMes.getSelectedIndex(), 1);
			int totalDiaMes = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			
			if(arquivo.getName().equals("Frequência do Estágio I.doc")){
				for(int i=1;i<=31;i++){
					String tk  = "#DIA"+ i +"#";
					if(i<=totalDiaMes){
						cld.set(anoNow, cbxMes.getSelectedIndex(), i);
						doc = replaceText(doc,tk,semanas[cld.get(Calendar.DAY_OF_WEEK)-1]);
					} else {
						doc = replaceText(doc,tk,"*******************");
					}
				}
			}
			doc = replaceText(doc,"#ANO#",tfAno.getText());
			doc = replaceText(doc,"#MES#",(String) cbxMes.getSelectedItem());
			doc = replaceText(doc,"#HORARIO_SEMANA#", horarioSemana);
			doc = replaceText(doc,"#DATA_CADASTRO_MOREONE#", cadastroMoreOne);
			
			saveWord(modeloAtual.toString(),doc);
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void atualizaDadosEstagio(Estagiario est){
		cld = Calendar.getInstance();
		cbxMes.setSelectedIndex(cld.get(Calendar.MONTH));
		tfAno.setText(cld.get(Calendar.YEAR) + "");
		this.matricula = est.getMatricula();
		this.cnpj = est.getCnpj();
		lbMatricula.setText(this.matricula);
		lbCnpj.setText(this.cnpj);
		lbEmpresa.setText(est.getEmpresa());
		lbAluno.setText(est.getNome());
	}
	
	public void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	
	private static HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText){
        Range r1 = doc.getRange(); 

        for (int i = 0; i < r1.numSections(); ++i ) { 
            Section s = r1.getSection(i); 
            for (int x = 0; x < s.numParagraphs(); x++) { 
                Paragraph p = s.getParagraph(x); 
                for (int z = 0; z < p.numCharacterRuns(); z++) { 
                    CharacterRun run = p.getCharacterRun(z); 
                    String text = run.text();
                    if(text.contains(findText)) {
                        run.replaceText(findText, replaceText);
                    } 
                }
            }
        } 
        return doc;
    }
	
	private static void saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(filePath);
            doc.write(out);
        }
        finally{
            out.close();
        }
    }
}
