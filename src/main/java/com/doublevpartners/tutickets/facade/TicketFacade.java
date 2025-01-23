package com.doublevpartners.tutickets.facade;

import com.doublevpartners.tutickets.util.EstatusEnum;
import com.doublevpartners.tutickets.repository.TicketRepository;
import com.doublevpartners.tutickets.repository.entity.TicketEntity;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import com.doublevpartners.tutickets.repository.specification.TicketSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TicketFacade {

  private final TicketRepository ticketRepository;

  public TicketFacade(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Transactional
  public TicketEntity saveTicket(TicketEntity ticket) {
    return ticketRepository.save(ticket);
  }

  public Optional<TicketEntity> findTicketById(UUID id) {
    return ticketRepository.findById(id);
  }

  public void deleteTicket(UUID id) {
    ticketRepository.deleteById(id);
  }

  public List<TicketEntity> findTicketsByEstatus(EstatusEnum estatus) {
    return ticketRepository.findByEstatus(estatus);
  }

  public List<TicketEntity> findTicketsByUser(UserEntity user) {
    return ticketRepository.findByUser(user);
  }

  public List<TicketEntity> findTicketsByStatusAndUser(EstatusEnum estatus, UUID userId) {
    Specification<TicketEntity> spec = Specification.where(TicketSpecification.hasStatus(estatus))
      .and(TicketSpecification.hasUser(userId));
    return ticketRepository.findAll(spec);
  }

  public Page<TicketEntity> findAllTickets(Pageable pageable) {
    return ticketRepository.findAll(pageable);
  }
}

