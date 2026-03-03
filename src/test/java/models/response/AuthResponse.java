package models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    private String id;
    private String email;

    @JsonProperty("redirectUrl")
    private String redirectUrl;

    @JsonProperty("createdAt")
    private String createdAt;

    private String sessionToken;
    private String userId;
}
