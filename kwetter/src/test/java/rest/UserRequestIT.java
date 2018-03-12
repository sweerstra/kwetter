package rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserRequestIT {
    @Test
    public void whenValidUsernameReturnUser() {
        given().when().get("http://www.google.com").then().statusCode(200);
    }
}
