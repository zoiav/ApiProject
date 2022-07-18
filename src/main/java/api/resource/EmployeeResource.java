package api.resource;

import api.dto.Employee;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class EmployeeResource {

    private static final String BASE_URL = "http://localhost:9090/";

    public static ValidatableResponse postEmployeeRequest(Employee employee) {
        return given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(employee)
                .when()
                    .post("employee/")
                .then();
    }

    public static ValidatableResponse postEmployeeListRequest(Employee[] employees) {
        return given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(employees)
                .when()
                    .post("employee/list")
                .then();
    }

    public static ValidatableResponse putEmployeeRequest(Employee employee) {
        return given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(employee)
                .when()
                    .put("employee/")
                .then();
    }

    public static ValidatableResponse patchEmployeeRequest(Employee employee) {
        return given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(employee)
                .when()
                    .patch("employee/")
                .then();
    }

    public static ValidatableResponse getEmployeeRequest(String id) {
        return given()
                    .baseUri(BASE_URL)
                    .pathParam("id", id)
                .when()
                    .get("employee/{id}")
                .then();
    }

    public static ValidatableResponse deleteEmployeeRequest(String id) {
        return given()
                    .baseUri(BASE_URL)
                    .pathParam("id", id)
                .when()
                    .delete("employee/{id}")
                .then();
    }
}
