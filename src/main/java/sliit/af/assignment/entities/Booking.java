package sliit.af.assignment.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "booking")
@Data
@Builder
public class Booking {

    @Id
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Resource resource;

}
