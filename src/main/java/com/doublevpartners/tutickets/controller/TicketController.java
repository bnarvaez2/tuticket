package com.doublevpartners.tutickets.controller;

import static com.doublevpartners.tutickets.util.Constants.PATH_ESTATUS;
import static com.doublevpartners.tutickets.util.Constants.PATH_TICKETS;
import static com.doublevpartners.tutickets.util.Constants.PATH_USER;
import static com.doublevpartners.tutickets.util.Constants.PATH_VARIABLE_ESTATUS;
import static com.doublevpartners.tutickets.util.Constants.PATH_VARIABLE_ID;
import static com.doublevpartners.tutickets.util.Constants.PATH_VARIABLE_USER_ID;
import static com.doublevpartners.tutickets.util.Constants.TICKET_CREATED_SUCCESSFULLY;
import static com.doublevpartners.tutickets.util.Constants.TICKET_DELETED_SUCCESSFULLY;
import static com.doublevpartners.tutickets.util.Constants.TICKET_UPDATED_SUCCESSFULLY;

import com.doublevpartners.tutickets.dto.request.TicketRequestDTO;
import com.doublevpartners.tutickets.dto.response.PageResponseDTO;
import com.doublevpartners.tutickets.dto.response.TicketResponseDTO;
import com.doublevpartners.tutickets.service.TicketService;
import com.doublevpartners.tutickets.util.EstatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(PATH_TICKETS)
@Tag(name = "Ticket API", description = "Operations related to tickets")
public class TicketController {

  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @Operation(summary = "Create a new ticket")
  @PostMapping
  public ResponseEntity<String> createTicket(
    @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
    UUID ticketId = ticketService.createTicket(ticketRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(TICKET_CREATED_SUCCESSFULLY + " Ticket ID: " + ticketId);
  }

  @Operation(summary = "Update an existing ticket by ID")
  @PutMapping(PATH_VARIABLE_ID)
  public ResponseEntity<String> updateTicket(
    @PathVariable UUID id,
    @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
    ticketService.updateTicket(id, ticketRequestDTO);
    return ResponseEntity.ok(TICKET_UPDATED_SUCCESSFULLY);
  }

  @Operation(summary = "Delete a tickets by ID")
  @DeleteMapping(PATH_VARIABLE_ID)
  public ResponseEntity<String> deleteTicket(@PathVariable UUID id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.ok(TICKET_DELETED_SUCCESSFULLY);
  }

  @Operation(summary = "Get a ticket by ID")
  @GetMapping(PATH_VARIABLE_ID)
  public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable UUID id) {
    TicketResponseDTO ticket = ticketService.getTicketById(id);
    return ResponseEntity.ok(ticket);
  }

  @Operation(summary = "Get all tickets with pagination")
  @GetMapping
  public ResponseEntity<PageResponseDTO<TicketResponseDTO>> getAllTickets(Pageable pageable) {
    PageResponseDTO<TicketResponseDTO> tickets = ticketService.getAllTickets(pageable);
    return ResponseEntity.ok(tickets);
  }

  @Operation(summary = "Get all tickets by estatus")
  @GetMapping(PATH_ESTATUS + PATH_VARIABLE_ESTATUS)
  public ResponseEntity<List<TicketResponseDTO>> getTicketsByStatus(
    @PathVariable EstatusEnum estatus) {
    List<TicketResponseDTO> tickets = ticketService.getTicketsByStatus(estatus);
    return ResponseEntity.ok(tickets);
  }

  @Operation(summary = "Get all tickets by user ID")
  @GetMapping(PATH_USER + PATH_VARIABLE_USER_ID)
  public ResponseEntity<List<TicketResponseDTO>> getTicketsByUser(@PathVariable UUID userId) {
    List<TicketResponseDTO> tickets = ticketService.getTicketsByUser(userId);
    return ResponseEntity.ok(tickets);
  }

  @Operation(summary = "Get all tickets by estatus and user ID")
  @GetMapping(PATH_ESTATUS + PATH_VARIABLE_ESTATUS + PATH_USER + PATH_VARIABLE_USER_ID)
  public ResponseEntity<List<TicketResponseDTO>> getTicketsByStatusAndUser(
    @PathVariable EstatusEnum estatus,
    @PathVariable UUID userId) {
    List<TicketResponseDTO> tickets = ticketService.getTicketsByStatusAndUser(estatus, userId);
    return ResponseEntity.ok(tickets);
  }
}
