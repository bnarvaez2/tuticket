package com.doublevpartners.tutickets.controller;

import com.doublevpartners.tutickets.dto.request.TicketRequestDTO;
import com.doublevpartners.tutickets.util.EstatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.doublevpartners.tutickets.util.Constants.INCLINED_BAR;
import static com.doublevpartners.tutickets.util.Constants.PATH_ESTATUS;
import static com.doublevpartners.tutickets.util.Constants.PATH_TICKETS;
import static com.doublevpartners.tutickets.util.Constants.PATH_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private TicketRequestDTO ticketRequestDTO;

  @BeforeEach
  public void setUp() {
    ticketRequestDTO = new TicketRequestDTO();
    ticketRequestDTO.setUserId(UUID.randomUUID().toString());
    ticketRequestDTO.setEstatus(EstatusEnum.ABIERTO.name());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testCreateTicket() throws Exception {
    mockMvc.perform(post(PATH_TICKETS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(ticketRequestDTO)))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testUpdateTicket() throws Exception {
    UUID ticketId = UUID.randomUUID();
    mockMvc.perform(put(PATH_TICKETS + INCLINED_BAR + ticketId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(ticketRequestDTO)))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testDeleteTicket() throws Exception {
    UUID ticketId = UUID.randomUUID();
    mockMvc.perform(delete(PATH_TICKETS + INCLINED_BAR + ticketId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetTicketById() throws Exception {
    UUID ticketId = UUID.randomUUID();
    mockMvc.perform(get(PATH_TICKETS + INCLINED_BAR + ticketId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetAllTickets() throws Exception {
    mockMvc.perform(get(PATH_TICKETS)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetTicketsByStatus() throws Exception {
    mockMvc.perform(get(PATH_TICKETS + PATH_ESTATUS + INCLINED_BAR + EstatusEnum.ABIERTO.name())
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetTicketsByUser() throws Exception {
    UUID userId = UUID.randomUUID();
    mockMvc.perform(get(PATH_TICKETS + PATH_USER + INCLINED_BAR + userId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetTicketsByStatusAndUser() throws Exception {
    UUID userId = UUID.randomUUID();
    mockMvc.perform(get(
        PATH_TICKETS + PATH_ESTATUS + INCLINED_BAR + EstatusEnum.ABIERTO.name() + PATH_USER
          + INCLINED_BAR + userId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}

