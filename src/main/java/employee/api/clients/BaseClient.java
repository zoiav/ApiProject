package employee.api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {
    private RequestSpecification reqSpec;

    protected BaseClient(String baseUrl, String endpoint) {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath(endpoint)
                .setContentType(ContentType.JSON)
                .build();
    }

    public ValidatableResponse post(Object object) {
        return given()
                    .spec(reqSpec)
                    .body(object)
                .when()
                    .post()
                .then();
    }

    public ValidatableResponse postList(Object[] object, String list) {
        return given()
                    .spec(reqSpec)
                    .body(object)
                .when()
                    .post(list)
                .then();
    }

    public ValidatableResponse put(Object object) {
        return given()
                    .spec(reqSpec)
                    .body(object)
                .when()
                    .put()
                .then();
    }

    public ValidatableResponse patch(Object object) {
        return given()
                    .spec(reqSpec)
                    .body(object)
                .when()
                    .patch()
                .then();
    }

    public ValidatableResponse get(String id) {
        return given()
                    .spec(reqSpec)
                .when()
                    .get(id)
                .then();
    }

    public ValidatableResponse delete(String id) {
        return given()
                    .spec(reqSpec)
                .when()
                    .delete(id)
                .then();
    }
}