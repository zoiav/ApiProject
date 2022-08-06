package employee;

import employee.dto.Employee;
import org.junit.*;

import java.io.IOException;

import static employee.api.factory.EmployeeResponseFactory.*;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class E2EPositiveTest extends BaseTest {

    @Test
    public void createEmployeeList() {
        Employee [] responseEmployees = employeeClient.postEmployeeList(employees)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                .extract().response().as(Employee[].class);

        assertThat(responseEmployees, equalTo(employees));
    }

    @Test
    public void createEmployee() throws IOException {
        employeeClient.postEmployee(employee)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo(positiveEmployeeResponse("post", employeeId)));

        Employee employeeResponse = employeeClient.getEmployee(employeeId)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                .extract().response().as(Employee.class);

        assertThat(employeeResponse, equalTo(getEmployeeResponse()));
    }

    @Test
    public void getAllEmployees() throws IOException {
        employeeClient.postEmployeeList(employees);

        Employee [] responseEmployees = employeeClient.getEmployee("")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                .extract().response().as(Employee[].class);

        assertThat(responseEmployees, equalTo(getEmployeeListResponse()));
    }

    @Test
    public void getEmployeeById() throws IOException {
        employeeClient.postEmployeeList(employees);

        Employee responseEmployee = employeeClient.getEmployee("105")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                .extract().response().as(Employee.class);

        assertThat(asList(getEmployeeListResponse()).contains(responseEmployee), equalTo(true));
    }

    @Test
    public void updateEmployee() throws IOException {
        employeeClient.postEmployee(employee);

        String newName = "Alex";
        employee.setName(newName);

        employeeClient.putEmployee(employee)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo(positiveEmployeeResponse("put", employeeId)));

        employeeClient.get(employeeId)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .body("name", equalTo(newName));
    }

    @Test
    public void partiallyUpdateEmployee() throws IOException {
        employeeClient.postEmployee(employee);

        String newPassportNumber = "AL128756";
        employee.setPassportNumber(newPassportNumber);

        employeeClient.patchEmployee(employee)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo(positiveEmployeeResponse("patch", employeeId)));

        employeeClient.get(employeeId)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .body("passportNumber", equalTo(newPassportNumber));
    }

    @Test
    public void deleteEmployeeById() throws IOException {
        employeeClient.postEmployee(employee);

        employeeClient.deleteEmployee(employeeId)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo(positiveEmployeeResponse("delete", employeeId)));
    }

    @Test
    public void deleteAllEmployees() throws IOException {
        employeeClient.postEmployeeList(employees);

        employeeClient.deleteEmployee("")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("text/plain;charset=UTF-8"))
                    .body(equalTo(deleteAllEmployeesResponse()));

        employeeClient.getEmployee("")
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                    .body("size()", equalTo(0));
    }
}
