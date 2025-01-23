package com.doublevpartners.tutickets.service;

import com.doublevpartners.tutickets.dto.request.UserRequestDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {

  void createUser(UserRequestDTO user);

  void updateUser(UUID id, UserRequestDTO userDetails);

  List<UserResponseDTO> getAllUsers();

  UserResponseDTO getUserById(UUID id);
}
