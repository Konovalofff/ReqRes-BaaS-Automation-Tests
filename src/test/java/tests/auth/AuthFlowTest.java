package tests.auth;

import api.AuthApiClient;
import io.qameta.allure.*;
import models.request.AuthRequest;
import models.response.AuthResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.base.BaseApiTest;

import static config.ApiSpecs.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Authentication")
@Feature("Magic Link Flow")
@Tag("auth")
@Tag("smoke")
public class AuthFlowTest extends BaseApiTest {

    private final AuthApiClient authApiClient = new AuthApiClient();

    @Test
    @DisplayName("Отправка magic link на email")
    @Description("Проверяет, что API принимает запрос на отправку magic link")
    @Severity(SeverityLevel.CRITICAL)
    void sendMagicLink() {
        String testEmail = "testuser_" + System.currentTimeMillis() + "@reqres.test";

        AuthRequest request = AuthRequest.builder()
                .email(testEmail)
                .redirectUrl("https://app.reqres.in/auth/callback")
                .build();

        Allure.step("Отправляем magic link на email: " + testEmail, () -> {
            var response = authApiClient.sendMagicLink(request);

            response.then()
                    .spec(responseCreated())
                    .body("magic_link_sent", equalTo(true));

            Allure.addAttachment("Email", testEmail);
            Allure.addAttachment("Результат", "Magic link отправлен");
        });
    }

    @Test
    @DisplayName("Получение session token после верификации")
    @Description("Проверяет получение session token по verification token")
    @Severity(SeverityLevel.BLOCKER)
    void verifySession() {

        String verificationToken = "test_verification_token_123";

        Allure.step("Верифицируем токен и получаем session token", () -> {
            var response = authApiClient.verifySession(verificationToken);

            AuthResponse authResponse = response.then()
                    .spec(responseOk())
                    .extract()
                    .as(AuthResponse.class);

            assertNotNull(authResponse.getSessionToken());
            assertNotNull(authResponse.getUserId());
            assertTrue(authResponse.isMagicLinkSent());

            Allure.addAttachment("Session Token", authResponse.getSessionToken());
            Allure.addAttachment("User ID", authResponse.getUserId());

            // Сохраняем в менеджер сессий
            sessionManager.saveSession("test@reqres.test", authResponse.getSessionToken());
        });
    }

    @Test
    @DisplayName("Полный цикл аутентификации")
    @Description("Magic link → verification → session token")
    @Severity(SeverityLevel.CRITICAL)
    void fullAuthenticationFlow() {
        String testEmail = "testuser_" + System.currentTimeMillis() + "@reqres.test";

        Allure.step("Шаг 1: Отправка magic link", () -> {
            AuthRequest request = AuthRequest.builder()
                    .email(testEmail)
                    .redirectUrl("https://app.reqres.in/auth/callback")
                    .build();

            var magicResponse = authApiClient.sendMagicLink(request);
            magicResponse.then().spec(responseOk());
        });

        Allure.step("Шаг 2: Получение verification token (эмуляция)", () -> {

            String verificationToken = "test_verification_token_" + testEmail.hashCode();
            Allure.addAttachment("Verification Token", verificationToken);

            var sessionResponse = authApiClient.verifySession(verificationToken);

            AuthResponse authResponse = sessionResponse.then()
                    .spec(responseOk())
                    .extract()
                    .as(AuthResponse.class);

            assertNotNull(authResponse.getSessionToken());

            Allure.addAttachment("Session Token", authResponse.getSessionToken());
            Allure.addAttachment("User ID", authResponse.getUserId());

            sessionManager.saveSession(testEmail, authResponse.getSessionToken());
        });

        assertTrue(sessionManager.hasSession(testEmail));
    }
}