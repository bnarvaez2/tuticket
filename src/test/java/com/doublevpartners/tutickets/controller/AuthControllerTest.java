package com.doublevpartners.tutickets.controller;

import com.doublevpartners.tutickets.dto.request.AuthenticationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.doublevpartners.tutickets.util.Constants.PATH_ADMIN;
import static com.doublevpartners.tutickets.util.Constants.PATH_AUTH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private AuthenticationRequestDTO authenticationRequestDTO;

  @Test
  public void testCreateAuthenticationToken() throws Exception {
    mockMvc.perform(post(PATH_AUTH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(getAuthenticationRequestDTO("brian712", "U53rP@ssw0rd!2025"))))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists());
  }

  @Test
  public void testCreateAuthenticationAdminToken() throws Exception {
    mockMvc.perform(post(PATH_AUTH + PATH_ADMIN)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(getAuthenticationRequestDTO("adminUser", "Adm1nP@ssw0rd!2025"))))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists());
  }

  @Test
  public void testCreateAuthenticationAdminToken_fasilure() throws Exception {
    mockMvc.perform(post(PATH_AUTH + PATH_ADMIN)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(getAuthenticationRequestDTO("testuser", "testpassword"))))
      .andExpect(status().isUnauthorized());
  }

  private static AuthenticationRequestDTO getAuthenticationRequestDTO(String username, String password) {
    AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();
    authenticationRequestDTO.setUsername(username);
    authenticationRequestDTO.setPassword(password);
    return authenticationRequestDTO;
  }
}

