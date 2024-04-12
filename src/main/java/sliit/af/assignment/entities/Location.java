package sliit.af.assignment.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "locations")
@Data
@Builder
public class Location {

    @Id
    private String id;
    private String locationName;
    private String availability;
    private int capacity;

}
