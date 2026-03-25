package com.vastpro.restapi.resources;

import java.util.Map;
import com.vastpro.utility.CreateConnection;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceContainer;

import com.vastpro.utility.CreateConnection;

@Path("/exam")
public class ExamResource {
	private Delegator getDelegator(ServletContext servletContext) {
        Delegator delegator = (Delegator) servletContext.getAttribute("delegator");
        if (delegator == null) {
            delegator = DelegatorFactory.getDelegator("default");
        }
        return delegator;
    }

    public LocalDispatcher getDispatcher(ServletContext servletContext) {
        LocalDispatcher dispatcher =
            (LocalDispatcher) servletContext.getAttribute("dispatcher");

        if (dispatcher == null) {
            dispatcher = ServiceContainer.getLocalDispatcher(
                "sphinx",   // must match web.xml
                getDelegator(servletContext)
            );
        }
        return dispatcher;
    }
    
	@Context
	private ServletContext servletContext;
	
	@Context
	private HttpServletRequest request;

    @POST
    @Path("/createQuestion")
    public Response getUser(Map<String, Object> context) {
    	try {
    		//transfer the data to create question service service
    		//exam name, topic, question details, option A, option B, option C, option D, answer
    		LocalDispatcher dispatcher = getDispatcher(servletContext);
    		Map<String, Object> result = dispatcher.runSync("createQuestion", context);
    	}catch(Exception e) {
    		
    		}
    	return null;
    	}
    
    public Response getUserDetail(Map<String, Object> input) {
		  
		  LocalDispatcher dispatcher = getDispatcher(servletContext);
		  if(dispatcher==null) {
			  Response.status(500).entity(Map.of("error","dispatcher is null")).build();
		  }
		  try {
			Map<String,Object> result=dispatcher.runSync("examcreate", input);
			if("created successfully".equals(result.get("message"))) {
				return Response.ok(Map.of("status","success",
										  "message","createdsuccesfully"
								  )).build();
			}
			else {
				return Response.status(500).entity(Map.of("error","not created")).build();
			}
		} catch (GenericServiceException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return Response.status(500).entity(Map.of("error",e.getMessage())).build();
			
		}
		  
	  }
}
