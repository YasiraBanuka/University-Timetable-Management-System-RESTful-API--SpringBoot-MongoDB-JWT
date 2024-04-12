package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sliit.af.assignment.entities.Timetable;

public interface TimetableRepository extends MongoRepository<Timetable, String> {
}
