package api;

import config.ApiConfig;
import io.restassured.response.Response;
import models.request.CreatePostRequest;

import static io.restassured.RestAssured.given;

public class PostApiClient {
    public Response createPost(CreatePostRequest request, String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .body(request)
                .when()
                .post("/posts");
    }

    public Response getPost(String postId, String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .when()
                .get("/posts/" + postId);
    }

    public Response getMyPosts(String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .when()
                .get("/posts/me");
    }

    public Response updatePost(String postId, CreatePostRequest request, String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .body(request)
                .when()
                .put("/posts/" + postId);
    }

    public Response deletePost(String postId, String sessionToken) {
        return given()
                .spec(ApiConfig.getAuthSpec(sessionToken))
                .when()
                .delete("/posts/" + postId);
    }

    public Response getPublicPosts() {
        return given()
                .spec(ApiConfig.getPublicSpec())
                .when()
                .get("/posts/public");
    }
}
