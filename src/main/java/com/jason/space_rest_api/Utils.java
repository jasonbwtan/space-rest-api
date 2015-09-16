package com.jason.space_rest_api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	static final Logger logger = LogManager.getLogger();

	public static Date formatDate(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		logger.debug(sdf.parse(date));
		return sdf.parse(date);
	}
	
	public static String validateMsidsn(String msidsn){
		return "";
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(Utils.formatDate("2015/05/01 17:00"));
	}
	
	public static String validateAdditionalComments(String additionalComments){
		 return additionalComments.substring(0, Math.min(additionalComments.length(), 255));
	}
}
