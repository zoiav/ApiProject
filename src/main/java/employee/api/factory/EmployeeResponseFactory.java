package employee.api.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import employee.dto.Employee;
import employee.dto.EmployeeResponse;

import java.io.File;
import java.io.IOException;

public class EmployeeResponseFactory {

    public static String positiveEmployeeResponse(String method, String id) throws IOException {
        return String.format(new ObjectMapper()
                .readValue(new File
                        (String.format("src/test/resources/templates/gcp/%s_employee/response.text", method)),
                        String.class), id);
    }

    public static Employee[] createEmployeeListResponse() throws IOException {
        return new ObjectMapper()
                .readValue(new File("src/test/resources/templates/gcp/post_employees/response.json"),
                        Employee[].class);
    }

    public static Employee getEmployeeResponse() throws IOException {
        return new ObjectMapper()
                .readValue(new File("src/test/resources/templates/gcp/get_employee/response.json"),
                        Employee.class);
    }

    public static Employee[] getEmployeeListResponse() throws IOException {
        return new ObjectMapper()
                .readValue(new File("src/test/resources/templates/gcp/get_employees/response.json"),
                        Employee[].class);
    }

    public static String deleteAllEmployeesResponse() throws IOException {
        return new ObjectMapper()
                .readValue(new File("src/test/resources/templates/gcp/delete_employees/response.text"),
                        String.class);
    }

    public static EmployeeResponse negativeEmployeeResponse(String method, String id) throws IOException {
        EmployeeResponse employeeResponse = new ObjectMapper()
                .readValue(new File
                                (String.format("src/test/resources/templates/gcp/%s_employee/negative_response.json",
                                        method)),
                        EmployeeResponse.class);
        employeeResponse.setId(id);
        return employeeResponse;
    }
}
