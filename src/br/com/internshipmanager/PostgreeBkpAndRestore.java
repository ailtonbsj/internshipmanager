package br.com.internshipmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import br.com.internshipmanager.database.ConversorDates;

public class PostgreeBkpAndRestore {

	public static void realizaBackup() throws IOException, InterruptedException {
		final ArrayList<String> comandos = new ArrayList<String>();

		// comandos.add("C:\\Program Files (x86)\\PostgreSQL\\8.4\\bin\\pg_dump.exe");
		// // esse é meu caminho
		comandos.add("C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_dump.exe");

		comandos.add("-i");
		comandos.add("-h");
		comandos.add("localhost");
		comandos.add("-p");
		comandos.add("5432");
		comandos.add("-U");
		comandos.add("postgres");
		comandos.add("-F");
		comandos.add("c");
		comandos.add("-b");
		comandos.add("-v");
		comandos.add("-f");

		File pasta = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		pasta = new File(pasta.getParent(),"backups");
		pasta = new File(pasta,ConversorDates.DateToString(new Date(), "yyy-MM-dd") + ".backup");
		comandos.add(pasta.toString()); // eu utilizei meu C:\ e D:\ para os
											// testes e gravei o backup com
											// sucesso.
		comandos.add("internshipdb");

		ProcessBuilder pb = new ProcessBuilder(comandos);

		pb.environment().put("PGPASSWORD", "123");

		try {
			final Process process = pb.start();

			final BufferedReader r = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			String line = r.readLine();
			while (line != null) {
				//System.err.println(line);
				line = r.readLine();
			}
			r.close();

			process.waitFor();
			process.destroy();
			//System.out.println("backup realizado com sucesso.");
			PostgreeBkpAndRestore.salvarArquivoConf();
			System.exit(0);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}

	public static void realizaRestore(File arquivo) throws IOException, InterruptedException{        
	       final ArrayList<String> comandos = new ArrayList<String>();        
	           
	       comandos.add("C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_restore.exe"); //testado no windows xp  
	         
	         
	       comandos.add("-i");        
	       comandos.add("-h");        
	       comandos.add("localhost");    //ou   comandos.add("192.168.0.1");   
	       comandos.add("-p");        
	       comandos.add("5432");        
	       comandos.add("-U");        
	       comandos.add("postgres");        
	       comandos.add("-d");        
	       comandos.add("internshipdb");       
	       comandos.add("-v");        
	           
	       comandos.add(arquivo.toString());   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.    
	  
	       ProcessBuilder pb = new ProcessBuilder(comandos);        
	           
	       pb.environment().put("PGPASSWORD", "123");     //Somente coloque sua senha           
	           
	       try {        
	           final Process process = pb.start();        
	       
	           final BufferedReader r = new BufferedReader(        
	               new InputStreamReader(process.getErrorStream()));        
	           String line = r.readLine();        
	           while (line != null) {        
	           System.err.println(line);        
	           line = r.readLine();        
	           }        
	           r.close();        
	       
	           process.waitFor();      
	           process.destroy();    
	       
	       } catch (IOException e) {        
	           e.printStackTrace();        
	       } catch (InterruptedException ie) {        
	           ie.printStackTrace();        
	       }           
	             
	   }
	
	public static void criaBanco() throws IOException, InterruptedException{        
	       final ArrayList<String> comandos = new ArrayList<String>();        
	           
	       comandos.add("C:\\Program Files\\PostgreSQL\\9.3\\bin\\createdb.exe"); //testado no windows xp  
	         
	                 
	       comandos.add("-h");        
	       comandos.add("localhost");    //ou   comandos.add("192.168.0.1");   
	       comandos.add("-p");        
	       comandos.add("5432");        
	       comandos.add("-U");        
	       comandos.add("postgres");                
	       comandos.add("internshipdb");        
  
	       ProcessBuilder pb = new ProcessBuilder(comandos);        
	           
	       pb.environment().put("PGPASSWORD", "123");     //Somente coloque sua senha           
	           
	       try {        
	           final Process process = pb.start();        
	       
	           final BufferedReader r = new BufferedReader(        
	               new InputStreamReader(process.getErrorStream()));        
	           String line = r.readLine();        
	           while (line != null) {        
	           System.err.println(line);        
	           line = r.readLine();        
	           }        
	           r.close();        
	       
	           process.waitFor();      
	           process.destroy();   
	       
	       } catch (IOException e) {        
	           e.printStackTrace();        
	       } catch (InterruptedException ie) {        
	           ie.printStackTrace();        
	       }           
	             
	   }
	
	public static void salvarArquivoConf(){
		File arqConf = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		arqConf = new File(arqConf.getParent(),"conf.ini");
		try {
			BufferedWriter bfr = new BufferedWriter(new FileWriter(arqConf));
			bfr.write(ConversorDates.DateToString(new Date(), "dd/MM/yyyy"));
			bfr.close();
		} catch (Exception e) {
			salvarArquivoConf();
		}
	}
}
