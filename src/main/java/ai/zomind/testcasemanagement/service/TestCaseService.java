package ai.zomind.testcasemanagement.service;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;

import java.util.List;

public interface TestCaseService {
    List<TestCaseResponseDto> getAllTestCases();

    TestCaseResponseDto getTestCaseById(String id);

    TestCaseResponseDto createTestCase(TestCaseRequestDto testCaseRequestDto);

    TestCaseResponseDto updateTestCase(String id, TestCaseRequestDto testCaseRequestDto);

    TestCaseResponseDto deleteTestCase(String id);
}
