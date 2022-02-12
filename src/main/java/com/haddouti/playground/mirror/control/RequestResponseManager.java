package com.haddouti.playground.mirror.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.haddouti.playground.mirror.entity.RequestResponsePair;

import org.jboss.logging.Logger;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.Vertx;

import static javax.transaction.Transactional.TxType;
/**
 * Manages the request-responses of the mirror context
 * 
 * 
 */
@ApplicationScoped
public class RequestResponseManager {
    
    private static final Logger LOG = Logger.getLogger(RequestResponseManager.class);

    public static String EVENT_LOG_REQUEST_RESPONSE = "mirror-log-req-res";

    @Inject
    Vertx vertx;

    @ConsumeEvent("mirror-log-req-res")
    //@Transactional(TxType.REQUIRES_NEW)
    public void consume(RequestResponsePair pair) {
        
        LOG.debugf("consume(): pair=%s", pair);
        
        RequestResponsePair newPair = new RequestResponsePair();
        newPair.createdAt = pair.createdAt;
        newPair.requestHeaders = pair.requestHeaders;
        newPair.requestBody = pair.requestBody;
        newPair.responseHeaders = pair.responseHeaders;
        newPair.responseBody = pair.responseBody;
        savePair(newPair);
    }
    
   
    public void savePair(RequestResponsePair pair) {

        LOG.debugf("savePair(): pair=%s", pair);

        /* H2 does not support reactive 
        vertx.executeBlocking((event) -> {

            pair.persist();
        });
        */
    }
}
