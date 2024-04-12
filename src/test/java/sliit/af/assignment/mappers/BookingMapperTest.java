package sliit.af.assignment.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sliit.af.assignment.dtos.BookingDto;
import sliit.af.assignment.entities.Booking;
import sliit.af.assignment.entities.Resource;

import java.time.LocalDateTime;

class BookingMapperTest {

    private BookingMapper bookingMapper;

    @BeforeEach
    void setUp() {
        bookingMapper = new BookingMapper();
    }

    @Test
    public void shouldMapToBookingDto() {
        // Given
        Booking booking = Booking.builder()
                .id("1L")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .resource(Resource.builder().id("1L").build())
                .build();

        // When
        BookingDto bookingDto = bookingMapper.mapToBookingDto(booking);

        // Then
        assertEquals(bookingDto.getId(), "1L");
        assertEquals(bookingDto.getStartTime(), booking.getStartTime().toString());
        assertEquals(bookingDto.getEndTime(), booking.getEndTime().toString());
        assertEquals(bookingDto.getResourceId(), booking.getResource().getId());
    }

}