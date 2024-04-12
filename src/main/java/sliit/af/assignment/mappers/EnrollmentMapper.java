package sliit.af.assignment.mappers;

import sliit.af.assignment.dtos.EnrollmentDto;
import sliit.af.assignment.entities.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentDto mapToEnrollmentDto(Enrollment enrollment) {
        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .enrollmentDate(enrollment.getEnrollmentDate().toString())
                .studentId(enrollment.getStudent().getId())
                .courseId(enrollment.getCourse().getId())
                .build();
    }

}
