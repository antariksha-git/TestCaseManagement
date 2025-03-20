package ai.zomind.testcasemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TestCaseRequestDto {

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;

    @Pattern(regexp = "PENDING|IN_PROGRESS|PASSED|FAILED", message = "Invalid status. Allowed values: PENDING, IN_PROGRESS, PASSED, FAILED")
    private String status;

    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Invalid priority. Allowed values: LOW, MEDIUM, HIGH")
    private String priority;
}
