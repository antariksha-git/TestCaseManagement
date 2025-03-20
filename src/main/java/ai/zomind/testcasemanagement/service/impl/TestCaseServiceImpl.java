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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private TestCaseRepository testCaseRepository;

    private TestCaseMapper testCaseMapper;

    @Override
    public Page<TestCaseResponseDto> getAllTestCases(String status, String priority, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Status statusEnum = null;
        Priority priorityEnum = null;

        if (status != null) {
            try {
                statusEnum = Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Invalid status value. Allowed values are: PENDING, IN_PROGRESS, PASSED, FAILED");
            }
        }

        if (priority != null) {
            try {
                priorityEnum = Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Invalid priority value. Allowed values are: LOW, MEDIUM, HIGH");
            }
        }

        if (statusEnum == null && priorityEnum == null) {
            return testCaseRepository.findAll(pageable)
                    .map(testCaseMapper::toTestCaseResponseDto);
        } else if (statusEnum != null && priorityEnum == null) {
            return testCaseRepository.findByStatus(statusEnum, pageable)
                    .map(testCaseMapper::toTestCaseResponseDto);
        } else if (statusEnum == null) {
            return testCaseRepository.findByPriority(priorityEnum, pageable)
                    .map(testCaseMapper::toTestCaseResponseDto);
        } else {
            return testCaseRepository.findByStatusAndPriority(statusEnum, priorityEnum, pageable)
                    .map(testCaseMapper::toTestCaseResponseDto);
        }
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
