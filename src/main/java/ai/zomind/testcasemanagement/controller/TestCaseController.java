package ai.zomind.testcasemanagement.controller;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;
import ai.zomind.testcasemanagement.service.TestCaseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/testcases")
@AllArgsConstructor
public class TestCaseController {

    private TestCaseService testCaseService;

    @GetMapping
    public Page<TestCaseResponseDto> getTestCases(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return testCaseService.getAllTestCases(status, priority, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> getTestCaseById(@PathVariable String id) {
        TestCaseResponseDto testCaseResponseDto = testCaseService.getTestCaseById(id);
        return ResponseEntity.ok(testCaseResponseDto);
    }

    @PostMapping
    public ResponseEntity<TestCaseResponseDto> createTestCase(@RequestBody @Valid TestCaseRequestDto testCaseRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testCaseService.createTestCase(testCaseRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> updateTestCase(@PathVariable String id, @RequestBody TestCaseRequestDto testCaseRequestDto) {
        TestCaseResponseDto testCaseResponseDto = testCaseService.updateTestCase(id, testCaseRequestDto);
        return ResponseEntity.ok(testCaseResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> deleteTestCase(@PathVariable String id) {
        return ResponseEntity.ok(testCaseService.deleteTestCase(id));
    }
}