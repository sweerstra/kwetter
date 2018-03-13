package rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class UserRestIT {
    private int OK = 200;
    private int BAD_REQUEST = 400;
    private int UNAUTHORIZED = 401;
    private int NOT_FOUND = 404;
    private int METHOD_NOT_ALLOWED = 405;

    @Test
    public void getUserWithEmptyUsernameShouldReturnMethodNotAllowed() {
        given()
                .when().get("/kwetter/api/user/")
                .then().statusCode(METHOD_NOT_ALLOWED);
    }

    @Test
    public void addUserWithValidRequestBodyShouldReturnOkWithId() {
        String json = "{ \"username\": \"testusername\", \"password\": \"password\" }";
        given().contentType("application/json").body(json)
                .when().post("/kwetter/api/user").peek()
                .then().statusCode(OK)
                .body("$", hasKey("id"));
    }

    @Test
    public void addUserWithEmptyRequestBodyShouldReturnNotFound() {
        given().contentType("application/json").body("{}")
                .when().post("/kwetter/api/user").peek()
                .then().statusCode(NOT_FOUND);
    }

    @Test
    public void editUserWithValidRequestBodyShouldReturnOkWithSameProperties() {
        String json = "{ \"username\": \"testusername\", \"bio\": \"My new bio\" }";
        given().contentType("application/json").body(json)
                .when().put("/kwetter/api/user").peek()
                .then().statusCode(OK)
                .body("bio", equalTo("My new bio"));
    }

    @Test
    public void editUserWithUnknownUsernameShouldReturnNotFound() {
        String json = "{ \"username\": \"unknown\", \"bio\": \"My new bio\" }";
        given().contentType("application/json").body(json)
                .when().put("/kwetter/api/user").peek()
                .then().statusCode(NOT_FOUND);
    }

    @Test
    public void authenticateResponseShouldReturnTrueIfPasswordCorrect() {
        String json = "{ \"username\": \"testusername\", \"password\": \"password\" }";
        given().contentType("application/json").body(json)
                .when().post("/kwetter/api/user/auth").peek()
                .then().statusCode(OK)
                .body("response", equalTo(true),
                        "message", equalTo("authenticated"));
    }

    @Test
    public void authenticateResponseShouldReturnFalseIfPasswordIncorrect() {
        String json = "{ \"username\": \"unknown\", \"password\": \"unknown\" }";
        given().contentType("application/json").body(json)
                .when().post("/kwetter/api/user/auth").peek()
                .then().statusCode(UNAUTHORIZED);
    }

    @Test
    public void followingResponseShouldReturnTrueIfUsersExist() {
        given()
                .when().post("/kwetter/api/user/1/follow/2").peek()
                .then().statusCode(OK)
                .body("response", equalTo(true));
    }

    @Test
    public void followingResponseShouldReturnFalseIfUsersDontExist() {
        given()
                .when().post("/kwetter/api/user/1/follow/2").peek()
                .then().statusCode(OK)
                .body("response", equalTo(false));
    }
}
