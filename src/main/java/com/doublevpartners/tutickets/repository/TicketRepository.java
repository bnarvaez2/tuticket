package com.doublevpartners.tutickets.repository;

import com.doublevpartners.tutickets.util.EstatusEnum;
import com.doublevpartners.tutickets.repository.entity.TicketEntity;
import com.doublevpartners.tutickets.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, UUID>, JpaSpecificationExecutor<TicketEntity> {
  List<TicketEntity> findByEstatus(EstatusEnum estatus);
  List<TicketEntity> findByUser(UserEntity user);
}
