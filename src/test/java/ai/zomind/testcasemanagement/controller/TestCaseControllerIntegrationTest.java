package ai.zomind.testcasemanagement.controller;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.enums.Priority;
import ai.zomind.testcasemanagement.enums.Status;
import ai.zomind.testcasemanagement.model.TestCase;
import ai.zomind.testcasemanagement.repository.TestCaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class TestCaseControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    private TestCaseRepository testCaseRepository;

    private TestCase testCase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        testCase = new TestCase();
        testCase.setId(UUID.randomUUID().toString().substring(0, 8));
        testCase.setTitle("Login Test Case");
        testCase.setStatus(Status.PENDING);
        testCase.setPriority(Priority.HIGH);
        testCase.setCreatedAt(new Date());
    }

    @Test
    void testGetTestCases() throws Exception {
        Page<TestCase> pageTestCase = new PageImpl<>(List.of(testCase));
        when(testCaseRepository.findAll(any(Pageable.class))).thenReturn(pageTestCase);

        mockMvc.perform(get("/api/v1/testcases")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTestCase() throws Exception {
        TestCaseRequestDto testCaseRequestDto = new TestCaseRequestDto();
        testCaseRequestDto.setTitle("Login Test Case");
        testCaseRequestDto.setStatus("PENDING");
        testCaseRequestDto.setPriority("HIGH");

        when(testCaseRepository.save(any(TestCase.class))).thenReturn(testCase);

        mockMvc.perform(post("/api/v1/testcases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCaseRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetTestCaseById() throws Exception {
        when(testCaseRepository.findById(anyString())).thenReturn(Optional.of(testCase));

        mockMvc.perform(get("/api/v1/testcases/{id}", testCase.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateTestCase() throws Exception {
        when(testCaseRepository.findById(anyString())).thenReturn(Optional.of(testCase));
        when(testCaseRepository.save(any(TestCase.class))).thenReturn(testCase);

        TestCaseRequestDto testCaseRequestDto = new TestCaseRequestDto();
        testCaseRequestDto.setTitle("Updated Test Case");

        mockMvc.perform(put("/api/v1/testcases/{id}", testCase.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCaseRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTestCase() throws Exception {
        when(testCaseRepository.findById(anyString())).thenReturn(Optional.of(testCase));

        mockMvc.perform(delete("/api/v1/testcases/{id}", testCase.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}