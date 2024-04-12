package sliit.af.assignment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import sliit.af.assignment.entities.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthDto {

    private String name;
    private String email;
    private String password;
    private String role;
    private User users;

    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;

}
