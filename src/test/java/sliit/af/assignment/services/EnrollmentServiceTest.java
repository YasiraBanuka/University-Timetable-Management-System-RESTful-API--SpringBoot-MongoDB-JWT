package sliit.af.assignment.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sliit.af.assignment.dtos.EnrollmentDto;
import sliit.af.assignment.entities.Course;
import sliit.af.assignment.entities.Enrollment;
import sliit.af.assignment.entities.User;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.repositories.CourseRepository;
import sliit.af.assignment.repositories.EnrollmentRepository;
import sliit.af.assignment.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldEnrollStudentWhenStudentAndCourseExistAndNotAlreadyEnrolled() {
        // Given
        EnrollmentDto enrollmentDto = EnrollmentDto.builder()
                .studentId("1L")
                .courseId("1L")
                .build();

        User student = new User();
        student.setId("1L");
        student.setRole("STUDENT");

        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();

        // When
        when(userRepository.findById("1L")).thenReturn(Optional.of(student));
        when(courseRepository.findById("1L")).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());

        // Then
        assertDoesNotThrow(() -> enrollmentService.enrollCourse(enrollmentDto));
    }

    @Test
    public void shouldThrowExceptionWhenStudentAlreadyEnrolled() {
        // Given
        EnrollmentDto enrollmentDto = EnrollmentDto.builder()
                .studentId("1L")
                .courseId("1L")
                .build();

        User student = new User();
        student.setId("1L");
        student.setRole("STUDENT");

        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        // When
        when(userRepository.findById("1L")).thenReturn(Optional.of(student));
        when(courseRepository.findById("1L")).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(Collections.singletonList(enrollment));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enrollCourse(enrollmentDto));
    }

    @Test
    public void shouldThrowExceptionWhenStudentDoesNotExist() {
        // Given
        EnrollmentDto enrollmentDto = EnrollmentDto.builder()
                .studentId("1L")
                .courseId("1L")
                .build();

        // When
        when(userRepository.findById("1L")).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enrollCourse(enrollmentDto));
    }

    @Test
    public void shouldThrowExceptionWhenCourseDoesNotExist() {
        // Given
        EnrollmentDto enrollmentDto = EnrollmentDto.builder()
                .studentId("1L")
                .courseId("1L")
                .build();

        User student = new User();
        student.setId("1L");
        student.setRole("STUDENT");

        // When
        when(userRepository.findById("1L")).thenReturn(Optional.of(student));
        when(courseRepository.findById("1L")).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enrollCourse(enrollmentDto));
    }

    @Test
    public void shouldDeleteEnrollmentWhenEnrollmentExists() {
        // Given
        String enrollmentId = "1L";

        User student = new User();
        student.setId("1L");
        student.setRole("STUDENT");

        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        // When
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));

        // Then
        assertDoesNotThrow(() -> enrollmentService.deleteEnrollment(enrollmentId));
        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingNonExistingEnrollment() {
        // Given
        String enrollmentId = "1L";

        // When
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.deleteEnrollment(enrollmentId));
    }

}
