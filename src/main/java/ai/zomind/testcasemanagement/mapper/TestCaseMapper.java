package ai.zomind.testcasemanagement.mapper;

import ai.zomind.testcasemanagement.dto.TestCaseRequestDto;
import ai.zomind.testcasemanagement.dto.TestCaseResponseDto;
import ai.zomind.testcasemanagement.model.TestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {
    TestCase toTestCase(TestCaseRequestDto testCaseRequestDto);

    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "dd:MM:yyyy HH:mm")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd:MM:yyyy HH:mm")
    TestCaseResponseDto toTestCaseResponseDto(TestCase testCase);
}
