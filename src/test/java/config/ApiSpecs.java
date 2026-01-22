package config;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

public class ApiSpecs {

    public static ResponseSpecification responseOk() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseCreated() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseNoContent() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseBadRequest() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseUnauthorized() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseForbidden() {
        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseNotFound() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseSuccess() {
        return new ResponseSpecBuilder()
                .expectStatusCode(org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.is(200),
                        org.hamcrest.Matchers.is(201),
                        org.hamcrest.Matchers.is(204)
                ))
                .log(LogDetail.ALL)
                .build();
    }
}
