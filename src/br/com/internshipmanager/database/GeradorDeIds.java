package br.com.internshipmanager.database;

import java.sql.Timestamp;
import java.util.Calendar;

public class GeradorDeIds {
	
	public static long getNewId(){
		Calendar cld = Calendar.getInstance();
		int ano = cld.get(Calendar.YEAR);
		int mes = cld.get(Calendar.MONTH);
		int dia = cld.get(Calendar.DATE);
		int hor = cld.get(Calendar.HOUR);
		int min = cld.get(Calendar.MINUTE);
		int sec = cld.get(Calendar.SECOND);
		int mil = cld.get(Calendar.MILLISECOND);
		Timestamp nanostamp = new Timestamp(System.nanoTime());
		int nano = nanostamp.getNanos()/1000000;
		
		return Long.parseLong("" + nano + mil + sec + min + hor + dia + mes + ano);
	}
	
}
