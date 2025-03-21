package ai.zomind.testcasemanagement.service.impl;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;
import ai.zomind.testcasemanagement.enums.Priority;
import ai.zomind.testcasemanagement.enums.Status;
import ai.zomind.testcasemanagement.mapper.TestCaseMapper;
import ai.zomind.testcasemanagement.model.TestCase;
import ai.zomind.testcasemanagement.repository.TestCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestCaseServiceImplTest {

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private TestCaseMapper testCaseMapper;

    @InjectMocks
    private TestCaseServiceImpl testCaseService;

    private TestCase testCase;
    private TestCaseRequestDto testCaseRequestDto;
    private TestCaseResponseDto testCaseResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCase = new TestCase();
        testCase.setId("0d72bd70");
        testCase.setTitle("Login Test Case");
        testCase.setStatus(Status.PENDING);
        testCase.setPriority(Priority.HIGH);

        testCaseRequestDto = new TestCaseRequestDto();
        testCaseRequestDto.setTitle("Login Test Case");
        testCaseRequestDto.setStatus("PENDING");
        testCaseRequestDto.setPriority("HIGH");

        testCaseResponseDto = new TestCaseResponseDto();
        testCaseResponseDto.setId(testCase.getId());
        testCaseResponseDto.setTitle("Login Test Case");
    }

    @Test
    void testGetAllTestCases() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TestCase> page = new PageImpl<>(Collections.singletonList(testCase));
        when(testCaseRepository.findAll(pageable)).thenReturn(page);
        when(testCaseMapper.toTestCaseResponseDto(any(TestCase.class))).thenReturn(testCaseResponseDto);

        Page<TestCaseResponseDto> result = testCaseService.getAllTestCases(null, null, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(testCaseRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetTestCaseById() {
        when(testCaseRepository.findById(anyString())).thenReturn(Optional.of(testCase));
        when(testCaseMapper.toTestCaseResponseDto(any(TestCase.class))).thenReturn(testCaseResponseDto);

        TestCaseResponseDto result = testCaseService.getTestCaseById(testCase.getId());

        assertNotNull(result);
        assertEquals(testCase.getId(), result.getId());
        verify(testCaseRepository, times(1)).findById(testCase.getId());
    }

    @Test
    void testCreateTestCase() {
        when(testCaseMapper.toTestCase(any(TestCaseRequestDto.class))).thenReturn(testCase);
        when(testCaseRepository.save(any(TestCase.class))).thenReturn(testCase);
        when(testCaseMapper.toTestCaseResponseDto(any(TestCase.class))).thenReturn(testCaseResponseDto);

        TestCaseResponseDto result = testCaseService.createTestCase(testCaseRequestDto);

        assertNotNull(result);
        verify(testCaseRepository, times(1)).save(testCase);
    }

    @Test
    void testUpdateTestCase() {
        when(testCaseRepository.findById(anyString())).thenReturn(Optional.of(testCase));
        when(testCaseRepository.save(any(TestCase.class))).thenReturn(testCase);
        when(testCaseMapper.toTestCaseResponseDto(any(TestCase.class))).thenReturn(testCaseResponseDto);

        TestCaseResponseDto result = testCaseService.updateTestCase(testCase.getId(), testCaseRequestDto);

        assertNotNull(result);
        assertEquals(testCase.getId(), result.getId());
        verify(testCaseRepository, times(1)).findById(testCase.getId());
        verify(testCaseRepository, times(1)).save(testCase);
    }

    @Test
    void testDeleteTestCase() {
        when(testCaseRepository.findById(anyString())).thenReturn(Optional.of(testCase));
        doNothing().when(testCaseRepository).deleteById(anyString());
        when(testCaseMapper.toTestCaseResponseDto(any(TestCase.class))).thenReturn(testCaseResponseDto);

        TestCaseResponseDto result = testCaseService.deleteTestCase(testCase.getId());

        assertNotNull(result);
        assertEquals(testCase.getId(), result.getId());
        verify(testCaseRepository, times(1)).findById(testCase.getId());
        verify(testCaseRepository, times(1)).deleteById(testCase.getId());
    }
}