package sliit.af.assignment.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sliit.af.assignment.dtos.CourseDto;
import sliit.af.assignment.entities.Course;
import sliit.af.assignment.repositories.CourseRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/** Integration tests for {@link CourseService} */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CourseServiceIntegrationTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @Test
    public void testGetAllCourses() {
        // Mock data
        Course course1 = Course.builder()
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(3)
                .faculty("Faculty of Computing")
                .build();
        Course course2 = Course.builder()
                .name("Computer Networks")
                .code("CN")
                .description("Computer Networks Course")
                .credits(3)
                .faculty("Faculty of Computing")
                .build();
        List<Course> courses = Arrays.asList(course1, course2);

        // Mock repository behavior
        when(courseRepository.findAll()).thenReturn(courses);

        // Perform the service method
        List<CourseDto> result = courseService.getAllCourses();

        // Verify the result
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Software Engineering", result.get(0).getName());
        Assertions.assertEquals("SE", result.get(0).getCode());
    }

    @Test
    public void testSaveCourse() {
        // Mock data
        CourseDto courseDto = CourseDto.builder()
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(3)
                .faculty("Faculty of Computing")
                .build();

        Course course = Course.builder()
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(3)
                .faculty("Faculty of Computing")
                .build();

        // Mock repository behavior
        when(courseRepository.save(course)).thenReturn(course);

        // Perform the service method
        CourseDto result = courseService.saveCourse(courseDto);

        // Verify the result
        Assertions.assertEquals("Software Engineering", result.getName());
        Assertions.assertEquals("SE", result.getCode());
    }

}
