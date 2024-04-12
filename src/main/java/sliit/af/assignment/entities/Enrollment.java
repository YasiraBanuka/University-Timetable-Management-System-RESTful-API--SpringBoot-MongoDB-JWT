package sliit.af.assignment.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "enrollment")
@Data
@Builder
public class Enrollment {

    @Id
    private String id;
    private LocalDateTime enrollmentDate;

    private User student;
    private Course course;

}
