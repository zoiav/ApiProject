package employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    String message;
    @JsonIgnore
    String stackTrace;

    public void setId(String id) {
        this.message = String.format(message, id);
    }
}
