package com.doublevpartners.tutickets.service;

import static com.doublevpartners.tutickets.util.Constants.USER_NOT_FOUND_WITH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.doublevpartners.tutickets.dto.request.UserRequestDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import com.doublevpartners.tutickets.exception.NotFoundException;
import com.doublevpartners.tutickets.facade.UserFacade;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import com.doublevpartners.tutickets.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.UUID;
import java.util.List;


public class UserServiceTest {

  @Mock
  private UserFacade userFacade;

  @InjectMocks
  private UserServiceImpl userService;

  private UserEntity userEntity;
  private UserRequestDTO userRequestDTO;
  private UserResponseDTO userResponseDTO;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userEntity = new UserEntity();
    userEntity.setId(UUID.randomUUID());
    userEntity.setName("Brian");
    userEntity.setLastname("Narvaez");

    userRequestDTO = new UserRequestDTO();
    userRequestDTO.setName("Brian de Jesus");
    userRequestDTO.setLastname("Narvaez Arias");

    userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(userEntity.getId());
    userResponseDTO.setName("Brian");
    userResponseDTO.setLastname("Narvaez");
  }

  @Test
  public void testCreateUser() {
    when(userFacade.saveUser(any(UserEntity.class))).thenReturn(userEntity);

    userService.createUser(userRequestDTO);

    verify(userFacade, times(1)).saveUser(any(UserEntity.class));
  }

  @Test
  public void testUpdateUser() {
    when(userFacade.findUserById(any(UUID.class))).thenReturn(Optional.of(userEntity));
    when(userFacade.saveUser(any(UserEntity.class))).thenReturn(userEntity);

    userService.updateUser(userEntity.getId(), userRequestDTO);

    verify(userFacade, times(1)).saveUser(any(UserEntity.class));
  }

  @Test
  public void testUpdateUser_throwNotFounException() {
    when(userFacade.findUserById(any(UUID.class))).thenReturn(Optional.empty());

    UUID randomUUID = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> userService.updateUser(randomUUID, userRequestDTO));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(USER_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testGetAllUsers() {
    when(userFacade.getAllUsers()).thenReturn(List.of(userEntity));

    List<UserResponseDTO> result = userService.getAllUsers();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(userResponseDTO.getName(), result.get(0).getName());
    assertEquals(userResponseDTO.getLastname(), result.get(0).getLastname());
  }

  @Test
  public void testGetUserById() {
    when(userFacade.findUserById(any(UUID.class))).thenReturn(Optional.of(userEntity));

    UserResponseDTO result = userService.getUserById(userEntity.getId());

    assertNotNull(result);
    assertEquals(userResponseDTO.getName(), result.getName());
    assertEquals(userResponseDTO.getLastname(), result.getLastname());
  }

  @Test
  public void testGetUserById_throwNotFounException() {
    when(userFacade.findUserById(any(UUID.class))).thenReturn(Optional.empty());

    UUID randomUUID = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> userService.getUserById(randomUUID));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(USER_NOT_FOUND_WITH_ID));
  }
}
