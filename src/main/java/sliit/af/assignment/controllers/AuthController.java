package sliit.af.assignment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sliit.af.assignment.dtos.SignUpResponseDto;
import sliit.af.assignment.dtos.UserAuthDto;
import sliit.af.assignment.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody UserAuthDto signUpRequest) {
        SignUpResponseDto response = authService.signUp(signUpRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserAuthDto> signIn(@RequestBody UserAuthDto signInRequest) {
        UserAuthDto response = authService.signIn(signInRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserAuthDto> refreshToken(@RequestBody UserAuthDto refreshTokenRequest) {
        UserAuthDto response = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(response);
    }

}
