package com.doublevpartners.tutickets.service.mapper;

import com.doublevpartners.tutickets.dto.request.UserRequestDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "creationDate", ignore = true)
  @Mapping(target = "updateDate", ignore = true)
  UserEntity toEntity(UserRequestDTO userRequestDTO);

  UserEntity toEntity(UserResponseDTO userResponseDTO);

  UserResponseDTO toResponseDTO(UserEntity userEntity);

  List<UserResponseDTO> toResponseDTO(List<UserEntity> userEntities);
}

