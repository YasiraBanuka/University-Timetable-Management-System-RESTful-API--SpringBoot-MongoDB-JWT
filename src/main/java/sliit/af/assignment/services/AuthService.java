package sliit.af.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sliit.af.assignment.dtos.SignUpResponseDto;
import sliit.af.assignment.dtos.UserAuthDto;
import sliit.af.assignment.entities.User;
import sliit.af.assignment.repositories.UserRepository;
import sliit.af.assignment.utils.JwtUtils;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public SignUpResponseDto signUp(UserAuthDto signUpRequest) {
        SignUpResponseDto request = new SignUpResponseDto();

        try {
            User user = new User();
            user.setName(signUpRequest.getName());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setRole(signUpRequest.getRole());
            User savedUser = userRepository.save(user);

            if (savedUser != null) {
                request.setId(savedUser.getId());
                request.setName(savedUser.getName());
                request.setEmail(savedUser.getEmail());
                request.setPassword(savedUser.getPassword());
                request.setRole(savedUser.getRole());
                request.setMessage(user.getRole() + " registered successfully!");
                System.out.println(user.getRole() + " registered successfully!");
            } else {
                System.out.println("User registration failed!");
            }
        } catch (Exception e) {
            request.setError(e.getMessage());
            System.out.println(e.getMessage());
        }
        return request;
    }

    public UserAuthDto signIn(UserAuthDto signInRequest) {
        UserAuthDto response = new UserAuthDto();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getEmail(), signInRequest.getPassword())
            );
            var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                    () -> new Exception("User not found with email: " + signInRequest.getEmail())
            );
            System.out.println("User found: " + user);

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24 Hrs");
            response.setMessage(user.getRole() + " signed in successfully!");
            System.out.println(user.getRole() + " signed in successfully!");
        } catch (Exception e) {
            response.setError(e.getMessage());
            System.out.println(e.getMessage());
        }
        return response;
    }

    public UserAuthDto refreshToken(UserAuthDto refreshTokenRequest) {
        UserAuthDto response = new UserAuthDto();

        String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found with email: " + email)
        );

        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24 Hrs");
            response.setMessage("Token refreshed successfully!");
        } else {
            response.setError("Token is invalid!");
            System.out.println("Token is invalid!");
        }

        return response;
    }

}
