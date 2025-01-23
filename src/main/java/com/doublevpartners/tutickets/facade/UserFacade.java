package com.doublevpartners.tutickets.facade;

import com.doublevpartners.tutickets.repository.UserRepository;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Component;
import java.util.UUID;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserFacade {

  private final UserRepository userRepository;

  public UserFacade(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UserEntity saveUser(UserEntity user) {
    return userRepository.save(user);
  }

  public Optional<UserEntity> findUserById(UUID id) {
    return userRepository.findById(id);
  }

  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }
}

