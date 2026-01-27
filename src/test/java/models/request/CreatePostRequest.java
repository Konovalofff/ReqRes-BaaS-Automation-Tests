package models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostRequest {
    private String title;
    private String content;
    private boolean published;
}
