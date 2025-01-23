package com.doublevpartners.tutickets.controller;

import static com.doublevpartners.tutickets.util.Constants.PATH_USERS;
import static com.doublevpartners.tutickets.util.Constants.PATH_VARIABLE_ID;
import static com.doublevpartners.tutickets.util.Constants.USER_CREATED_SUCCESSFULLY;
import static com.doublevpartners.tutickets.util.Constants.USER_UPDATED_SUCCESSFULLY;
import com.doublevpartners.tutickets.dto.request.UserRequestDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import com.doublevpartners.tutickets.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(PATH_USERS)
@Tag(name = "User API", description = "Operations related to users.")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Create a new user")
  @PostMapping
  public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
    userService.createUser(userRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(USER_CREATED_SUCCESSFULLY);
  }

  @Operation(summary = "Update an existing user by ID")
  @PutMapping(PATH_VARIABLE_ID)
  public ResponseEntity<String> updateUser(
    @PathVariable UUID id,
    @Valid @RequestBody UserRequestDTO userRequestDTO) {
    userService.updateUser(id, userRequestDTO);
    return ResponseEntity.ok(USER_UPDATED_SUCCESSFULLY);
  }

  @Operation(summary = "Get all users")
  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    List<UserResponseDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @Operation(summary = "Get a user by ID")
  @GetMapping(PATH_VARIABLE_ID)
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
    UserResponseDTO user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }
}