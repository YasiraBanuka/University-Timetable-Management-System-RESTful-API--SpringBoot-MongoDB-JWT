package sliit.af.assignment.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDto {
    private String id;
    private String startTime;
    private String endTime;
    private String resourceId;
}
