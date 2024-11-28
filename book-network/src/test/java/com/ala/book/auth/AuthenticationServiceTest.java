package com.ala.book.auth;

import com.ala.book.email.EmailService;
import com.ala.book.email.EmailTemplateName;
import com.ala.book.role.Role;
import com.ala.book.role.RoleRepository;
import com.ala.book.security.JwtService;
import com.ala.book.user.Token;
import com.ala.book.user.TokenRepository;
import com.ala.book.user.User;
import com.ala.book.user.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService.activationUrl = "http://localhost:8080/activate";
    }

    @Test
    void register_ShouldSaveUserAndSendEmail() throws MessagingException {
        // Arrange
        RegistrationRequest request = new RegistrationRequest("John", "Doe", "john.doe@example.com", "password");
        Role userRole = Role.builder()
                .name("USER")
                .build();
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");

        // Act
        authenticationService.register(request);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendEmail(
                eq("bjaouiala3@gmail.com"),
                anyString(),
                eq(EmailTemplateName.ActivateAccount),
                eq("http://localhost:8080/activate"),
                anyString(),
                eq("Account Activation ")
        );
    }

    @Test
    void authenticate_ShouldReturnAuthenticationResponse() throws MessagingException {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("john.doe@example.com", "password");
        User user = User.builder().email(request.getEmail()).enabled(true).build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, request.getPassword(), List.of()));
        when(jwtService.generateToken(anyMap(), eq(user))).thenReturn("jwt-token");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(anyMap(), eq(user));
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void activateAccount_ShouldActivateUserWhenTokenIsValid() throws MessagingException {
        // Arrange
        String tokenValue = "valid-token";
        User user = User.builder().enabled(false).build();
        Token token = Token.builder()
                .token(tokenValue)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();
        when(tokenRepository.findByToken(tokenValue)).thenReturn(Optional.of(token));

        // Act
        authenticationService.activateAccount(tokenValue);

        // Assert
        assertTrue(user.isEnabled());
        verify(userRepository, times(1)).save(user);
        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    void activateAccount_ShouldResendEmailWhenTokenIsExpired() throws MessagingException {
        // Arrange
        String tokenValue = "expired-token";
        User user = User.builder().enabled(false).build();
        Token expiredToken = Token.builder()
                .token(tokenValue)
                .user(user)
                .expiresAt(LocalDateTime.now().minusMinutes(1))
                .build();
        when(tokenRepository.findByToken(tokenValue)).thenReturn(Optional.of(expiredToken));
        doNothing().when(emailService).sendEmail(anyString(), anyString(), any(), anyString(), anyString(), anyString());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> authenticationService.activateAccount(tokenValue));
        assertEquals("Activation token has expired: A new token has been sent", exception.getMessage());
        verify(emailService, times(1)).sendEmail(
                eq("bjaouiala3@gmail.com"),
                anyString(),
                eq(EmailTemplateName.ActivateAccount),
                eq("http://localhost:8080/activate"),
                anyString(),
                eq("Account Activation ")
        );
    }

    @Test
    void resentConfirmationToken_ShouldResendEmailIfUserIsNotEnabled() throws MessagingException {
        // Arrange
        String email = "john.doe@example.com";
        User user = User.builder().email(email).enabled(false).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        doNothing().when(emailService).sendEmail(anyString(), anyString(), any(), anyString(), anyString(), anyString());

        // Act
        authenticationService.resentConfirmationToken(email);

        // Assert
        verify(emailService, times(1)).sendEmail(
                eq("bjaouiala3@gmail.com"),
                anyString(),
                eq(EmailTemplateName.ActivateAccount),
                eq("http://localhost:8080/activate"),
                anyString(),
                eq("Account Activation ")
        );
    }
}
