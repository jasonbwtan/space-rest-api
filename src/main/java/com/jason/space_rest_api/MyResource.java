package com.jason.space_rest_api;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Type;

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

	@GET
	@Path("/getreport")
	@Produces("text/plain")
	public Response getReport() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd");
		String dateString = sdf.format(date);
		File file = new File("C:\\tmp\\rest-demo\\foo.txt");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=\"space_report_"+dateString+".txt\"");
		return response.build();

	}

}
