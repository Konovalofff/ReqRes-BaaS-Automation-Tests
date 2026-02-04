package services;

import api.AuthApiClient;
import helpers.SessionManager;
import io.restassured.response.Response;
import models.request.AuthRequest;
import models.response.AuthResponse;

public class AuthService {
    private final AuthApiClient authApiClient = new AuthApiClient();
    private final SessionManager sessionManager = SessionManager.getInstance();

    public String authenticateUser(String email) {

        AuthRequest request = AuthRequest.builder()
                .email(email)
                .redirectUrl("https://app.reqres.in/auth/callback")
                .build();

        Response magicResponse = authApiClient.sendMagicLink(request);

        String verificationToken = "test_verification_token_" + email.hashCode();

        Response sessionResponse = authApiClient.verifySession(verificationToken);
        AuthResponse authResponse = sessionResponse.as(AuthResponse.class);

        sessionManager.saveSession(email, authResponse.getSessionToken());

        return authResponse.getSessionToken();
    }

    public boolean isSessionValid(String sessionToken) {
        try {
            Response response = authApiClient.refreshSession(sessionToken);
            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public void logout(String sessionToken) {
        authApiClient.logout(sessionToken);
        sessionManager.removeSession(sessionToken);
    }
}
