package com.haddouti.playground.asset.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Asset representation
 *
 */
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {

	public Long id;
	public String name;

	public String description;
	public Date createdAd;
	public Date modifiedAt;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Asset [id=").append(id).append(", name=").append(name).append(", description=")
				.append(description).append(", createdAd=").append(createdAd).append(", modifiedAt=").append(modifiedAt)
				.append("]");
		return builder.toString();
	}

}
