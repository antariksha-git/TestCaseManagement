package ai.zomind.testcasemanagement.model;

import ai.zomind.testcasemanagement.enums.Priority;
import ai.zomind.testcasemanagement.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "testcases")
public class TestCase {
    @Id
    private String id;

    private String title;

    private String description;

    @Indexed(name = "status_index")
    private Status status;

    @Indexed(name = "priority_index")
    private Priority priority;

    private Date createdAt;

    private Date updatedAt;
}