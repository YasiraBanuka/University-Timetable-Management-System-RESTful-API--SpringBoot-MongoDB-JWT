package sliit.af.assignment.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "courses")
@Data
@Builder
public class Course {

    @Id
    private String id;

    @Field(name = "course_name")
    private String name;

    @Field(name = "course_code")
    @Indexed(unique = true)
    private String code;

    private String description;
    @Field(name = "course_credits")

    private int credits;

    private String faculty;

}
