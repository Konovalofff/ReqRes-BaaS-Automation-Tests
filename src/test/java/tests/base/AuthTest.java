package tests.base;

import org.junit.jupiter.api.BeforeEach;
import services.AuthService;

public class AuthTest extends BaseApiTest {
    protected AuthService authService = new AuthService();
    protected String sessionToken;
    protected String testUserEmail;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        testUserEmail = "testuser_" + System.currentTimeMillis() + "@reqres.test";

        System.out.println("Аутентификация пользователя: " + testUserEmail);

        sessionToken = "test_session_token_" + testUserEmail.hashCode();
        sessionManager.saveSession(testUserEmail, sessionToken);

        System.out.println("Токен сессии получен: " +
                sessionToken.substring(0, Math.min(15, sessionToken.length())) + "...");
    }
}
