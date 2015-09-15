package com.jason.space_rest_api.heroku;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;

public class HelloWorld extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!\n");
    }

    public static void main(String[] args) throws Exception{
        int port = 5000;
        try {
        	port = Integer.valueOf(System.getenv("PORT"));
        } catch(NumberFormatException e) {}

        Server server = new Server(port);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setSecurityHandler(basicAuth("scott", "tiger", "Private!"));
        context.setContextPath("/");
        server.setHandler(context);
        //context.addServlet(new ServletHolder(new HelloWorld()),"/");
        context.addServlet(new ServletHolder(new HelloWorld()),"/foobar");
        context.addServlet(new ServletHolder(new HelloWorld()),"/foobar2");

        server.start();
        server.join();   
    }
    
    private static final SecurityHandler basicAuth(String username, String password, String realm) {

    	HashLoginService l = new HashLoginService();
        l.putUser(username, Credential.getCredential(password), new String[] {"user"});
        l.setName(realm);
        
        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);
        constraint.setRoles(new String[]{"user"});
        constraint.setAuthenticate(true);
         
        ConstraintMapping cm = new ConstraintMapping();
        cm.setConstraint(constraint);
        cm.setPathSpec("/foobar");
        
        ConstraintSecurityHandler csh = new ConstraintSecurityHandler();
        csh.setAuthenticator(new BasicAuthenticator());
        csh.setRealmName("myrealm");
        csh.addConstraintMapping(cm);
        csh.setLoginService(l);
        
        return csh;
    	
    }
}