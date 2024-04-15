package sliit.af.assignment.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sliit.af.assignment.dtos.BookingDto;
import sliit.af.assignment.entities.Booking;
import sliit.af.assignment.entities.Resource;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.repositories.BookingRepository;
import sliit.af.assignment.repositories.ResourceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ResourceRepository resourceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_successfully_book_a_resource() {
        // Arrange
        String resourceId = "resourceId";
        BookingDto bookingDto = BookingDto.builder()
                .resourceId(resourceId)
                .startTime(LocalDateTime.now().plusMinutes(1).toString())
                .endTime(LocalDateTime.now().plusHours(1).toString())
                .build();

        Resource resource = Resource.builder()
                .id(resourceId)
                .availability("AVAILABLE")
                .build();

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setId("bookingId");
            return booking;
        });

        // Act
        BookingDto result = bookingService.bookResource(bookingDto);

        // Assert
        assertNotNull(result);
        assertEquals("bookingId", result.getId());
        assertEquals(resourceId, result.getResourceId());

        verify(resourceRepository, times(1)).findById(resourceId);
        verify(resourceRepository, times(1)).save(resource);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void should_throw_exception_when_resource_not_found() {
        // Given
        String resourceId = "resourceId";
        BookingDto bookingDto = BookingDto.builder()
                .resourceId(resourceId)
                .build();

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> bookingService.bookResource(bookingDto));
    }

    @Test
    void should_throw_exception_when_resource_not_available() {
        // Given
        String resourceId = "resourceId";
        BookingDto bookingDto = BookingDto.builder()
                .resourceId(resourceId)
                .build();

        Resource resource = Resource.builder()
                .id(resourceId)
                .availability("BOOKED")
                .build();

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> bookingService.bookResource(bookingDto));
    }

    @Test
    void should_throw_exception_when_start_time_after_end_time() {
        // Given
        String resourceId = "resourceId";
        BookingDto bookingDto = BookingDto.builder()
                .resourceId(resourceId)
                .startTime(LocalDateTime.now().plusHours(2).toString())
                .endTime(LocalDateTime.now().toString())
                .build();

        Resource resource = Resource.builder()
                .id(resourceId)
                .availability("AVAILABLE")
                .build();

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // Then
        assertThrows(IllegalArgumentException.class, () -> bookingService.bookResource(bookingDto));
    }

    @Test
    void should_throw_exception_when_booking_times_overlap() {
        // Given
        String resourceId = "resourceId";
        BookingDto bookingDto = BookingDto.builder()
                .resourceId(resourceId)
                .startTime(LocalDateTime.now().plusHours(2).toString())
                .endTime(LocalDateTime.now().plusHours(4).toString())
                .build();

        Resource resource = Resource.builder()
                .id(resourceId)
                .availability("AVAILABLE")
                .build();

        Booking existingBooking = Booking.builder()
                .startTime(LocalDateTime.now().plusHours(3))
                .endTime(LocalDateTime.now().plusHours(5))
                .resource(resource)
                .build();

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        when(bookingRepository.findAllByResource(resource)).thenReturn(List.of(existingBooking));

        // Then
        assertThrows(IllegalArgumentException.class, () -> bookingService.bookResource(bookingDto));
    }

    @Test
    void should_successfully_delete_a_booking() {
        // Given
        String bookingId = "bookingId";

        Booking booking = Booking.builder()
                .id(bookingId)
                .build();

        Resource resource = Resource.builder()
                .id("resourceId")
                .availability("BOOKED")
                .build();

        booking.setResource(resource);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        // Act
        bookingService.deleteBooking(bookingId);

        // Assert
        verify(bookingRepository, times(1)).delete(booking);
        assertEquals("AVAILABLE", resource.getAvailability());
    }

    @Test
    void should_throw_exception_when_deleting_non_existent_booking() {
        // Given
        String bookingId = "bookingId";

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> bookingService.deleteBooking(bookingId));
    }

    @Test
    void should_throw_exception_when_start_time_and_end_time_are_the_same() {
        // Given
        String resourceId = "resourceId";
        LocalDateTime now = LocalDateTime.now().plusHours(1);
        BookingDto bookingDto = BookingDto.builder()
                .resourceId(resourceId)
                .startTime(now.toString())
                .endTime(now.toString()) // set start time and end time to the same time
                .build();

        Resource resource = Resource.builder()
                .id(resourceId)
                .availability("AVAILABLE")
                .build();

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // Then
        assertThrows(IllegalArgumentException.class, () -> bookingService.bookResource(bookingDto));
    }

}