package ai.zomind.testcasemanagement.controller;

import ai.zomind.testcasemanagement.model.TestCase;
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
    public List<TestCase> getAllTestCases() {
        return testCaseService.getAllTestCases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable String id) {
        TestCase testCase = testCaseService.getTestCaseById(id);
        return ResponseEntity.ok(testCase);
    }

    @PostMapping
    public ResponseEntity<TestCase> createTestCase(@RequestBody TestCase testCase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testCaseService.createTestCase(testCase));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCase> updateTestCase(@PathVariable String id, @RequestBody TestCase updatedTestCase) {
        try {
            TestCase testCase = testCaseService.updateTestCase(id, updatedTestCase);
            return ResponseEntity.ok(testCase);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable String id) {
        try {
            testCaseService.deleteTestCase(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}