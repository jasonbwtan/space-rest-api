package com.jason.space_rest_api.deprecated;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BookingReportServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(req.getParameter("from"));
		System.out.println(req.getParameter("to"));

	    File file = new File("src/main/resources/template.xlsx");
	    FileInputStream inputStream = new FileInputStream(file);
	    XSSFWorkbook wb = null;
		try{
			wb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = wb.getSheetAt(0);
			for (int i = 3; i < 5; i++) {
				XSSFRow row = sheet.createRow(i);
				// iterating j number of columns
				for (int j = 0; j < 5; j++) {
					XSSFCell cell = row.createCell(j);
					cell.setCellValue("Cell" + j);
				}
			}
		} catch (Exception e){
			
		}
		//Headers must come before writing to the stream otherwise you'll set it to the default application/octet-stream
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd");
		String dateString = sdf.format(date);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=space_report"+dateString+".xlsx");
		response.setHeader("Expires:", "0"); // eliminates browser caching

		// write this workbook to an Outputstream.
		wb.write(response.getOutputStream()); // Write workbook to response.
		wb.close();

	}

}
