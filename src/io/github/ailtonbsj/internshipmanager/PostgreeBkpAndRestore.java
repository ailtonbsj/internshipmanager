package io.github.ailtonbsj.internshipmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import io.github.ailtonbsj.internshipmanager.database.ConversorDates;

public class PostgreeBkpAndRestore {

	public static void realizaBackup() throws IOException, InterruptedException {
		File pasta = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		pasta = new File(pasta.getParent(), "backups");
		pasta.mkdirs();
		pasta = new File(pasta, ConversorDates.DateToString(new Date(), "yyy-MM-dd") + ".backup");

		List<String> comandos;
		if (Config.OS == OperatingSystem.WINDOWS) {
			comandos = new ArrayList<>(Arrays.asList("C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_dump.exe", "-i", "-h",
					Config.HOST, "-p", "5432", "-U", Config.USERNAME, "-F", "c", "-b", "-v", "-f"));
		} else {
			comandos = new ArrayList<>(Arrays.asList("pg_dump", "-h", Config.HOST, "-p", Config.PORT, "-U",
					Config.USERNAME, "-F", "c", "-b", "-v", "-f"));
		}
		comandos.add(pasta.toString());
		comandos.add(Config.DATABASE);

		ProcessBuilder pb = new ProcessBuilder(comandos);
		pb.environment().put("PGPASSWORD", Config.PASSWORD);

		try {
			final Process process = pb.start();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			System.out.println(result);
			reader.close();

			process.waitFor();
			process.destroy();
			PostgreeBkpAndRestore.salvarArquivoConf();
			System.exit(0);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	public static void realizaRestore(File arquivo) throws IOException, InterruptedException {
		List<String> comandos;
		if (Config.OS == OperatingSystem.WINDOWS) {
			comandos = new ArrayList<>(Arrays.asList("C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_restore.exe", "-h",
					Config.HOST, "-p", Config.PORT, "-U", Config.USERNAME, "-d", Config.DATABASE, "-v"));
		} else {
			comandos = new ArrayList<>(Arrays.asList("pg_restore", "-h", Config.HOST, "-p", Config.PORT, "-U", Config.USERNAME,
					"-d", Config.DATABASE, "-v"));
		}
		comandos.add(arquivo.toString());

		ProcessBuilder pb = new ProcessBuilder(comandos);
		pb.environment().put("PGPASSWORD", Config.PASSWORD);

		try {
			final Process process = pb.start();
			final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
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

	public static void createDatabaseFromTerminal() {
		final List<String> comandos = Arrays.asList("C:\\Program Files\\PostgreSQL\\9.3\\bin\\createdb.exe", "-h",
				Config.HOST, "-p", Config.PORT, "-U", Config.USERNAME, Config.DATABASE);
		ProcessBuilder pb = new ProcessBuilder(comandos);
		pb.environment().put("PGPASSWORD", Config.PASSWORD);
		try {
			final Process process = pb.start();
			final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
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

	public static void criaBanco() {
		try {
			DriverManager.getConnection(
					"jdbc:postgresql://" + Config.HOST + "/?user=" + Config.USERNAME + "&password=" + Config.PASSWORD)
					.createStatement().executeUpdate("CREATE DATABASE " + Config.DATABASE);
			JOptionPane.showMessageDialog(null, "Um novo Banco de dados foi criado!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void salvarArquivoConf() {
		File arqConf = new File(InternshipManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		arqConf = new File(arqConf.getParent(), "conf.ini");
		try {
			BufferedWriter bfr = new BufferedWriter(new FileWriter(arqConf));
			bfr.write(ConversorDates.DateToString(new Date(), "dd/MM/yyyy"));
			bfr.close();
		} catch (Exception e) {
			salvarArquivoConf();
		}
	}
}
