package models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    @JsonProperty("magic_link_sent")
    private boolean magicLinkSent;

    @JsonProperty("session_token")
    private String sessionToken;

    @JsonProperty("expires_at")
    private String expiresAt;

    @JsonProperty("user_id")
    private String userId;
}
