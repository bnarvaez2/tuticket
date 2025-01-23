package com.doublevpartners.tutickets.service.impl;

import static com.doublevpartners.tutickets.util.Constants.USER_NOT_FOUND_WITH_ID;

import com.doublevpartners.tutickets.dto.request.UserRequestDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import com.doublevpartners.tutickets.exception.NotFoundException;
import com.doublevpartners.tutickets.facade.UserFacade;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import com.doublevpartners.tutickets.service.UserService;
import com.doublevpartners.tutickets.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private final UserFacade userFacade;

  public UserServiceImpl(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  public void createUser(UserRequestDTO user) {
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(user);
    userFacade.saveUser(userEntity);
  }

  public void updateUser(UUID id, UserRequestDTO userDetails) {
    UserEntity user = userFacade.findUserById(id)
      .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_WITH_ID + id));

    user.setName(userDetails.getName());
    user.setLastname(userDetails.getLastname());
    userFacade.saveUser(user);
  }

  public List<UserResponseDTO> getAllUsers() {
    List<UserEntity> users = userFacade.getAllUsers();

    return UserMapper.INSTANCE.toResponseDTO(users);
  }

  public UserResponseDTO getUserById(UUID id) {
    UserEntity user = userFacade.findUserById(id)
      .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_WITH_ID + id));

    return UserMapper.INSTANCE.toResponseDTO(user);
  }
}
