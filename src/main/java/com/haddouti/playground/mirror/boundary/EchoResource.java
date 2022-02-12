package com.haddouti.playground.mirror.boundary;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.haddouti.playground.mirror.control.RequestResponseManager;
import com.haddouti.playground.mirror.entity.RequestResponsePair;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.vertx.core.eventbus.EventBus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;

/**
 * Base interface to mirror/echo requests, with additional info
 */
@Path("v1/mirror")
public class EchoResource {

	private static final Logger LOG = Logger.getLogger(EchoResource.class);

	static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	private final MeterRegistry registry;
	private final Counter requestCounter;

	@Inject 
	EventBus eventBus;
	
	public EchoResource(MeterRegistry mr) {
		registry = mr;

		requestCounter = registry.counter("quarkus_playground_echo_requests_count", "endpoint_version", "v1");
	}

	/**
	 * Echos the given request with all request headers.
	 * 
	 * @param txt
	 * @param httpHeaders
	 * @return
	 */
	@Transactional
	@Path("")
	@GET
	@NoCache
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
	public String echo(@QueryParam("e") String txt, @Context HttpHeaders httpHeaders) {
		LOG.info("echo()");
		requestCounter.increment();

		LocalDateTime currentDateTime = LocalDateTime.now();
		String formattedDateTime = currentDateTime.format(formatter);
		//return String.format("Echo <%s>: %s", formattedDateTime, txt);

		StringBuffer sb = new StringBuffer("");
		sb.append(String.format("Echo:\n<%s>: %s\n", formattedDateTime, txt));

		for(String headerKey: httpHeaders.getRequestHeaders().keySet()) {

			sb.append(String.format("%s=%s\n", headerKey, httpHeaders.getRequestHeader(headerKey).toString()));
		}

		// fire and forget this request-response pair
		fireEventForRequestResponse(httpHeaders.getRequestHeaders(), null, null, null);

		return sb.toString();
	}

	@GET
	@Path("/list/pairs")
	@NoCache
	public String list() {

		//List<RequestResponsePair> pairs = RequestResponsePair.findAll().list();
		List<RequestResponsePair> pairs = RequestResponsePair.list("order by createdAt desc");

		StringBuilder sb = new StringBuilder("");
		for(RequestResponsePair pair : pairs) {
			sb.append(String.format("%s<br />", pair));
		}

		return sb.toString();
	}

	private void fireEventForRequestResponse(MultivaluedMap<String, String> requestHeaders, String requestBody,
			MultivaluedMap<String, String> responseHeaders, String responseBody) {

		LOG.debug("fireEventForRequestResponse()");		

		StringBuffer sb = new StringBuffer("");
		for (String headerKey : requestHeaders.keySet()) {
			
			sb.append(String.format("%s=%s\n", headerKey, requestHeaders.get(headerKey).toString()));
		}
		
		RequestResponsePair pair = new RequestResponsePair();
		pair.createdAt = new Date();
		pair.requestHeaders = sb.toString();
		pair.requestBody = requestBody;
		pair.responseHeaders = null;
		pair.responseBody = responseBody;
		eventBus.publish(RequestResponseManager.EVENT_LOG_REQUEST_RESPONSE, pair);

		pair.persist();
	}

}
