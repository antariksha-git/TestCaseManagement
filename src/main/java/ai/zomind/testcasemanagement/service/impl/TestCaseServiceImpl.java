package ai.zomind.testcasemanagement.service.impl;

import ai.zomind.testcasemanagement.model.TestCase;
import ai.zomind.testcasemanagement.repository.TestCaseRepository;
import ai.zomind.testcasemanagement.service.TestCaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private TestCaseRepository testCaseRepository;

    @Override
    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    @Override
    public TestCase getTestCaseById(String id) {
        return testCaseRepository.findById(id).get();
    }

    @Override
    public TestCase createTestCase(TestCase testCase) {
        testCase.setId(UUID.randomUUID().toString().substring(0, 8));
        return testCaseRepository.save(testCase);
    }

    @Override
    public TestCase updateTestCase(String id, TestCase updatedTestCase) {
        if (id != null) {
            return testCaseRepository.findById(id).map(tc -> {
                if (updatedTestCase.getTitle() != null) {
                    tc.setTitle(updatedTestCase.getTitle());
                }
                if (updatedTestCase.getDescription() != null) {
                    tc.setDescription(updatedTestCase.getDescription());
                }
                if (updatedTestCase.getStatus() != null) {
                    tc.setStatus(updatedTestCase.getStatus());
                }
                if (updatedTestCase.getPriority() != null) {
                    tc.setPriority(updatedTestCase.getPriority());
                }
                if (updatedTestCase.getUpdatedAt() != null) {
                    tc.setUpdatedAt(updatedTestCase.getUpdatedAt());
                }
                return testCaseRepository.save(tc);
            }).orElseThrow();
        } else {
            throw new RuntimeException("Test case id is required");
        }
    }

    @Override
    public void deleteTestCase(String id) {
        if (testCaseRepository.existsById(id)) {
            testCaseRepository.deleteById(id);
        } else {
            throw new RuntimeException("TestCase not found with id: " + id);
        }
    }
}
