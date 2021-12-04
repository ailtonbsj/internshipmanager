package io.github.ailtonbsj.internshipmanager.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ConversorDates {
	
	public static String DateBaseToDateCommon(String datebase){
		SimpleDateFormat inDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outDate = new SimpleDateFormat("dd/MM/yyyy");
		Date dt;
		try {
			dt = inDate.parse(datebase);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return outDate.format(dt);
	}
	
	public static String DateCommonToDateBase(String datecommon){
		SimpleDateFormat inDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outDate = new SimpleDateFormat("dd/MM/yyyy");
		Date dt;
		try {
			dt = outDate.parse(datecommon);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return inDate.format(dt);
	}
	
	public static Date DateBaseToDate(String datebase){
		SimpleDateFormat inDate = new SimpleDateFormat("yyyy-MM-dd");
		Date dt;
		try {
			dt = inDate.parse(datebase);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return dt;
	}
	
	public static Date DateCommonToDate(String datecommon){
		SimpleDateFormat outDate = new SimpleDateFormat("dd/MM/yyyy");
		Date dt;
		try {
			dt = outDate.parse(datecommon);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return dt;
	}

	public static String DateToString(Date dt,String mask){
		SimpleDateFormat outDate = new SimpleDateFormat(mask);
		return outDate.format(dt);
	}
	
	public static Date StringToDate(String dt,String mask) throws ParseException {
		SimpleDateFormat outDate = new SimpleDateFormat(mask);
		Date data = outDate.parse(dt);
		return data;
	}
}
