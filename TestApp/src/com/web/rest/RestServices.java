/**
 *  NAME :
 * 	 LrgRestServices
 * 
 * FUNCTION :
 * 	 Initializing Jersey Resource config
 * 
 *  @author kangverm
 */
package com.web.rest;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
@ApplicationPath("restservices")
public class RestServices extends ResourceConfig {
    public RestServices() {
        packages("com.fasterxml.jackson.jaxrs.json");
        packages("com.web.rest");
    }
}