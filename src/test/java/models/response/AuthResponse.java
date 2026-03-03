package models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    // Поля для ответа sendMagicLink
    private String id;
    private String email;
    private String redirectUrl;
    private String createdAt;

    // Поля для ответа verifySession
    @JsonProperty("session_token")
    private String sessionToken;

    @JsonProperty("app_user")
    private AppUser appUser; // Вложенный объект

    @Data
    public static class AppUser {
        private String id;
        private String email;
    }
}
