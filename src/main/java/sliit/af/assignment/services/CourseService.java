package sliit.af.assignment.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.af.assignment.dtos.CourseDto;
import sliit.af.assignment.entities.Course;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.mappers.CourseMapper;
import sliit.af.assignment.repositories.CourseRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;

    public CourseDto saveCourse(CourseDto courseDto) {
        Course course = CourseMapper.mapToCourse(courseDto);
        Course savedCourse = courseRepository.save(course);
        return CourseMapper.mapToCourseDto(savedCourse);
    }

    public CourseDto getCourseById(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id: " + courseId)
                );
        return CourseMapper.mapToCourseDto(course);
    }

    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(CourseMapper::mapToCourseDto)
                .toList();
    }

    public CourseDto updateCourse(String courseId, CourseDto courseDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id: " + courseId)
                );

        course.setName(courseDto.getName());
        course.setCode(courseDto.getCode());
        course.setDescription(courseDto.getDescription());
        course.setCredits(courseDto.getCredits());

        Course updatedCourse = courseRepository.save(course);
        return CourseMapper.mapToCourseDto(updatedCourse);
    }

    public void deleteCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id: " + courseId)
                );
        courseRepository.delete(course);
    }

}
