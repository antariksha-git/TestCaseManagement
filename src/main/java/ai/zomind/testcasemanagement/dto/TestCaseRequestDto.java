package ai.zomind.testcasemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TestCaseRequestDto {

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be blank")
    @Schema(description = "Title of the test case", example = "Login Test")
    private String title;

    @Schema(description = "Description of the test case", example = "This test case verifies the login functionality.")
    private String description;

    @Pattern(regexp = "PENDING|IN_PROGRESS|PASSED|FAILED", message = "Invalid status. Allowed values: PENDING, IN_PROGRESS, PASSED, FAILED")
    @Schema(description = "Status of the test case", example = "IN_PROGRESS")
    private String status;

    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Invalid priority. Allowed values: LOW, MEDIUM, HIGH")
    @Schema(description = "Priority of the test case", example = "HIGH")
    private String priority;
}
