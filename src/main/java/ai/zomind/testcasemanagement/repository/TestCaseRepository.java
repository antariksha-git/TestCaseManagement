package ai.zomind.testcasemanagement.repository;

import ai.zomind.testcasemanagement.enums.Priority;
import ai.zomind.testcasemanagement.enums.Status;
import ai.zomind.testcasemanagement.model.TestCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCase, String> {
    Page<TestCase> findByStatusAndPriority(Status status, Priority priority, Pageable pageable);

    Page<TestCase> findByStatus(Status status, Pageable pageable);

    Page<TestCase> findByPriority(Priority priority, Pageable pageable);
}
