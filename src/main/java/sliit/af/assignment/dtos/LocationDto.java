package sliit.af.assignment.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {
    private String id;
    private String locationName;
    private String availability;
    private int capacity;

}
