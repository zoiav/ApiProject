package employee;

import employee.dto.EmployeeResponse;
import org.junit.Test;

import java.io.IOException;

import static employee.api.factory.EmployeeResponseFactory.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class E2ENegativeTest extends BaseTest{

    @Test
    public void updateEmployeeNegative() throws IOException {
        employee.setName("Alex");

        EmployeeResponse employeeResponse = employeeClient.putEmployee(employee)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                .extract().response().as(EmployeeResponse.class);

        assertThat(employeeResponse, equalTo(negativeEmployeeResponse("put", employeeId)));
    }

    @Test
    public void partiallyUpdateEmployeeNegative() throws IOException {
        employee.setName("Alex");

        EmployeeResponse employeeResponse = employeeClient.patch(employee)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                .extract().response().as(EmployeeResponse.class);

        assertThat(employeeResponse, equalTo(negativeEmployeeResponse("patch", employeeId)));
    }

    @Test
    public void createEmployeeNegative() throws IOException {
                employeeClient.postEmployee(employee);
        EmployeeResponse employeeResponse = employeeClient.postEmployee(employee)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                .extract().response().as(EmployeeResponse.class);

        assertThat(employeeResponse, equalTo(negativeEmployeeResponse("post", employeeId)));
    }

    @Test
    public void deleteEmployeeNegative() throws IOException {
        String id = "107";
        EmployeeResponse employeeResponse = employeeClient.deleteEmployee(id)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                .extract().response().as(EmployeeResponse.class);

        assertThat(employeeResponse, equalTo(negativeEmployeeResponse("delete", id)));
    }

    @Test
    public void getEmployeeNegative() throws IOException {
        String id = "107";
        EmployeeResponse employeeResponse = employeeClient.getEmployee(id)
                .assertThat()
                    .statusCode(HTTP_INTERNAL_ERROR)
                .extract().response().as(EmployeeResponse.class);

        assertThat(employeeResponse, equalTo(negativeEmployeeResponse("get", id)));
    }
}
