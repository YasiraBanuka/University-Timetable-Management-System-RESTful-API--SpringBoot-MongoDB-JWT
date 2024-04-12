package sliit.af.assignment.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sliit.af.assignment.dtos.BookingDto;
import sliit.af.assignment.entities.Booking;
import sliit.af.assignment.entities.Resource;
import sliit.af.assignment.repositories.BookingRepository;
import sliit.af.assignment.repositories.ResourceRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

}