package employee.api.factory;


import com.fasterxml.jackson.databind.ObjectMapper;
import employee.dto.Employee;
import employee.dto.InvalidEmployee;

import java.io.File;
import java.io.IOException;

public class EmployeeRequestFactory {

    public static Employee createEmployeeRequest() throws IOException {
        return new ObjectMapper()
                .readValue(new File("src/test/resources/templates/gcp/post_employee/request.json"),
                        Employee.class);
    }

    public static Employee[] createEmployeeListRequest() throws IOException {
        return new ObjectMapper()
                .readValue(new File("src/test/resources/templates/gcp/post_employees/request.json"),
                        Employee[].class);
    }

    public static InvalidEmployee createInvalidEmployeeRequest() throws IOException {
        return new ObjectMapper()
                .readValue(new File("src/test/resources/templates/gcp/post_employee/invalid_request.json"),
                        InvalidEmployee.class);
    }
}