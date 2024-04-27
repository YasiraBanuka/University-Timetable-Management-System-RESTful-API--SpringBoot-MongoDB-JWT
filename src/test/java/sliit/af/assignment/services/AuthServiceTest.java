package sliit.af.assignment.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import sliit.af.assignment.dtos.SignUpResponseDto;
import sliit.af.assignment.dtos.UserAuthDto;
import sliit.af.assignment.entities.User;
import sliit.af.assignment.repositories.UserRepository;
import sliit.af.assignment.utils.JwtUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUpWithValidData() {
        // Given
        UserAuthDto signUpRequest = new UserAuthDto();
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setName("Test User");
        signUpRequest.setPassword("password");
        signUpRequest.setRole("STUDENT");

        // When
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Then
        SignUpResponseDto response = authService.signUp(signUpRequest);

        assertEquals("Test User", response.getName());
        assertEquals("test@test.com", response.getEmail());
        assertEquals("encodedPassword", response.getPassword());
        assertEquals("STUDENT", response.getRole());
        assertNull(response.getError());
    }

    @Test
    public void signUpWithInvalidEmail() {
        // Given
        UserAuthDto signUpRequest = new UserAuthDto();
        signUpRequest.setEmail("invalidEmail");
        signUpRequest.setName("Test User");
        signUpRequest.setPassword("password");
        signUpRequest.setRole("STUDENT");

        // When
        SignUpResponseDto response = authService.signUp(signUpRequest);

        // Then
        assertEquals("Invalid email format!", response.getError());
    }

    @Test
    public void signUpWithShortPassword() {
        UserAuthDto signUpRequest = new UserAuthDto();
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setName("Test User");
        signUpRequest.setPassword("pass");
        signUpRequest.setRole("STUDENT");

        SignUpResponseDto response = authService.signUp(signUpRequest);

        assertEquals("Invalid password. It must contain at least 6 characters!", response.getError());
    }

    @Test
    public void signInWithInvalidEmail() {
        UserAuthDto signInRequest = new UserAuthDto();
        signInRequest.setEmail("invalid@test.com");
        signInRequest.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UserAuthDto response = authService.signIn(signInRequest);

        assertEquals("User not found with email: invalid@test.com", response.getError());
    }

    @Test
    public void refreshTokenWithValidToken() {
        UserAuthDto refreshTokenRequest = new UserAuthDto();
        refreshTokenRequest.setToken("validToken");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole("USER");

        when(jwtUtils.extractUsername(anyString())).thenReturn("test@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtUtils.isTokenValid(anyString(), ArgumentMatchers.any(User.class))).thenReturn(true);
        when(jwtUtils.generateToken(ArgumentMatchers.any(User.class))).thenReturn("newToken");

        UserAuthDto response = authService.refreshToken(refreshTokenRequest);

        assertEquals("newToken", response.getToken());
        assertEquals("validToken", response.getRefreshToken());
        assertNull(response.getError());
    }

    @Test
    public void refreshTokenWithInvalidToken() {
        UserAuthDto refreshTokenRequest = new UserAuthDto();
        refreshTokenRequest.setToken("invalidToken");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole("USER");

        when(jwtUtils.extractUsername(anyString())).thenReturn("test@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtUtils.isTokenValid(anyString(), ArgumentMatchers.any(User.class))).thenReturn(false);

        UserAuthDto response = authService.refreshToken(refreshTokenRequest);

        assertEquals("Token is invalid!", response.getError());
    }

}
