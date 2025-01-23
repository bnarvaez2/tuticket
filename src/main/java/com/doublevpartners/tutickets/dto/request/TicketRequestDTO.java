package com.doublevpartners.tutickets.dto.request;

import com.doublevpartners.tutickets.validation.anotation.ValidEstatus;
import com.doublevpartners.tutickets.validation.anotation.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {

  @Schema(description = "ID of the user who owns the ticket", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "User ID is required.")
  @ValidUUID(message = "User ID is invalid")
  private String userId;

  @Schema(description = "Status of the ticket", example = "ABIERTO", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "estatus is required.")
  @ValidEstatus(message = "estatus is invalid.")
  private String estatus;
}

