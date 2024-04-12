package sliit.af.assignment.mappers;

import sliit.af.assignment.dtos.BookingDto;
import sliit.af.assignment.entities.Booking;

public class BookingMapper {

    public static BookingDto mapToBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .startTime(booking.getStartTime().toString())
                .endTime(booking.getEndTime().toString())
                .resourceId(booking.getResource().getId())
                .build();
    }

}
