package models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostResponse {
    private String id;
    private String title;
    private String content;
    private boolean published;

    @JsonProperty("author_id")
    private String authorId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
