package com.ala.book.auth;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;



    @Test
    void register_ShouldCallServiceAndReturnAccepted() throws MessagingException {
        // Arrange
        RegistrationRequest request = RegistrationRequest.builder().build();
        doNothing().when(authenticationService).register(request);

        // Act
        ResponseEntity<?> response = authenticationController.register(request);

        // Assert
        verify(authenticationService, times(1)).register(request);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void authenticate_ShouldReturnAuthenticationResponse() throws MessagingException {
        // Arrange
        AuthenticationRequest request = AuthenticationRequest.builder().build();
        AuthenticationResponse responseMock = AuthenticationResponse.builder().build();
        when(authenticationService.authenticate(request)).thenReturn(responseMock);

        // Act
        ResponseEntity<AuthenticationResponse> response = authenticationController.authenticate(request);

        // Assert
        verify(authenticationService, times(1)).authenticate(request);
        assertEquals(responseMock, response.getBody());
    }

    @Test
    void confirm_ShouldCallActivateAccount() throws MessagingException {
        // Arrange
        String token = "sample-token";
        doNothing().when(authenticationService).activateAccount(token);

        // Act
        authenticationController.confirm(token);

        // Assert
        verify(authenticationService, times(1)).activateAccount(token);
    }

    @Test
    void resentConfirmationToken_ShouldCallServiceAndReturnAccepted() throws MessagingException {
        // Arrange
        String email = "test@example.com";
        doNothing().when(authenticationService).resentConfirmationToken(email);

        // Act
        ResponseEntity<?> response = authenticationController.resentConfirmationToken(email);

        // Assert
        verify(authenticationService, times(1)).resentConfirmationToken(email);
        assertEquals(ResponseEntity.accepted().build(), response);
    }
}
