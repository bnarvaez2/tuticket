package com.doublevpartners.tutickets.service;

import com.doublevpartners.tutickets.dto.request.AuthenticationRequestDTO;
import com.doublevpartners.tutickets.exception.UnathorizedException;
import com.doublevpartners.tutickets.service.impl.AuthServiceImp;
import com.doublevpartners.tutickets.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private UserDetailsService userDetailsService;

  @InjectMocks
  private AuthServiceImp authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void generateToken_Success() {
    AuthenticationRequestDTO request = new AuthenticationRequestDTO("testUser", "testPassword");
    List<String> roles = List.of("ROLE_USER");
    UserDetails userDetails = mock(UserDetails.class);

    when(userDetails.getUsername()).thenReturn("testUser");
    when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
    when(jwtUtil.generateToken("testUser", roles)).thenReturn("mockedToken");

    String token = authService.generateToken(request, roles);

    assertEquals("mockedToken", token);

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userDetailsService).loadUserByUsername("testUser");
    verify(jwtUtil).generateToken("testUser", roles);
  }

  @Test
  void generateToken_Failure_InvalidCredentials() {
    AuthenticationRequestDTO request = new AuthenticationRequestDTO("invalidUser", "wrongPassword");
    List<String> roles = List.of("ROLE_USER");

    doThrow(new RuntimeException("Invalid credentials")).when(authenticationManager)
      .authenticate(any(UsernamePasswordAuthenticationToken.class));

    assertThrows(UnathorizedException.class, () -> authService.generateToken(request, roles));

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verifyNoInteractions(userDetailsService);
    verifyNoInteractions(jwtUtil);
  }

  @Test
  void generateToken_Failure_UserNotFound() {
    AuthenticationRequestDTO request = new AuthenticationRequestDTO("nonExistentUser", "password");
    List<String> roles = List.of("ROLE_USER");

    when(userDetailsService.loadUserByUsername("nonExistentUser"))
      .thenThrow(new RuntimeException("User not found"));

    assertThrows(RuntimeException.class, () -> authService.generateToken(request, roles));

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userDetailsService).loadUserByUsername("nonExistentUser");
    verifyNoInteractions(jwtUtil);
  }
}
