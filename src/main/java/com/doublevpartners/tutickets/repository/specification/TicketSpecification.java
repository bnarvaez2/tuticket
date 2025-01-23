package com.doublevpartners.tutickets.repository.specification;

import com.doublevpartners.tutickets.util.EstatusEnum;
import com.doublevpartners.tutickets.repository.entity.TicketEntity;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

public class TicketSpecification {

  private TicketSpecification() {}

  public static Specification<TicketEntity> hasStatus(EstatusEnum estatus) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("estatus"), estatus);
  }

  public static Specification<TicketEntity> hasUser(UUID userId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
  }
}

