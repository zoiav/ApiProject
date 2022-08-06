package employee.api.config;

import java.io.IOException;

public class Configuration {

    public static String getHost() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        return System.getProperty("host");
    }

    public static String getEmployeeEndpoint() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        return System.getProperty("employee.endpoint");
    }
}
