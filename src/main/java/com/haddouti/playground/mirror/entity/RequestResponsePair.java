package com.haddouti.playground.mirror.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Holding a request and response pair
 * 
 * Holds the request-response pair, where headers and body are separated.
 * Body will be truncated, if the content is to long.
 */
@Entity
public class RequestResponsePair extends PanacheEntity {
    
    public Date createdAt;
    @Column(length = 1000)
    public String requestHeaders;
    @Lob
    public String requestBody;
    @Column(length = 1000)
    public String responseHeaders;
    @Lob
    public String responseBody;

    @Override
    public String toString() {
        return "RequestResponsePair [createdAt=" + createdAt + ", requestBody=" + requestBody + ", requestHeaders="
                + requestHeaders + ", responseBody=" + responseBody + ", responseHeaders=" + responseHeaders + "]";
    }

    
}
