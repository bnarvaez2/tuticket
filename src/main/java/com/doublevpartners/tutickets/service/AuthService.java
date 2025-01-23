package com.doublevpartners.tutickets.service;

import com.doublevpartners.tutickets.dto.request.AuthenticationRequestDTO;
import java.util.List;

public interface AuthService {

  String generateToken(AuthenticationRequestDTO authenticationRequest, List<String> roles);
}
