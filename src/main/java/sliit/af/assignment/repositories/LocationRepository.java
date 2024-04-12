package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sliit.af.assignment.entities.Location;

public interface LocationRepository extends MongoRepository<Location, String> {
}
