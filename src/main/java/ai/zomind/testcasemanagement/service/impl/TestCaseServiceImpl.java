package ai.zomind.testcasemanagement.service.impl;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;
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
                .get();
    }

    @Override
    public TestCaseResponseDto createTestCase(TestCaseRequestDto testCaseRequestDto) {
        TestCase testCase = testCaseMapper.toTestCase(testCaseRequestDto);
        testCase.setId(UUID.randomUUID().toString().substring(0, 8));
        testCase.setCreatedAt(new Date());
        testCase.setUpdatedAt(new Date());
        return testCaseMapper.toTestCaseResponseDto(testCaseRepository.save(testCase));
    }

    @Override
    public TestCaseResponseDto updateTestCase(String id, TestCaseRequestDto testCaseRequestDto) {
        if (id != null) {
            return testCaseRepository.findById(id).map(testCase -> {
                if (testCaseRequestDto.getTitle() != null) {
                    testCase.setTitle(testCaseRequestDto.getTitle());
                }
                if (testCaseRequestDto.getDescription() != null) {
                    testCase.setDescription(testCaseRequestDto.getDescription());
                }
                if (testCaseRequestDto.getStatus() != null) {
                    testCase.setStatus(testCaseRequestDto.getStatus());
                }
                if (testCaseRequestDto.getPriority() != null) {
                    testCase.setPriority(testCaseRequestDto.getPriority());
                }
                testCase.setUpdatedAt(new Date());
                return testCaseMapper.toTestCaseResponseDto(testCaseRepository.save(testCase));
            }).orElseThrow();
        } else {
            throw new RuntimeException("Test case id is required");
        }
    }

    @Override
    public TestCaseResponseDto deleteTestCase(String id) {
        if (testCaseRepository.existsById(id)) {
            TestCaseResponseDto testCaseResponseDto = testCaseRepository.findById(id)
                    .map(testCaseMapper::toTestCaseResponseDto)
                    .get();

            testCaseRepository.deleteById(id);

            return testCaseResponseDto;
        } else {
            throw new RuntimeException("TestCase not found with id: " + id);
        }
    }
}
