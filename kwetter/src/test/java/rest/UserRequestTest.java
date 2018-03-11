package rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserRequestTest {
    @Test
    public void whenValidUsernameReturnUser() {
        given().when().get("http://www.google.com").then().statusCode(200);
    }
}
