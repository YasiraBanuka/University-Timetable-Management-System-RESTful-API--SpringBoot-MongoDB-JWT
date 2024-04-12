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
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

            LocalDateTime startTime = LocalDateTime.parse(bookingDto.getStartTime());
            LocalDateTime endTime = LocalDateTime.parse(bookingDto.getEndTime());
            validateBookingTimes(startTime, endTime);

            ZoneId zoneId = ZoneId.of("Asia/Colombo");
            ZonedDateTime zonedStartTime = startTime.atZone(zoneId);
            ZonedDateTime zonedEndTime = endTime.atZone(zoneId);

            checkForOverlappingBookings(resource, zonedStartTime.toLocalDateTime(), zonedEndTime.toLocalDateTime());

            Booking booking = Booking.builder()
                    .startTime(zonedStartTime.toLocalDateTime())
                    .endTime(zonedEndTime.toLocalDateTime())
                    .resource(resource)
                    .build();
            bookingRepository.save(booking);
            System.out.println("Booking saved successfully");
            return BookingMapper.mapToBookingDto(booking);
        }
        else {
            System.out.println("Resource not available");
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
        System.out.println("Booking deleted successfully");
    }

    // check if the start time is before the end time and if the start time is not in the past.
    private void validateBookingTimes(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time must not be null");
        }
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (!startTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time must not be in the past");
        }
    }

    // check if the booking times overlap with any existing bookings for the resource
    private void checkForOverlappingBookings(Resource resource, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> existingBookings = bookingRepository.findAllByResource(resource);

        for (Booking existingBooking : existingBookings) {
            if ((startTime.isBefore(existingBooking.getEndTime()) && endTime.isAfter(existingBooking.getStartTime()))) {
                System.out.println("Booking times overlap with an existing booking");
                throw new IllegalArgumentException("Booking times overlap with an existing booking");
            }
        }
    }

}
