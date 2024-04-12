package sliit.af.assignment.mappers;

import sliit.af.assignment.dtos.CourseDto;
import sliit.af.assignment.entities.Course;

public class CourseMapper {

    public static CourseDto mapToCourseDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .code(course.getCode())
                .description(course.getDescription())
                .credits(course.getCredits())
                .faculty(course.getFaculty())
                .build();
    }

    public static Course mapToCourse(CourseDto courseDto) {
        return Course.builder()
                .id(courseDto.getId())
                .name(courseDto.getName())
                .code(courseDto.getCode())
                .description(courseDto.getDescription())
                .credits(courseDto.getCredits())
                .faculty(courseDto.getFaculty())
                .build();
    }

}
