package employee;

import employee.api.clients.EmployeeClient;
import employee.dto.Employee;
import org.junit.After;
import org.junit.Before;

import static employee.api.factory.EmployeeRequestFactory.*;

public class BaseTest {
    Employee[] employees;
    Employee employee;
    String employeeId;
    EmployeeClient employeeClient;

    @Before
    public void setUp() throws Exception {
        employeeClient = new EmployeeClient();
        employees = createEmployeeListRequest();
        employee = createEmployeeRequest();
        employeeId = String.valueOf(employee.getId());
    }

    @After
    public void tearDown() {
        employeeClient.deleteEmployee("");
    }
}
