package employee;

import employee.dto.Employee;
import employee.dto.InvalidEmployee;
import org.junit.Test;

import java.io.IOException;

import static employee.api.factory.EmployeeRequestFactory.*;
import static employee.api.factory.EmployeeResponseFactory.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class E2EValidationTest extends BaseTest {

    @Test
    public void updateEmployeeNullField() {
        employee.setName(null);

        employeeClient.putEmployee(employee)
                .assertThat()
                    .statusCode(HTTP_BAD_REQUEST)
                    .body("message", containsString("Validation failed"));
    }

    @Test
    public void createInvalidEmployee() throws IOException {
        InvalidEmployee invalidEmployee = createInvalidEmployeeRequest();
        String id = String.valueOf(invalidEmployee.getId());

        employeeClient.postEmployee(invalidEmployee)
                .assertThat()
                    .statusCode(HTTP_OK)
                .body(equalTo(positiveEmployeeResponse("post", id)));

        Employee employeeResponse = employeeClient.getEmployee(id)
                .assertThat()
                    .statusCode(HTTP_OK)
                    .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                .extract().response().as(Employee.class);

        assertThat(employeeResponse, equalTo(getEmployeeResponse()));
    }

    @Test
    public void createEmptyEmployee() {
        Employee employee = new Employee();

        employeeClient.postEmployee(employee)
                .assertThat()
                    .statusCode(HTTP_BAD_REQUEST)
                    .body("message", containsString("Validation failed"));
    }
}
