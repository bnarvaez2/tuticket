package com.doublevpartners.tutickets.dto.request;

import static com.doublevpartners.tutickets.util.Constants.LASTNAME_IS_REQUIRED;
import static com.doublevpartners.tutickets.util.Constants.LASTNAME_TOO_LARGE;
import static com.doublevpartners.tutickets.util.Constants.MAX_CHARACTER;
import static com.doublevpartners.tutickets.util.Constants.NAME_IS_REQUIRED;
import static com.doublevpartners.tutickets.util.Constants.NAME_TOO_LARGE;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

  @Schema(description = "Name of the user", example = "Brian", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = NAME_IS_REQUIRED)
  @NotEmpty(message = NAME_IS_REQUIRED)
  @Size(max = MAX_CHARACTER, message = NAME_TOO_LARGE)
  private String name;

  @Schema(description = "Lastname of the user", example = "Narvaez", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = LASTNAME_IS_REQUIRED)
  @NotEmpty(message = LASTNAME_IS_REQUIRED)
  @Size(max = MAX_CHARACTER, message = LASTNAME_TOO_LARGE)
  private String lastname;
}

