import api.dto.Employee;

import org.junit.FixMethodOrder;
import org.junit.Test;
import api.resource.EmployeeResource;
import org.junit.runners.MethodSorters;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.JVM)
public class ApiTest {

    @Test
    public void updateEmployeeNegative() {
        Employee employee = new Employee();
        employee.setId(108);
        employee.setName("Alex");
        employee.setPassportNumber("BR741852");

        EmployeeResource.putEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_BAD_REQUEST)
                    .body("message", containsString("Validation failed"));

        employee.setEducation("College");

        EmployeeResource.putEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                    .body("message", equalTo("Employee with such id = 108 doesn't exist"));

        EmployeeResource.patchEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                    .body("message", equalTo("Employee with such id = 108 doesn't exist"));
    }

    @Test
    public void createEmployeeList() {
        Employee [] employees = {
                new Employee(
                        104,
                        "Max",
                        "MX456798",
                        "High"),
                new Employee(
                        105,
                        "John",
                        "JN789123",
                        "School"),
                new Employee(
                        106,
                        "Alla",
                        "AL456123",
                        "School")
        };

        Employee [] responseEmployees = EmployeeResource.postEmployeeListRequest(employees)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                .extract().response().as(Employee[].class);

        assertThat(employees, equalTo(responseEmployees));
    }

    @Test
    public void createEmployee() {
        Employee employee = new Employee(
                108,
                "Bred",
                "BR741852",
                "College");

       EmployeeResource.postEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo("Employee with id = 108 has been added!"));
    }

    @Test
    public void createEmployeeNegative() {
        Employee employee = new Employee(
                108,
                "Bred",
                "BR741852",
                "College");

        EmployeeResource.postEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                    .body("message", equalTo("Employee with such id = 108 already exists"));

        employee.setId(109);
        employee.setPassportNumber(null);

        EmployeeResource.postEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_BAD_REQUEST)
                    .body("message", containsString("Validation failed"));
    }

    @Test
    public void updateEmployee() {
        Employee employee = new Employee(
                108,
                "Alex",
                "AL741852",
                "High");

        EmployeeResource.putEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo("Employee with id = 108 has been updated!"));
    }

    @Test
    public void partiallyUpdateEmployee() {
        Employee employee = new Employee(
                108,
                "Alex",
                "AL128756",
                "High");

        EmployeeResource.patchEmployeeRequest(employee)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo("Employee with id = 108 has been partially updated!"));
    }

    @Test
    public void getEmployee() {
        EmployeeResource.getEmployeeRequest("")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                    .body("size()", equalTo(4))
                    .body("id", everyItem(notNullValue()))
                    .body("name", everyItem(notNullValue()))
                    .body("passportNumber", everyItem(notNullValue()))
                    .body("education", everyItem(notNullValue()))
                    .body("id", contains(104, 105, 106, 108));

       EmployeeResource.getEmployeeRequest("105")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                    .body("id", equalTo(105))
                    .body("name", equalTo("John"))
                    .body("passportNumber", equalTo("JN789123"))
                    .body("education", equalTo("School"));


        EmployeeResource.getEmployeeRequest("108")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                    .body("id", equalTo(108))
                    .body("name", equalTo("Alex"))
                    .body("passportNumber", equalTo("AL128756"))
                    .body("education", equalTo("High"));
    }

    @Test
    public void deleteEmployee() {
        EmployeeResource.deleteEmployeeRequest("108")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo("Employee with id = 108 has been successfully deleted!"));

        EmployeeResource.deleteEmployeeRequest("")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo("All employees have been successfully deleted!"));
    }

    @Test
    public void deleteEmployeeNegative() {
        EmployeeResource.deleteEmployeeRequest("108")
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                    .body("message", equalTo("Employee with such id = 108 not found"));
    }

    @Test
    public void getEmployeeNegative() {
        EmployeeResource.getEmployeeRequest("105")
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                    .body("message", equalTo("Employee with such id = 105 not found"));

        EmployeeResource.getEmployeeRequest("")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                    .body("size()", equalTo(0));
    }
}
