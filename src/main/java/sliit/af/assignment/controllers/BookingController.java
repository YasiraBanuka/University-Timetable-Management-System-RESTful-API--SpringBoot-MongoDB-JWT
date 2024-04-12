package sliit.af.assignment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sliit.af.assignment.dtos.BookingDto;
import sliit.af.assignment.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookingDto> bookResource(@RequestBody BookingDto bookingDto) {
        BookingDto savedBooking = bookingService.bookResource(bookingDto);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") String bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok("Booking deleted successfully");
    }

}
