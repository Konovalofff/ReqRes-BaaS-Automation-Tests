package models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {
    private String error;
    private String message;
    private String hint;

    @JsonProperty("docs_url")
    private String docsUrl;

    @JsonProperty("status_code")
    private Integer statusCode;
}
