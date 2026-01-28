package api;

import config.ApiConfig;
import io.restassured.response.Response;
import models.request.AuthRequest;

import static io.restassured.RestAssured.given;

public class AuthApiClient {
    public Response sendMagicLink(AuthRequest request) {
        return given()
                .spec(ApiConfig.getPublicSpec())
                .body(request)
                .when()
                .post("/auth/magic-link");
    }

    public Response verifySession(String verificationToken) {
        return given()
                .spec(ApiConfig.getPublicSpec())
                .queryParam("token", verificationToken)
                .when()
                .get("/auth/verify");
    }

    public Response refreshSession(String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .when()
                .post("/auth/refresh");
    }

    public Response logout(String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .when()
                .post("/auth/logout");
    }

    public Response healthCheck() {
        return given()
                .spec(ApiConfig.getPublicSpec())
                .when()
                .get("/auth/health");
    }
}
