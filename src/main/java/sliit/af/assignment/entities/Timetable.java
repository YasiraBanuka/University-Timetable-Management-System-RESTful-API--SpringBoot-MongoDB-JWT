package sliit.af.assignment.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "timetables")
@Data
@Builder
public class Timetable {

    @Id
    private String id;
    @Field(name = "start_time")
    private String startTime;
    @Field(name = "end_time")
    private String endTime;
    @Field(name = "faculty_name")
    private String faculty;

    private Location location;
    private Course course;

}
