package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiConfig {

    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
            .setBaseUri(TestConfig.getApiBaseUrl())
            .setContentType(ContentType.JSON)
            .addHeader("X-Api-Key", TestConfig.getApiKey())
            .addHeader("User-Agent", "ReqRes-BaaS-Tests/1.0")
            .build();
    }

    public static RequestSpecification getAuthSpec(String sessionToken) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpec())
                .addHeader("Authorization", "Bearer" + sessionToken)
                .build();
    }

    public static RequestSpecification getPublicSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(TestConfig.getApiBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("X-Api-Key", TestConfig.getPublicKey())
                .build();
    }

    public static String getPublicKey() {
        return TestConfig.getPublicKey();
    }
}
