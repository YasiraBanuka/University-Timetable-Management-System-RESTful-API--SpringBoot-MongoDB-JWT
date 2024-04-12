package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import sliit.af.assignment.entities.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'email' : ?0 }")
    Optional<User> findByEmail(String email);

}
