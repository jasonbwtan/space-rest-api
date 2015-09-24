package com.jason.space_rest_api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jason.space_rest_api.deprecated.CustomerService;
import com.jason.space_rest_api.hibernate.model.Customer;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

public class Utils {
	static final Logger logger = LogManager.getLogger(Utils.class);

	public static Date formatDate(String date) throws ParseException {
		String[] acceptedFormats = {"dd/MM/yyyy","dd/MM/yyyy HH:mm","dd-MM-yyyy HH:mm:ss"};
		Date obj = DateUtils.parseDate(date,acceptedFormats);
		logger.debug("Utils.formatDate: converted "+date+" to: "+obj);
		return obj;
	}

	public static String validateMsidsn(String msidsn) {
		return "";
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(Utils.formatDate("2015/05/01 17:00"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sdf.parse("2015-10-02 03:10:00"));
		
		String[] acceptedFormats = {"dd/MM/yyyy","dd/MM/yyyy HH:mm","dd-MM-yyyy HH:mm:ss"};
		System.out.println(DateUtils.parseDate("12/07/2012", acceptedFormats));
		System.out.println(DateUtils.parseDate("12/07/2012 23:59:59", acceptedFormats));
	}
	
	public static String validateAdditionalComments(String additionalComments) {
		return additionalComments.substring(0,
				Math.min(additionalComments.length(), 255));
	}

	public static XSSFWorkbook fillReport(XSSFWorkbook wb,
			List<Customer> customerList) {
		XSSFSheet sheet = wb.getSheetAt(0);
		// Customer e = new Customer("JT1", "jason@testmail.com","07123456789",
		// "com.JT", new Date(
		// System.currentTimeMillis()), 5, 1, 1,"it was great");
		// customerList.add(e);
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

	public static void sendMail(Customer customer) {
		SendGrid sendgrid = new SendGrid("app41598790@heroku.com",
				"y7czbo6c1513");

		SendGrid.Email email = new SendGrid.Email();
		email.addTo("jasonbwtan@hotmail.com");
		email.setFrom("jasonbwtan@hotmail.com");
		email.setSubject("Space Booking Request for: " + customer.getName()
				+ " Date: " + customer.getCreated());
		email.setHtml("<p>Details for booking request received on: "
				+ customer.getCreated() + "</p>" + "<p>Name: <b>"
				+ customer.getName() + "</b><br>" + "Email: <b>"
				+ customer.getEmail() + "</b><br>" + "Phone: <b>"
				+ customer.getPhone() + "</b><br>" + "Organisation: <b>"
				+ customer.getOrganisation() + "</b><br>" + "Start Date: <b>"
				+ customer.getStartDate() + "</b><br>" + "End Date: <b>"
				+ customer.getEndDate() + "</b><br>" + "Number of People: <b>"
				+ customer.getNumberOfPeople() + "</b><br>" + "Catering: <b>"
				+ customer.isCatering() + "</b><br>" + "Additional Comments: <b>"
				+ customer.getAdditionalComments() + "</b><br></p>" + "<p>Sent automatically from https://space-rest-api.herokuapp.com");
		try {
			logger.info("Sendgrid: sending mail...");
			SendGrid.Response response = sendgrid.send(email);
			logger.info("Sendgrid: response code.."+response.getCode());
		} catch (SendGridException e) {
			System.out.println(e);
		}
	}
}
