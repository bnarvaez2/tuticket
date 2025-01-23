package com.doublevpartners.tutickets.service.impl;

import static com.doublevpartners.tutickets.util.Constants.TICKED_NOT_FOUND_WITH_ID;

import com.doublevpartners.tutickets.dto.response.PageResponseDTO;
import com.doublevpartners.tutickets.exception.NotFoundException;
import com.doublevpartners.tutickets.util.EstatusEnum;
import com.doublevpartners.tutickets.dto.request.TicketRequestDTO;
import com.doublevpartners.tutickets.dto.response.TicketResponseDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import com.doublevpartners.tutickets.facade.TicketFacade;
import com.doublevpartners.tutickets.repository.entity.TicketEntity;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import com.doublevpartners.tutickets.service.TicketService;
import com.doublevpartners.tutickets.service.UserService;
import com.doublevpartners.tutickets.service.mapper.TicketMapper;
import com.doublevpartners.tutickets.service.mapper.UserMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketFacade ticketFacade;
  private final UserService userService;

  public UUID createTicket(TicketRequestDTO ticket) {
    UserResponseDTO userDTO = userService.getUserById(UUID.fromString(ticket.getUserId()));
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDTO);
    TicketEntity ticketEntity = TicketMapper.INSTANCE.toEntity(ticket, userEntity);
    return ticketFacade.saveTicket(ticketEntity).getId();
  }

  public void updateTicket(UUID id, TicketRequestDTO ticketDetails) {
    TicketEntity ticketEntity = ticketFacade.findTicketById(id)
      .orElseThrow(() -> new NotFoundException(TICKED_NOT_FOUND_WITH_ID + id));

    UserResponseDTO userDTO = userService.getUserById(UUID.fromString(ticketDetails.getUserId()));
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDTO);
    ticketEntity.setEstatus(EstatusEnum.valueOf(ticketDetails.getEstatus()));
    ticketEntity.setUser(userEntity);
    ticketEntity.setUpdateDate(LocalDateTime.now());
    ticketFacade.saveTicket(ticketEntity);
  }

  public void deleteTicket(UUID id) {
    TicketEntity ticketEntity = ticketFacade.findTicketById(id)
      .orElseThrow(() -> new NotFoundException(TICKED_NOT_FOUND_WITH_ID + id));

    ticketFacade.deleteTicket(ticketEntity.getId());
  }

  public TicketResponseDTO getTicketById(UUID id) {
    TicketEntity ticketEntity = ticketFacade.findTicketById(id)
      .orElseThrow(() -> new NotFoundException(TICKED_NOT_FOUND_WITH_ID + id));

    UserResponseDTO userDTO = userService.getUserById(ticketEntity.getUser().getId());

    return TicketMapper.INSTANCE.toResponseDTO(ticketEntity, userDTO);
  }

  public PageResponseDTO<TicketResponseDTO> getAllTickets(Pageable pageable) {
    Page<TicketEntity> tickets = ticketFacade.findAllTickets(pageable);
    Page<TicketResponseDTO> ticketDTOs = tickets.map(ticket -> {
      UserResponseDTO userDTO = UserMapper.INSTANCE.toResponseDTO(ticket.getUser());
      return TicketMapper.INSTANCE.toResponseDTO(ticket, userDTO);
    });

    return new PageResponseDTO<>(ticketDTOs);
  }

  public List<TicketResponseDTO> getTicketsByStatus(EstatusEnum estatus) {
    List<TicketEntity> tickets = ticketFacade.findTicketsByEstatus(estatus);

    return TicketMapper.INSTANCE.toResponseDTO(tickets);
  }

  public List<TicketResponseDTO> getTicketsByUser(UUID userId) {
    UserResponseDTO userDTO = userService.getUserById(userId);
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDTO);
    List<TicketEntity> tickets = ticketFacade.findTicketsByUser(userEntity);

    return TicketMapper.INSTANCE.toResponseDTO(tickets);
  }

  public List<TicketResponseDTO> getTicketsByStatusAndUser(EstatusEnum estatus, UUID userId) {
    List<TicketEntity> tickets = ticketFacade.findTicketsByStatusAndUser(estatus, userId);

    return TicketMapper.INSTANCE.toResponseDTO(tickets);
  }

}
