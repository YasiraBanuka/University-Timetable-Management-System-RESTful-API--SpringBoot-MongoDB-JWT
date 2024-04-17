package sliit.af.assignment.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.af.assignment.dtos.EnrollmentDto;
import sliit.af.assignment.entities.Course;
import sliit.af.assignment.entities.Enrollment;
import sliit.af.assignment.entities.User;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.mappers.EnrollmentMapper;
import sliit.af.assignment.repositories.CourseRepository;
import sliit.af.assignment.repositories.EnrollmentRepository;
import sliit.af.assignment.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private MailService mailService;

    public EnrollmentDto enrollCourse(EnrollmentDto enrollmentDto) {
        User student = userRepository.findById(enrollmentDto.getStudentId()).orElseThrow(
                () -> new ResourceNotFoundException("Student not found")
        );
        Course course = courseRepository.findById(enrollmentDto.getCourseId()).orElseThrow(
                () -> new ResourceNotFoundException("Course not found")
        );
        // check if the student has already enrolled for the course
        boolean alreadyEnrolled = enrollmentRepository
                .findAll()
                .stream()
                .anyMatch(enrollment -> enrollment.getStudent().getId().equals(student.getId())
                        && enrollment.getCourse().getId().equals(course.getId())
                );
        if (!alreadyEnrolled) {
            if (student.getRole().equals("STUDENT")) {
                Enrollment enrollment = Enrollment.builder()
                        .enrollmentDate(LocalDateTime.now())
                        .student(student)
                        .course(course)
                        .build();
                enrollmentRepository.save(enrollment);

                // Send email after successful enrollment
                try {
                    mailService.sendMail(student.getEmail(), "Enrollment Successful", "Dear " + student.getName() + ", you have successfully enrolled in the course " + course.getName() + "!");
                } catch (Exception e) {
                    System.out.println("Failed to send email: " + e.getMessage());
                }

                return EnrollmentMapper.mapToEnrollmentDto(enrollment);
            } else {
                throw new ResourceNotFoundException("User role not allowed to enroll to a course");
            }
        } else {
            throw new ResourceNotFoundException("Student already enrolled for the course");
        }
    }

    public List<EnrollmentDto> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(EnrollmentMapper::mapToEnrollmentDto)
                .collect(Collectors.toList());
    }

    public void deleteEnrollment(String enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(
                () -> new ResourceNotFoundException("Enrollment not found")
        );
        enrollmentRepository.delete(enrollment);
    }

}
