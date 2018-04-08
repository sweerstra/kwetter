package rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class KweetRestIT {
    private int OK = 200;
    private int BAD_REQUEST = 400;
    private int UNAUTHORIZED = 401;
    private int NOT_FOUND = 404;
    private int METHOD_NOT_ALLOWED = 405;

    @Test
    public void postKweetWithValidRequestBodyShouldReturnOk() {
        String json = "{ \"text\": \"kweet message\", \"user\": { \"id\": \"1\" } }";
        given().contentType("application/json").body(json)
                .when().post("/kwetter/api/kweet")
                .then().statusCode(OK)
                .body("id", not(0), "text", equalTo("kweet message"));
    }

    @Test
    public void postKweetWithEmptyTextShouldReturnBadRequest() {
        String json = "{ \"text\": \"\", \"user\": { \"id\": \"1\" } }";
        given().contentType("application/json").body(json)
                .when().post("/kwetter/api/kweet")
                .then().statusCode(BAD_REQUEST);
    }

    @Test
    public void getKweetShouldReturnOk() {
        given().contentType("application/json")
                .when().get("/kwetter/api/kweet/4")
                .then().statusCode(OK)
                .body("$", hasKey("text"));
    }

    @Test
    public void getUnknownKweetShouldReturnNotFound() {
        given().contentType("application/json")
                .when().get("/kwetter/api/kweet/100")
                .then().statusCode(NOT_FOUND);
    }

    @Test
    public void editKweetWithValidRequestBodyShouldReturnOk() {
        String json = "{ \"id\": \"4\", \"text\": \"Excuses #respect\" }";
        given().contentType("application/json").body(json)
                .when().put("/kwetter/api/kweet")
                .then().statusCode(OK)
                .body("text", equalTo("Excuses #respect"));
    }

    @Test
    public void editKweetWithUnknownUserShouldReturnNotFound() {
        String json = "{ \"text\": \"Excuses #respect\", \"id\": \"100\" }";
        given().contentType("application/json").body(json)
                .when().put("/kwetter/api/kweet")
                .then().statusCode(NOT_FOUND);
    }

    @Test
    public void searchKweetsShouldResultReturnArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/search/stad")
                .then().statusCode(OK)
                .body("$", not(hasSize(0)));
    }

    @Test
    public void searchKweetsWithEmptyResultShouldReturnEmptyArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/search/bericht")
                .then().statusCode(OK)
                .body("$", hasSize(0));
    }

    @Test
    public void getKweetsOfValidUserShouldReturnArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/user/user1")
                .then().statusCode(OK)
                .body("$", not(hasSize(0)));
    }

    @Test
    public void getKweetsOfInvalidUserShouldReturnNotFound() {
        given()
                .when().get("/kwetter/api/kweet/user/100")
                .then().statusCode(NOT_FOUND);
    }

    @Test
    public void getKweetsByTrendShouldReturnArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/trend/heftig")
                .then().statusCode(OK)
                .body("$", not(hasSize(0)));
    }

    @Test
    public void getKweetsByUnknownTrendShouldReturnEmptyArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/trend/unknown")
                .then().statusCode(OK)
                .body("$", hasSize(0));
    }

    @Test
    public void getKweetsByMentionShouldReturnArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/mention/niffo")
                .then().statusCode(OK)
                .body("$", not(hasSize(0)));
    }

    @Test
    public void getKweetsByUnknownMentionShouldReturnEmptyArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/mention/unknown")
                .then().statusCode(OK)
                .body("$", hasSize(0));
    }

    @Test
    public void getCurrentTrendsShouldContainTrendStringReturnArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/trends")
                .then().statusCode(OK)
                .body("$", hasItem("#heftig"));
    }

    @Test
    public void getTimelineShouldReturnArrayAndOk() {
        given()
                .when().get("/kwetter/api/kweet/user/user1")
                .then().statusCode(OK)
                .body("$", not(hasSize(0)));
    }

    @Test
    public void getTimelineWithUnknownUserShouldReturnNotFound() {
        given()
                .when().get("/kwetter/api/kweet/user/100")
                .then().statusCode(NOT_FOUND);
    }

    @Test
    public void likeKweetShouldReturnTrue() {
        String json = "{ \"id\": \"4\" }";
        given().contentType("application/json").body(json)
                .when().post("/kwetter/api/kweet/like/1")
                .then().statusCode(OK)
                .body("response", equalTo(true));
    }

    @Test
    public void likeKweetWithUnknownUserShouldReturnFalse() {
        String json = "{ \"id\": \"4\" }";
        given().contentType("application/json").body(json)
                .when().post("/kwetter/api/kweet/like/100")
                .then().statusCode(OK)
                .body("response", equalTo(false));
    }
}
