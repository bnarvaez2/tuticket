package com.doublevpartners.tutickets.controller;

import static com.doublevpartners.tutickets.util.Constants.PATH_ADMIN;
import static com.doublevpartners.tutickets.util.Constants.PATH_AUTH;

import com.doublevpartners.tutickets.dto.request.AuthenticationRequestDTO;
import com.doublevpartners.tutickets.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PATH_AUTH)
@Tag(name = "Authentication API", description = "Operations related to authentication")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @Operation(summary = "Create an authentication token")
  @PostMapping()
  public Map<String, String> createAuthenticationToken(
    @RequestBody AuthenticationRequestDTO authenticationRequest) {
    Map<String, String> body = new HashMap<>();
    List<String> roles = List.of("ROLE_USER");
    body.put("token", authService.generateToken(authenticationRequest, roles));

    return body;
  }

  @Operation(summary = "Create an authentication admin token")
  @PostMapping(PATH_ADMIN)
  public Map<String, String> createAuthenticationAdminToken(
    @RequestBody AuthenticationRequestDTO authenticationRequest) {
    Map<String, String> body = new HashMap<>();
    List<String> roles = List.of("ROLE_ADMIN");
    body.put("token", authService.generateToken(authenticationRequest, roles));

    return body;
  }
}

