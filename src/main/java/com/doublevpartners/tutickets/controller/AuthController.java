package com.doublevpartners.tutickets.controller;

import static com.doublevpartners.tutickets.util.Constants.PATH_AUTH;

import com.doublevpartners.tutickets.dto.request.AuthenticationRequestDTO;
import com.doublevpartners.tutickets.exception.UnathorizedException;
import com.doublevpartners.tutickets.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PATH_AUTH)
@Tag(name = "Authentication API", description = "Operations related to authentication")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
    UserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }


  @Operation(summary = "Create an authentication token")
  @PostMapping()
  public Map<String, String> createAuthenticationToken(
    @RequestBody AuthenticationRequestDTO authenticationRequest) {

    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
          authenticationRequest.getPassword())
      );
    } catch (Exception e) {
      throw new UnathorizedException();
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(
      authenticationRequest.getUsername());
    Map<String, String> body = new HashMap<>();
    body.put("token", jwtUtil.generateToken(userDetails.getUsername()));

    return body;
  }
}

