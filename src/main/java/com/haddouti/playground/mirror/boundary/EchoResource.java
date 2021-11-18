package com.haddouti.playground.mirror.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;


import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;

import io.quarkus.security.identity.SecurityIdentity;

@Path("v1/mirror")
public class EchoResource {

	private static final Logger LOG = Logger.getLogger(EchoResource.class);

	@Path("")
	@GET
	@NoCache
	public String echo(@QueryParam("e") String txt) {
		LOG.info("echo()");
		
		return "Echo: " + txt;
	}

}
