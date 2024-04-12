package sliit.af.assignment.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "resources")
@Data
@Builder
public class Resource {

    @Id
    private String id;
    private String resourceName;
    private String availability;

}
