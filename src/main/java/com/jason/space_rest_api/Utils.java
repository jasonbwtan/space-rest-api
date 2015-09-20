package com.jason.space_rest_api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jason.space_rest_api.hibernate.model.Customer;
import com.javacodegeeks.snippets.enterprise.hibernate.service.CustomerService;

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
	
	public static XSSFWorkbook fillReport(XSSFWorkbook wb,List<Customer> customerList){
		XSSFSheet sheet = wb.getSheetAt(0);
		//Customer e = new Customer("JT1", "jason@testmail.com","07123456789", "com.JT", new Date(
				//System.currentTimeMillis()), 5, 1, 1,"it was great");
		//customerList.add(e);
		for (int i = 0; i < customerList.size(); i++) {
			XSSFRow row = sheet.createRow(i + 3);
			Customer customer = customerList.get(i);
			for (int j = 0; j < 9; j++) {
				XSSFCell cell = row.createCell(j);
				switch (j) {
				case 0:
					cell.setCellValue(customer.getCreated());
					break;					
				case 1:
					cell.setCellValue(customer.getName());
					break;
				case 2:
					cell.setCellValue(customer.getEmail());
					break;
				case 3:
					cell.setCellValue(customer.getPhone());
					break;
				case 4:
					cell.setCellValue(customer.getOrganisation());
					break;
				case 5:
					cell.setCellValue(customer.getStartDate());
					break;
				case 6:
					cell.setCellValue(customer.getEndDate());
					break;
				case 7:
					cell.setCellValue(customer.getNumberOfPeople());
					break;
				case 8:
					cell.setCellValue(customer.isCatering());
					break;
				case 9:
					cell.setCellValue(customer.getAdditionalComments());
					break;						
				default:
					break;
				}
			}
		}
		return wb;
	}
}
