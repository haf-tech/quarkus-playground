package com.haddouti.playground.asset.boundary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AssetResourceTest {

	@Test
	public void testHelloEndpoint() {
		given().when().get("/v1/assets").then().statusCode(200);
	}

}