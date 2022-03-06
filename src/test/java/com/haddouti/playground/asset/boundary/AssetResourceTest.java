package com.haddouti.playground.asset.boundary;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.oidc.server.OidcWiremockTestResource;

@QuarkusTest
@QuarkusTestResource(OidcWiremockTestResource.class)
public class AssetResourceTest {

	@Test
	public void testHelloEndpoint() {
		given().when().get("/v1/assets").then().statusCode(200);
	}

}