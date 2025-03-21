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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
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
                log.error("Invalid status value. Allowed values are: PENDING, IN_PROGRESS, PASSED, FAILED");
                throw new InvalidDataException("Invalid status value. Allowed values are: PENDING, IN_PROGRESS, PASSED, FAILED");
            }
        }

        if (priority != null) {
            try {
                priorityEnum = Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.error("Invalid priority value. Allowed values are: LOW, MEDIUM, HIGH");
                throw new InvalidDataException("Invalid priority value. Allowed values are: LOW, MEDIUM, HIGH");
            }
        }

        Page<TestCase> pageTestCase;

        if (statusEnum == null && priorityEnum == null) {
            pageTestCase = testCaseRepository.findAll(pageable);
        } else if (statusEnum != null && priorityEnum == null) {
            pageTestCase = testCaseRepository.findByStatus(statusEnum, pageable);
        } else if (statusEnum == null) {
            pageTestCase = testCaseRepository.findByPriority(priorityEnum, pageable);
        } else {
            pageTestCase = testCaseRepository.findByStatusAndPriority(statusEnum, priorityEnum, pageable);
        }

        log.info("Found {} test cases", pageTestCase.getTotalElements());
        return pageTestCase.map(testCaseMapper::toTestCaseResponseDto);
    }

    @Override
    public TestCaseResponseDto getTestCaseById(String id) {
        TestCaseResponseDto testCaseResponseDto = testCaseRepository.findById(id)
                .map(testCaseMapper::toTestCaseResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with the given id: " + id));

        log.info("Found test case with id {}", id);
        return testCaseResponseDto;
    }

    @Override
    public TestCaseResponseDto createTestCase(TestCaseRequestDto testCaseRequestDto) {
        TestCase testCase = testCaseMapper.toTestCase(testCaseRequestDto);
        testCase.setId(UUID.randomUUID().toString().substring(0, 8));
        testCase.setCreatedAt(new Date());
        TestCase savedTestCase = testCaseRepository.save(testCase);

        log.info("Created new test case with id {}", savedTestCase.getId());
        return testCaseMapper.toTestCaseResponseDto(testCase);
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
                    log.error("Invalid status value. Allowed values are: LOW, MEDIUM, HIGH");
                    throw new InvalidDataException("Status must be PENDING or IN_PROGRESS or FAILED or PASSED");
                }
            }

            if (testCaseRequestDto.getPriority() != null) {
                try {
                    testCase.setPriority(Priority.valueOf(testCaseRequestDto.getPriority().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    log.error("Invalid priority value. Allowed values are: LOW, MEDIUM, HIGH");
                    throw new InvalidDataException("Priority must be LOW or MEDIUM or HIGH");
                }
            }
            testCase.setUpdatedAt(new Date());
            log.info("Updated test case with id {}", id);
            return testCaseMapper.toTestCaseResponseDto(testCaseRepository.save(testCase));
        }).orElseThrow(() -> new ResourceNotFoundException("TestCase not found with the given id: " + id));
    }


    @Override
    public TestCaseResponseDto deleteTestCase(String id) {
        TestCaseResponseDto testCaseResponseDto = testCaseRepository.findById(id)
                .map(testCaseMapper::toTestCaseResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with the given id: " + id));

        testCaseRepository.deleteById(id);
        log.info("Deleted test case with id {}", id);
        return testCaseResponseDto;
    }
}
