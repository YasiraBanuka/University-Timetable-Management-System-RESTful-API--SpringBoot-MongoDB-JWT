package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sliit.af.assignment.entities.Booking;
import sliit.af.assignment.entities.Resource;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findAllByResource(Resource resource);
}
