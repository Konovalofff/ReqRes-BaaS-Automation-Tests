package api;

import config.ApiConfig;
import io.restassured.response.Response;
import models.request.AuthRequest;

import java.util.HashMap;
import java.util.Map;

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
        // Создаём тело запроса с токеном
        Map<String, String> body = new HashMap<>();
        body.put("token", verificationToken); // Предполагаем, что поле называется "token"

        return given()
                .spec(ApiConfig.getPublicSpec()) // Оставляем, но он добавит только baseURL и contentType
                .body(body) // Добавляем тело запроса
                .when()
                .post("/app-users/verify"); // Меняем метод на POST и путь
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
