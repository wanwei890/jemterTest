package com.mytest.Learning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class time_format_converter {
	public static void main(String[] args){
		String str = "2017-01-09";
		System.out.println(formatDate());
		System.out.println(parse(str));
	}
	/**
	   * 获取现在时间
	   * 
	   * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	   */
	public static String formatDate(){
		Date current_time = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestring = formater.format(current_time);
		return datestring;
	}
	// String转Date
	public static Date parse(String str){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
		try{
			date = format1.parse(str);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
