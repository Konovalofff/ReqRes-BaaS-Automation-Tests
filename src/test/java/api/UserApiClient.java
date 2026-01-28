package api;

import config.ApiConfig;
import io.restassured.response.Response;
import models.request.CreateUserRequest;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    public Response createUser(CreateUserRequest request) {
        return given()
                .spec(ApiConfig.getBaseSpec())
                .body(request)
                .when()
                .post("/users");
    }

    public Response getUser(String userId, String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .when()
                .get("/users/" + userId);
    }

    public Response getCurrentUser(String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .when()
                .get("/users/me");
    }

    public Response updateUser(String userId, CreateUserRequest request, String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .body(request)
                .when()
                .put("/users/" + userId);
    }

    public Response deleteUser(String userId) {
        return given()
                .spec(ApiConfig.getBaseSpec())
                .when()
                .delete("/users/" + userId);
    }

    public Response getUsers(int page, int limit, String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .queryParam("page", page)
                .queryParam("limit", limit)
                .when()
                .get("/users");
    }
}
