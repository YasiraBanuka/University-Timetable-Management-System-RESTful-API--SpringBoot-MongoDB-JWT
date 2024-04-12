package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sliit.af.assignment.entities.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
}
