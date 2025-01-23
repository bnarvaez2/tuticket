package com.doublevpartners.tutickets.controller;

import com.doublevpartners.tutickets.dto.request.UserRequestDTO;
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
import static com.doublevpartners.tutickets.util.Constants.PATH_USERS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private UserRequestDTO userRequestDTO;

  @BeforeEach
  public void setUp() {
    userRequestDTO = new UserRequestDTO();
    userRequestDTO.setName("Brian");
    userRequestDTO.setLastname("Narvaez");
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testCreateUser() throws Exception {
    mockMvc.perform(post(PATH_USERS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequestDTO)))
      .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testUpdateUser() throws Exception {
    UUID userId = UUID.randomUUID();
    mockMvc.perform(put(PATH_USERS + INCLINED_BAR + userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequestDTO)))
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetAllUsers() throws Exception {
    mockMvc.perform(get(PATH_USERS)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetUserById() throws Exception {
    UUID userId = UUID.randomUUID();
    mockMvc.perform(get(PATH_USERS + INCLINED_BAR + userId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }
}

