package ai.zomind.testcasemanagement.service;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;
import org.springframework.data.domain.Page;

public interface TestCaseService {
    Page<TestCaseResponseDto> getAllTestCases(String status, String priority, int page, int size);

    TestCaseResponseDto getTestCaseById(String id);

    TestCaseResponseDto createTestCase(TestCaseRequestDto testCaseRequestDto);

    TestCaseResponseDto updateTestCase(String id, TestCaseRequestDto testCaseRequestDto);

    TestCaseResponseDto deleteTestCase(String id);
}
