package sliit.af.assignment.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.af.assignment.dtos.BookingDto;
import sliit.af.assignment.entities.Booking;
import sliit.af.assignment.entities.Resource;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.mappers.BookingMapper;
import sliit.af.assignment.repositories.BookingRepository;
import sliit.af.assignment.repositories.ResourceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private BookingRepository bookingRepository;
    private ResourceRepository resourceRepository;

    public BookingDto bookResource(BookingDto bookingDto) {
        Resource resource = resourceRepository.findById(bookingDto.getResourceId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found")
        );
        if (resource.getAvailability().equals("AVAILABLE")) {
            resource.setAvailability("BOOKED");
            resourceRepository.save(resource);
            Booking booking = Booking.builder()
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusHours(2))
                    .resource(resource)
                    .build();
            bookingRepository.save(booking);
            return BookingMapper.mapToBookingDto(booking);
        } else {
            throw new ResourceNotFoundException("Resource not available");
        }
    }

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors.toList());
    }

    public void deleteBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new ResourceNotFoundException("Booking not found")
        );
        Resource resource = booking.getResource();
        resource.setAvailability("AVAILABLE");
        resourceRepository.save(resource);
        bookingRepository.delete(booking);
    }

}
