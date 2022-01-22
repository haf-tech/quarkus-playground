package com.haddouti.playground;

import java.util.Arrays;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import io.quarkus.runtime.configuration.ProfileManager;

/**
 * Contains custom configuration for the application
 */
@Singleton
public class CustomConfiguration {
    
    @ConfigProperty(name = "quarkus.application.version")
    String appVersion;

    /** 
     * Define common tags that apply globally 
     */
    @Produces
    @Singleton
    public MeterFilter configureAllRegistries() {
        return MeterFilter.commonTags(Arrays.asList(
                    Tag.of("env", ProfileManager.getActiveProfile()),
                    Tag.of("app_version", appVersion)));
    }
}
