package com.jason.space_rest_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jason.space_rest_api.hibernate.model.Customer;
import com.javacodegeeks.snippets.enterprise.hibernate.service.CustomerService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("api")
public class MyResource {
	static final Logger logger = LogManager.getLogger();
	static final CustomerService customerService = CustomerService
			.getInstance();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Hello, Jersey-Rest-Heroku (space)!";
	}

	@GET
	@Path("/db")
	public String getDbInfo() {
		String DATABASE_URL = System.getenv("DATABASE_URL");
		URI dbUri = null;

		if (DATABASE_URL != null && !DATABASE_URL.equals("")) {
			try {
				dbUri = new URI(DATABASE_URL);
			} catch (URISyntaxException e) {
				logger.info("Could not read DATABASE_URL heroku environment variable");
				e.printStackTrace();
			}
		}
		return "hibernate.connection.url:" + "jdbc:postgresql://"
				+ dbUri.getHost() + ":" + dbUri.getPort() + "/"
				+ dbUri.getPath();

	}
	@GET
	@Path("/getall")
	public String getAll() {
		return "result"+customerService.findAll().toString();

	}

	@GET
	@Path("/getreport")
	@Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public Response exportExcel(@QueryParam("from") String from,
			@QueryParam("to") String to) throws Exception {
		String params = from + to;
		System.out.println(params);
		File file = new File("src/main/resources/template.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		List<Customer> customerList = null;
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);

		if (from != null && to != null) {
			Date startDate = Utils.formatDate(from);
			Date endDate = Utils.formatDate(to);
			customerList = customerService.findByDate(startDate, endDate);
			//wb = Utils.fillReport(wb, customerList);
		} else {
			customerList = customerService.findAll();
			//wb = Utils.fillReport(wb, customerList);
		}
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
		StreamingOutput stream = new StreamingOutput() {
			public void write(OutputStream output) throws IOException,
					WebApplicationException {
				try {
					wb.write(output);
					wb.close();
				} catch (Exception e) {
					throw new WebApplicationException(e);
				}
			}
		};
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd");
		String dateString = sdf.format(date);
		return Response
				.ok(stream)
				.header("content-disposition",
						"attachment; filename = space-bookings-export_"
								+ dateString + ".xlsx").build();

	}

	@POST
	@Path("/add")
	public Response addUser(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("phone") String phone,
			@FormParam("organisation") String organisation,
			@FormParam("startDate") String startDateString,
			@FormParam("endDate") String endDateString,
			@FormParam("numberOfPeople") int numberOfPeople,
			@FormParam("catering") int catering,
			@FormParam("additionalComments") String additionalComments) {
		try {
			logger.info(String
					.format("Received POST Request with params:[name:%s,organisation:%s,date:%s,numberOfPeople:%s,catering:%s,overtime:%s",
							name, organisation, startDateString, endDateString, numberOfPeople,
							catering));
			Date startDate = Utils.formatDate(startDateString);
			Date endDate = Utils.formatDate(endDateString);
			Customer customer = new Customer(name, email, phone, organisation,
					startDate, endDate, numberOfPeople, catering,
					additionalComments);
			customerService.persist(customer);
		} catch (ParseException e) {
			return Response
					.status(200)
					.entity(String
							.format("Received POST Request, but could not parse dateString:%s",
									startDateString)).build();
		}
		return Response
				.status(200)
				.entity(String
						.format("Successfully received and submitted POST Request with params:[name:%s,organisation:%s,startDate:%s,endDate:%s,numberOfPeople:%s,catering:%s",
								name, organisation, startDateString,endDateString, numberOfPeople,
								catering)).build();
	}

	// @GET
	// @Path("/getreport")
	// @Produces("text/plain")
	// public Response getReport() throws IOException {
	// Date date = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd");
	// String dateString = sdf.format(date);
	// // File file = new File("foo.txt");
	// // FileOutputStream out = new FileOutputStream(file);
	// // byte[] data = {1, 2, 3, 4, 5};
	// // out.write(data);
	// // out.close();
	// PrintWriter writer = new PrintWriter("foo.txt", "UTF-8");
	// writer.println("The first line");
	// writer.println("The second line");
	// writer.close();
	// File file = new File("foo.txt");
	//
	// ResponseBuilder response = Response.ok((Object) file);
	// response.header("Content-Disposition",
	// "attachment; filename=\"space_report_"+dateString+".txt\"");
	// return response.build();
	//
	// }

}
