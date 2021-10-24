package com.haddouti.playground.importantevent.boundary;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.SseElementType;

/**
 * Simple REST endpoint consuming Kafka topic and exposing the content back
 * using SSE.
 * 
 */
@Path("/v1/importantevents")
public class ImportantEventResource {

	private static final Logger LOG = Logger.getLogger(ImportantEventResource.class);

	ConcurrentLinkedQueue<Object> events = new ConcurrentLinkedQueue<>();

	@Incoming("importantevents")
	public void consume(ConsumerRecord<String, ?> record) {

		// Can be `null` if the incoming record has no key
		String key = record.key();
		Object value = record.value();
		String topic = record.topic();
		int partition = record.partition();
		// ...

		LOG.debugf("consume(): topic: %s, key: %s, partition: %d", topic, key, partition);
		LOG.infof("consume(): value=%s", value);

		events.add(value);
	}

	@GET
	@Path("/")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@SseElementType("text/plain")
	public String stream() {
		final Object event = events.poll();

		return (event != null) ? event.toString() : "";
	}
}
