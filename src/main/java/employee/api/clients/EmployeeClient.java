package employee.api.clients;

import employee.dto.Employee;
import io.restassured.response.ValidatableResponse;

import java.io.IOException;

import static employee.api.config.Configuration.*;

public class EmployeeClient extends BaseClient {

    public EmployeeClient() throws IOException {
        super(getHost(), getEmployeeEndpoint());
    }

    public ValidatableResponse postEmployee(Employee employee) {
        return post(employee);
    }

    public ValidatableResponse postEmployeeList(Employee[] employees) {
        return super.postList(employees, "list");
    }

    public ValidatableResponse putEmployee(Employee employee) {
        return put(employee);
    }

    public ValidatableResponse patchEmployee(Employee employee) {
        return patch(employee);
    }

    public ValidatableResponse getEmployee(String id) {
        return get(id);
    }

    public ValidatableResponse deleteEmployee(String id) {
        return delete(id);
    }
}
