package tests.auth;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.base.BaseApiTest;

import java.util.List;

import static config.ApiSpecs.responseOk;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("API Health Check")
@Feature("Проверка доступности сервисов")
@Tag("health")
@Tag("smoke")
public class HealthCheckTest extends BaseApiTest {

    @Test
    @DisplayName("Проверка доступности публичного API")
    @Description("Тест проверяет, что публичное API отвечает на health check запрос")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Базовые проверки API")
    void publicApiHealthCheck() {
        Response response = given()
                .spec(config.ApiConfig.getPublicSpec())
                .when()
                .get("collections/healthchecks/records")
                .then()
                .spec(responseOk())
                .body("data[0].data.status", equalTo("ok"))
                .extract()
                .response();

        Allure.step("Проверяем структуру ответа", () -> {
            List data = response.path("data");
            int total = response.path("meta.total");

            Allure.addAttachment("Количество записей", String.valueOf(data != null ? data.size() : 0));
            Allure.addAttachment("Всего записей в коллекции", String.valueOf(total));

            if (data != null && !data.isEmpty()) {
                String status = response.path("data[0].data.status");
                String service = response.path("data[0].data.service");
                Allure.addAttachment("Статус сервиса", status + " (" + service + ")");
            }
        });
    }

    @Test
    @DisplayName("Проверка доступности аутентифицированного API")
    @Description("Тест проверяет, что аутентифицированные endpoint'ы доступны")
    @Severity(SeverityLevel.CRITICAL)
    void authenticatedApiHealthCheck() {

        String testToken = "test_token_for_health_check";

        given()
                .spec(config.ApiConfig.getAuthSpec(testToken))
                .when()
                .get("/auth/health")
                .then()
                .spec(responseOk())
                .body("authenticated", equalTo(true))
                .body("service", equalTo("auth"));
    }

    @Test
    @DisplayName("Проверка доступности админского API")
    @Description("Тест проверяет доступность endpoint'ов для администраторов")
    @Severity(SeverityLevel.NORMAL)
    void adminApiHealthCheck() {
        given()
                .spec(config.ApiConfig.getBaseSpec())
                .when()
                .get("/admin/health")
                .then()
                .spec(responseOk())
                .body("service", equalTo("admin"))
                .body("privileged", equalTo(true));
    }
}
