package com.doublevpartners.tutickets.service;

import static com.doublevpartners.tutickets.util.Constants.TICKED_NOT_FOUND_WITH_ID;
import static com.doublevpartners.tutickets.util.Constants.USER_NOT_FOUND_WITH_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.doublevpartners.tutickets.dto.response.PageResponseDTO;
import com.doublevpartners.tutickets.exception.NotFoundException;
import com.doublevpartners.tutickets.util.EstatusEnum;
import com.doublevpartners.tutickets.dto.request.TicketRequestDTO;
import com.doublevpartners.tutickets.dto.response.TicketResponseDTO;
import com.doublevpartners.tutickets.dto.response.UserResponseDTO;
import com.doublevpartners.tutickets.facade.TicketFacade;
import com.doublevpartners.tutickets.repository.entity.TicketEntity;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import com.doublevpartners.tutickets.service.impl.TicketServiceImpl;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class TicketServiceTest {

  @Mock
  private TicketFacade ticketFacade;

  @Mock
  private UserService userService;

  @InjectMocks
  private TicketServiceImpl ticketService;

  private TicketEntity ticketEntity;
  private TicketRequestDTO ticketRequestDTO;
  private TicketResponseDTO ticketResponseDTO;
  private UserResponseDTO userResponseDTO;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    ticketEntity = new TicketEntity();
    ticketEntity.setId(UUID.randomUUID());
    ticketEntity.setEstatus(EstatusEnum.ABIERTO);
    ticketEntity.setUser(UserEntity
      .builder()
      .id(UUID.randomUUID())
      .name("Brian")
      .lastname("Narvaez")
      .build());

    ticketRequestDTO = new TicketRequestDTO();
    ticketRequestDTO.setUserId(UUID.randomUUID().toString());
    ticketRequestDTO.setEstatus(EstatusEnum.ABIERTO.name());

    userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(UUID.fromString(ticketRequestDTO.getUserId()));
    userResponseDTO.setName("Brian");
    userResponseDTO.setLastname("Narvaez");

    ticketResponseDTO = new TicketResponseDTO();
    ticketResponseDTO.setId(ticketEntity.getId());
    ticketResponseDTO.setUser(userResponseDTO);
    ticketResponseDTO.setEstatus(EstatusEnum.ABIERTO);
  }

  @Test
  public void testCreateTicket() {
    when(userService.getUserById(any(UUID.class))).thenReturn(userResponseDTO);
    when(ticketFacade.saveTicket(any(TicketEntity.class))).thenReturn(ticketEntity);

    ticketService.createTicket(ticketRequestDTO);

    verify(ticketFacade, times(1)).saveTicket(any(TicketEntity.class));
  }

  @Test
  public void testCreateTicket_throwNotFoundUser() {
    when(userService.getUserById(any(UUID.class))).thenThrow(
      new NotFoundException(USER_NOT_FOUND_WITH_ID));

    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> ticketService.createTicket(ticketRequestDTO));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(USER_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testUpdateTicket() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.of(ticketEntity));
    when(userService.getUserById(any(UUID.class))).thenReturn(userResponseDTO);
    when(ticketFacade.saveTicket(any(TicketEntity.class))).thenReturn(ticketEntity);

    ticketService.updateTicket(ticketEntity.getId(), ticketRequestDTO);

    verify(ticketFacade, times(1)).saveTicket(any(TicketEntity.class));
  }

  @Test
  public void testUpdateTicket_throwNotFoundException() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.empty());

    UUID id = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> ticketService.updateTicket(id, ticketRequestDTO));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(TICKED_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testUpdateTicket_throwUserNotFoundException() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.of(ticketEntity));
    when(userService.getUserById(any(UUID.class))).thenThrow(
      new NotFoundException(USER_NOT_FOUND_WITH_ID));

    UUID id = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> ticketService.updateTicket(id, ticketRequestDTO));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(USER_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testDeleteTicket() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.of(ticketEntity));
    doNothing().when(ticketFacade).deleteTicket(any(UUID.class));

    ticketService.deleteTicket(ticketEntity.getId());

    verify(ticketFacade, times(1)).deleteTicket(ticketEntity.getId());
  }

  @Test
  public void testDeleteTicket_throwNotFoundException() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.empty());

    UUID id = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> ticketService.deleteTicket(id));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(TICKED_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testGetTicketById() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.of(ticketEntity));
    when(userService.getUserById(any(UUID.class))).thenReturn(userResponseDTO);

    TicketResponseDTO result = ticketService.getTicketById(ticketEntity.getId());

    assertNotNull(result);
    assertEquals(ticketResponseDTO.getEstatus(), result.getEstatus());
    assertEquals(ticketResponseDTO.getUser().getLastname(), result.getUser().getLastname());
  }

  @Test
  public void testGetTicketById_throwNotFoundException() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.empty());

    UUID id = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> ticketService.getTicketById(id));


    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(TICKED_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testGetTicketById_throwUserNotFoundException() {
    when(ticketFacade.findTicketById(any(UUID.class))).thenReturn(Optional.of(ticketEntity));
    when(userService.getUserById(any(UUID.class)))
      .thenThrow(new NotFoundException(USER_NOT_FOUND_WITH_ID));

    UUID id = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> ticketService.getTicketById(id));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(USER_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testGetAllTickets() {
    when(ticketFacade.findAllTickets(any(Pageable.class))).thenReturn(
      new PageImpl<>(List.of(ticketEntity)));

    PageResponseDTO<TicketResponseDTO> result = ticketService.getAllTickets(PageRequest.of(0, 10));

    assertNotNull(result);
    assertFalse(result.getContent().isEmpty());
    assertEquals(ticketResponseDTO.getEstatus(), result.getContent().get(0).getEstatus());
    assertEquals(ticketResponseDTO.getUser().getName(),
      result.getContent().get(0).getUser().getName());
  }

  @Test
  public void testGetAllTickets_returnEmptyContent() {
    when(ticketFacade.findAllTickets(any(Pageable.class)))
      .thenReturn(new PageImpl<>(Collections.emptyList()));

    PageResponseDTO<TicketResponseDTO> result = ticketService.getAllTickets(PageRequest.of(0, 10));

    assertNotNull(result);
    assertTrue(result.getContent().isEmpty());
  }

  @Test
  public void testGetTicketsByStatus() {
    when(ticketFacade.findTicketsByEstatus(any(EstatusEnum.class))).thenReturn(
      List.of(ticketEntity));

    List<TicketResponseDTO> result = ticketService.getTicketsByStatus(EstatusEnum.ABIERTO);

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(ticketResponseDTO.getEstatus(), result.get(0).getEstatus());
    assertEquals(ticketResponseDTO.getUser().getName(), result.get(0).getUser().getName());
  }

  @Test
  public void testGetTicketsByStatus_returnEmptyList() {
    when(ticketFacade.findTicketsByEstatus(any(EstatusEnum.class)))
      .thenReturn(Collections.emptyList());

    List<TicketResponseDTO> result = ticketService.getTicketsByStatus(EstatusEnum.ABIERTO);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testGetTicketsByUser() {
    when(userService.getUserById(any(UUID.class))).thenReturn(userResponseDTO);
    when(ticketFacade.findTicketsByUser(any(UserEntity.class))).thenReturn(List.of(ticketEntity));

    List<TicketResponseDTO> result = ticketService.getTicketsByUser(userResponseDTO.getId());

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(ticketResponseDTO.getEstatus(), result.get(0).getEstatus());
    assertEquals(ticketResponseDTO.getUser().getName(), result.get(0).getUser().getName());
  }

  @Test
  public void testGetTicketsByUser_returnEmptyList() {
    when(userService.getUserById(any(UUID.class))).thenReturn(userResponseDTO);
    when(ticketFacade.findTicketsByUser(any(UserEntity.class))).thenReturn(Collections.emptyList());

    List<TicketResponseDTO> result = ticketService.getTicketsByUser(userResponseDTO.getId());

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testGetTicketsByUser_throwUserNotFoundException() {
    when(userService.getUserById(any(UUID.class)))
      .thenThrow(new NotFoundException(USER_NOT_FOUND_WITH_ID));

    UUID id = UUID.randomUUID();
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
      () -> ticketService.getTicketsByUser(id));

    String actualMessage = notFoundException.getMessage();

    assertTrue(actualMessage.contains(USER_NOT_FOUND_WITH_ID));
  }

  @Test
  public void testGetTicketsByStatusAndUser() {
    when(ticketFacade.findTicketsByStatusAndUser(any(EstatusEnum.class), any(UUID.class)))
      .thenReturn(List.of(ticketEntity));

    List<TicketResponseDTO> result = ticketService.getTicketsByStatusAndUser(EstatusEnum.ABIERTO,
      userResponseDTO.getId());

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(ticketResponseDTO.getEstatus(), result.get(0).getEstatus());
    assertEquals(ticketResponseDTO.getUser().getName(), result.get(0).getUser().getName());
  }

  @Test
  public void testGetTicketsByStatusAndUser_returnEmptyList() {
    when(ticketFacade.findTicketsByStatusAndUser(any(EstatusEnum.class), any(UUID.class)))
      .thenReturn(Collections.emptyList());

    List<TicketResponseDTO> result = ticketService.getTicketsByStatusAndUser(EstatusEnum.ABIERTO,
      userResponseDTO.getId());

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}
