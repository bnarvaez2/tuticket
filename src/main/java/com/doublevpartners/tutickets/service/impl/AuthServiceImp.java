package com.doublevpartners.tutickets.service.impl;

import com.doublevpartners.tutickets.dto.request.AuthenticationRequestDTO;
import com.doublevpartners.tutickets.exception.UnathorizedException;
import com.doublevpartners.tutickets.service.AuthService;
import com.doublevpartners.tutickets.util.JwtUtil;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public AuthServiceImp(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
    UserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public String generateToken(AuthenticationRequestDTO authenticationRequest, List<String> roles) {
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

    return jwtUtil.generateToken(userDetails.getUsername(), roles);
  }
}
