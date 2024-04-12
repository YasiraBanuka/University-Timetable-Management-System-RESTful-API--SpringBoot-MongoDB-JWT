package sliit.af.assignment.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sliit.af.assignment.dtos.CourseDto;
import sliit.af.assignment.entities.Course;
import sliit.af.assignment.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    // service class to be tested
    @InjectMocks
    private CourseService courseService;

    // mock repository
    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_student() {
        // Given
        CourseDto courseDto = CourseDto.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();
        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();

        // Mock the calls
        when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course);

        // When
        CourseDto savedCourse = courseService.saveCourse(courseDto);

        // Then
        assertNotNull(savedCourse);
        assertEquals(savedCourse.getId(), "1L");
        assertEquals(savedCourse.getName(), "Software Engineering");
        assertEquals(savedCourse.getCode(), "SE");
        assertEquals(savedCourse.getDescription(), "Software Engineering Course");
        assertEquals(savedCourse.getCredits(), 4);
        assertEquals(savedCourse.getFaculty(), "Computing");

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void should_return_all_courses() {
        // Given
        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build());

        // Mock the calls
        when(courseRepository.findAll()).thenReturn(courses);

        // When
        List<CourseDto> courseDtos = courseService.getAllCourses();

        // Then
        assertEquals(courses.size(), courseDtos.size());

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void should_return_student_by_id() {
        // Given
        String courseId = "1L";
        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();

        // Mock the calls
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));

        // When
        CourseDto courseDto = courseService.getCourseById(courseId);

        // Then
        assertEquals(course.getId(), courseDto.getId());
        assertEquals(course.getName(), courseDto.getName());
        assertEquals(course.getCode(), courseDto.getCode());
        assertEquals(course.getDescription(), courseDto.getDescription());
        assertEquals(course.getCredits(), courseDto.getCredits());

        verify(courseRepository, times(1)).findById(courseId);
    }

}