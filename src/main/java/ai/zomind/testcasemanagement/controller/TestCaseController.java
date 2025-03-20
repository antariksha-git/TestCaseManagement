package ai.zomind.testcasemanagement.controller;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;
import ai.zomind.testcasemanagement.service.TestCaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/testcases")
@AllArgsConstructor
public class TestCaseController {

    private TestCaseService testCaseService;

    @GetMapping
    public List<TestCaseResponseDto> getAllTestCases() {
        return testCaseService.getAllTestCases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> getTestCaseById(@PathVariable String id) {
        TestCaseResponseDto testCaseResponseDto = testCaseService.getTestCaseById(id);
        return ResponseEntity.ok(testCaseResponseDto);
    }

    @PostMapping
    public ResponseEntity<TestCaseResponseDto> createTestCase(@RequestBody TestCaseRequestDto testCaseRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testCaseService.createTestCase(testCaseRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> updateTestCase(@PathVariable String id, @RequestBody TestCaseRequestDto testCaseRequestDto) {
        try {
            TestCaseResponseDto testCaseResponseDto = testCaseService.updateTestCase(id, testCaseRequestDto);
            return ResponseEntity.ok(testCaseResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TestCaseResponseDto> deleteTestCase(@PathVariable String id) {
        try {
            return ResponseEntity.ok(testCaseService.deleteTestCase(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}