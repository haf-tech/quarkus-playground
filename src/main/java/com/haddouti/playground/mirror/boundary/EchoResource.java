package com.haddouti.playground.mirror.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;

/**
 * Base interface to mirror/echo requests, with additional info
 */
@Path("v1/mirror")
public class EchoResource {

	private static final Logger LOG = Logger.getLogger(EchoResource.class);

	static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	/**
	 * Echos the given request with all request headers.
	 * 
	 * @param txt
	 * @param httpHeaders
	 * @return
	 */
	@Path("")
	@GET
	@NoCache
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
	public String echo(@QueryParam("e") String txt, @Context HttpHeaders httpHeaders) {
		LOG.info("echo()");
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		String formattedDateTime = currentDateTime.format(formatter);
		//return String.format("Echo <%s>: %s", formattedDateTime, txt);

		StringBuffer sb = new StringBuffer("");
		sb.append(String.format("Echo:\n<%s>: %s\n", formattedDateTime, txt));

		for(String headerKey: httpHeaders.getRequestHeaders().keySet()) {

			sb.append(String.format("%s=%s\n", headerKey, httpHeaders.getRequestHeader(headerKey).toString()));
		}

		return sb.toString();
	}

}
