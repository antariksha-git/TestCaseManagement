package ai.zomind.testcasemanagement.service;

import ai.zomind.testcasemanagement.model.TestCase;

import java.util.List;

public interface TestCaseService {
    List<TestCase> getAllTestCases();

    TestCase getTestCaseById(String id);

    TestCase createTestCase(TestCase testCase);

    TestCase updateTestCase(String id, TestCase updatedTestCase);

    void deleteTestCase(String id);
}
