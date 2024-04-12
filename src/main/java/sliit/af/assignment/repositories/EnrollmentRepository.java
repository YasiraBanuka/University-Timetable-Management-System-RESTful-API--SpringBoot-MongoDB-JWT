package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sliit.af.assignment.entities.Enrollment;

public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {
}
