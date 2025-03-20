package ai.zomind.testcasemanagement.repository;

import ai.zomind.testcasemanagement.model.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCase, String> {
}
