package ai.zomind.testcasemanagement.repository;

import ai.zomind.testcasemanagement.enums.Priority;
import ai.zomind.testcasemanagement.enums.Status;
import ai.zomind.testcasemanagement.model.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class TestCaseRepositoryTest {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Test
    void testFindByStatus() {
        TestCase testCase = new TestCase();
        testCase.setId("0d72bd70");
        testCase.setTitle("Login Test Case");
        testCase.setStatus(Status.PENDING);
        testCase.setPriority(Priority.HIGH);
        testCase.setCreatedAt(new Date());
        testCaseRepository.save(testCase);

        Pageable pageable = PageRequest.of(0, 10);
        Page<TestCase> result = testCaseRepository.findByStatus(Status.PENDING, pageable);

        assertNotNull(result);
    }

    @Test
    void testFindByPriority() {
        TestCase testCase = new TestCase();
        testCase.setId("0d72bd70");
        testCase.setTitle("Login Test Case");
        testCase.setStatus(Status.PENDING);
        testCase.setPriority(Priority.HIGH);
        testCase.setCreatedAt(new Date());
        testCaseRepository.save(testCase);

        Pageable pageable = PageRequest.of(0, 10);
        Page<TestCase> result = testCaseRepository.findByPriority(Priority.HIGH, pageable);

        assertNotNull(result);
    }
}
