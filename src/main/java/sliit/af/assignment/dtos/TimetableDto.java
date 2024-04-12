package sliit.af.assignment.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimetableDto {
    private String id;
    private String startTime;
    private String endTime;
    private String faculty;
    private String locationId;
    private String courseId;
}
