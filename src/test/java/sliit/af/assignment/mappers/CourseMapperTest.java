package sliit.af.assignment.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sliit.af.assignment.dtos.CourseDto;
import sliit.af.assignment.entities.Course;

class CourseMapperTest {

    private CourseMapper courseMapper;

    @BeforeEach
    void setUp() {
        courseMapper = new CourseMapper();
    }

    @Test
    public void shouldMapCourseDtoToCourse() {
        // Given
        CourseDto courseDto = CourseDto.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();

        // When
        Course course = courseMapper.mapToCourse(courseDto);

        // Then
        assertEquals(course.getId(), "1L");
        assertEquals(course.getName(), "Software Engineering");
        assertEquals(course.getCode(), "SE");
        assertEquals(course.getDescription(), "Software Engineering Course");
        assertEquals(course.getCredits(), 4);
        assertEquals(course.getFaculty(), "Computing");
    }

    @Test
    public void should_map_courseDto_to_course_when_courseDto_is_null() {
        Course course = courseMapper.mapToCourse(null);
        assertEquals("", course.getName());
        assertEquals("", course.getCode());
    }

    @Test
    public void shouldMapCourseToCourseDto() {
        // Given
        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();

        // When
        CourseDto courseDto = courseMapper.mapToCourseDto(course);

        // Then
        assertEquals(courseDto.getId(), course.getId());
        assertEquals(courseDto.getName(), course.getName());
        assertEquals(courseDto.getCode(), course.getCode());
        assertEquals(courseDto.getDescription(), course.getDescription());
        assertEquals(courseDto.getCredits(), course.getCredits());
        assertEquals(courseDto.getFaculty(), course.getFaculty());
    }

}