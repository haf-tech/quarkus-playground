package com.haddouti.playground;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "greeting")
public interface GreetingConfig {

	@ConfigProperty(name = "message")
	String message();

}