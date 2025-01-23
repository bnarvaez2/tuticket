package com.doublevpartners.tutickets.service.mapper;

import com.doublevpartners.tutickets.dto.request.TicketRequestDTO;
import com.doublevpartners.tutickets.dto.response.TicketResponseDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import com.doublevpartners.tutickets.repository.entity.TicketEntity;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {
  TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", source = "userEntity")
  TicketEntity toEntity(TicketRequestDTO ticketRequestDTO, UserEntity userEntity);

  @Mapping(target = "user", source = "userDetail")
  @Mapping(target = "id", source = "ticketEntity.id")
  @Mapping(target = "creationDate", source = "ticketEntity.creationDate")
  @Mapping(target = "updateDate", source = "ticketEntity.updateDate")
  TicketResponseDTO toResponseDTO(TicketEntity ticketEntity, UserResponseDTO userDetail);

  TicketResponseDTO toResponseDTO(TicketEntity ticketEntity);

  List<TicketResponseDTO> toResponseDTO(List<TicketEntity> tickets);
}


