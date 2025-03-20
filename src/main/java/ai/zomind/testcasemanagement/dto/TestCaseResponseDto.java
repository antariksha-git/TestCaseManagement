package ai.zomind.testcasemanagement.dto;

import ai.zomind.testcasemanagement.enums.Priority;
import ai.zomind.testcasemanagement.enums.Status;
import lombok.Data;

@Data
public class TestCaseResponseDto {
    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private String createdAt;
    private String updatedAt;
}
