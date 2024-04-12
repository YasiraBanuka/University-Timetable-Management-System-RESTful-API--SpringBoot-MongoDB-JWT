package sliit.af.assignment.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentDto {
    private String id;
    private String enrollmentDate;
    private String studentId;
    private String courseId;
}
