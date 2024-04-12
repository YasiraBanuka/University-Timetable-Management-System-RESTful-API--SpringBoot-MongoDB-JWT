package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sliit.af.assignment.entities.Resource;

public interface ResourceRepository extends MongoRepository<Resource, String> {
}
