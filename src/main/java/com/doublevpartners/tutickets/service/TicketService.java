package com.doublevpartners.tutickets.service;

import com.doublevpartners.tutickets.dto.response.PageResponseDTO;
import com.doublevpartners.tutickets.util.EstatusEnum;
import com.doublevpartners.tutickets.dto.request.TicketRequestDTO;
import com.doublevpartners.tutickets.dto.response.TicketResponseDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface TicketService {

  void createTicket(TicketRequestDTO ticket);

  void updateTicket(UUID id, TicketRequestDTO ticketDetails);

  void deleteTicket(UUID id);

  TicketResponseDTO getTicketById(UUID id);

  PageResponseDTO<TicketResponseDTO> getAllTickets(Pageable pageable);

  List<TicketResponseDTO> getTicketsByStatus(EstatusEnum estatus);

  List<TicketResponseDTO> getTicketsByUser(UUID userId);

  List<TicketResponseDTO> getTicketsByStatusAndUser(EstatusEnum estatus, UUID userId);
}
