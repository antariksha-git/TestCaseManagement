package ai.zomind.testcasemanagement.service.impl;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;
import ai.zomind.testcasemanagement.enums.Priority;
import ai.zomind.testcasemanagement.enums.Status;
import ai.zomind.testcasemanagement.exception.InvalidDataException;
import ai.zomind.testcasemanagement.exception.ResourceNotFoundException;
import ai.zomind.testcasemanagement.mapper.TestCaseMapper;
import ai.zomind.testcasemanagement.model.TestCase;
import ai.zomind.testcasemanagement.repository.TestCaseRepository;
import ai.zomind.testcasemanagement.service.TestCaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private TestCaseRepository testCaseRepository;

    private TestCaseMapper testCaseMapper;

    @Override
    public List<TestCaseResponseDto> getAllTestCases() {
        return testCaseRepository.findAll()
                .stream()
                .map(testCaseMapper::toTestCaseResponseDto)
                .toList();
    }

    @Override
    public TestCaseResponseDto getTestCaseById(String id) {
        return testCaseRepository.findById(id)
                .map(testCaseMapper::toTestCaseResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with the given id: " + id));
    }

    @Override
    public TestCaseResponseDto createTestCase(TestCaseRequestDto testCaseRequestDto) {
        TestCase testCase = testCaseMapper.toTestCase(testCaseRequestDto);
        testCase.setId(UUID.randomUUID().toString().substring(0, 8));
        testCase.setCreatedAt(new Date());
        return testCaseMapper.toTestCaseResponseDto(testCaseRepository.save(testCase));
    }

    @Override
    public TestCaseResponseDto updateTestCase(String id, TestCaseRequestDto testCaseRequestDto) {
        return testCaseRepository.findById(id).map(testCase -> {
            if (testCaseRequestDto.getTitle() != null) {
                testCase.setTitle(testCaseRequestDto.getTitle());
            }
            if (testCaseRequestDto.getDescription() != null) {
                testCase.setDescription(testCaseRequestDto.getDescription());
            }

            if (testCaseRequestDto.getStatus() != null) {
                try {
                    testCase.setStatus(Status.valueOf(testCaseRequestDto.getStatus().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new InvalidDataException("Status must be PENDING or IN_PROGRESS or FAILED or PASSED");
                }
            }

            if (testCaseRequestDto.getPriority() != null) {
                try {
                    testCase.setPriority(Priority.valueOf(testCaseRequestDto.getPriority().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new InvalidDataException("Priority must be LOW or MEDIUM or HIGH");
                }
            }
            testCase.setUpdatedAt(new Date());
            return testCaseMapper.toTestCaseResponseDto(testCaseRepository.save(testCase));
        }).orElseThrow(() -> new ResourceNotFoundException("TestCase not found with the given id: " + id));
    }


    @Override
    public TestCaseResponseDto deleteTestCase(String id) {
        TestCaseResponseDto testCaseResponseDto = testCaseRepository.findById(id)
                .map(testCaseMapper::toTestCaseResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with the given id: " + id));

        testCaseRepository.deleteById(id);
        return testCaseResponseDto;
    }
}
