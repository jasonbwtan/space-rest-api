package com.jason.space_rest_api;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jason.space_rest_api.hibernate.model.Customer;
import com.javacodegeeks.snippets.enterprise.hibernate.service.CustomerService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
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
	public String getDbInfo(){
		String DATABASE_URL = System.getenv("DATABASE_URL");
		URI dbUri = null;

		if(DATABASE_URL != null && !DATABASE_URL.equals("")){
			try {
				dbUri = new URI(DATABASE_URL);
			} catch (URISyntaxException e) {
				logger.info("Could not read DATABASE_URL heroku environment variable");
				e.printStackTrace();
			}
		}
		return "hibernate.connection.url:"+"jdbc:postgresql://"+dbUri.getHost()+":"+dbUri.getPort()+"/"+dbUri.getPath();

	}
	@POST
	@Path("/add")
	public Response addUser(@FormParam("name") String name,
			@FormParam("organisation") String organisation,
			@FormParam("date") String dateString,
			@FormParam("numberOfPeople") int numberOfPeople,
			@FormParam("catering") int catering,
			@FormParam("overtime") int overtime) {
		try {
			logger.info(String
					.format("Received POST Request with params:[name:%s,organisation:%s,date:%s,numberOfPeople:%s,catering:%s,overtime:%s",
							name, organisation, dateString, numberOfPeople,
							catering, overtime));
			Date date = Utils.formatDate(dateString);

			Customer customer = new Customer(name, organisation, date,
					numberOfPeople, catering, overtime);
			customerService.persist(customer);
		} catch (ParseException e) {
			return Response
					.status(200)
					.entity(String
							.format("Received POST Request, but could not parse dateString:%s",
									dateString)).build();
		}
		return Response
				.status(200)
				.entity(String
						.format("Received POST Request with params:[name:%s,organisation:%s,date:%s,numberOfPeople:%s,catering:%s,overtime:%s",
								name, organisation, dateString, numberOfPeople,
								catering, overtime)).build();
	}

	
//	@GET
//	@Path("/getreport")
//	@Produces("text/plain")
//	public Response getReport() throws IOException {
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd");
//		String dateString = sdf.format(date);
////		File file = new File("foo.txt");
////		FileOutputStream out = new FileOutputStream(file);
////		byte[] data = {1, 2, 3, 4, 5};
////		out.write(data);
////		out.close();
//		PrintWriter writer = new PrintWriter("foo.txt", "UTF-8");
//		writer.println("The first line");
//		writer.println("The second line");
//		writer.close();
//		File file = new File("foo.txt");
//		
//		ResponseBuilder response = Response.ok((Object) file);
//		response.header("Content-Disposition",
//				"attachment; filename=\"space_report_"+dateString+".txt\"");
//		return response.build();
//
//	}

}
