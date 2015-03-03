package com.bta.twitter.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@ApplicationPath("api")
public class RestConfig extends ResourceConfig {

	public RestConfig() {
		//packages("com.bta.twitter.rest.controler");
		packages("com.bta.twitter");
		register(JacksonJsonProvider.class);
	}
}