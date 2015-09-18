package com.jason.space_rest_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportGenerator {
	
	public void generateReport(){
		
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, InvalidFormatException {
	    File file = new File("src/main/resources/template.xlsx");
	    FileInputStream inputStream = new FileInputStream(file);

		try(XSSFWorkbook wb = new XSSFWorkbook(inputStream);){
			XSSFSheet sheet = wb.getSheetAt(0);
			for (int i = 3; i < 5; i++) {
				XSSFRow row = sheet.createRow(i);
				// iterating j number of columns
				for (int j = 0; j < 5; j++) {
					XSSFCell cell = row.createCell(j);
					cell.setCellValue("Cell" + j);
				}
			}
		}
		
		
	}
}
