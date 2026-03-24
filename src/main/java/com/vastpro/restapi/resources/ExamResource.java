package com.vastpro.restapi.resources;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("/exam")
public class ExamResource {
	
	@Context
	private ServletContext servletContext;
	
	@Context
	private HttpServletRequest request;
	
	
}
