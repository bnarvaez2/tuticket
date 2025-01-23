package com.doublevpartners.tutickets.dto.request;

import static com.doublevpartners.tutickets.util.Constants.PASSWORD_IS_REQUIRED;
import static com.doublevpartners.tutickets.util.Constants.USERNAME_IS_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

  @Schema(description = "username of the user", example = "username", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = USERNAME_IS_REQUIRED)
  @NotEmpty(message = USERNAME_IS_REQUIRED)
  private String username;

  @Schema(description = "password the user", example = "password12345", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = PASSWORD_IS_REQUIRED)
  @NotEmpty(message = PASSWORD_IS_REQUIRED)
  private String password;
}

