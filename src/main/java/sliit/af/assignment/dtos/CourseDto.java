package sliit.af.assignment.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {
    private String id;
    private String name;
    private String code;
    private String description;
    private int credits;
    private String faculty;
}
