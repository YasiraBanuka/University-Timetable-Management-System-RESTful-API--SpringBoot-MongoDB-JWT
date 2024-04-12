package sliit.af.assignment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sliit.af.assignment.entities.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
