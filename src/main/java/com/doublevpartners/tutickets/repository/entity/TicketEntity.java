package com.doublevpartners.tutickets.repository.entity;

import com.doublevpartners.tutickets.util.EstatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TicketEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Enumerated(EnumType.STRING)
  private EstatusEnum estatus;

  @Column(updatable = false)
  private LocalDateTime creationDate;
  private LocalDateTime updateDate;

  @PrePersist
  protected void onCreate() {
    creationDate = LocalDateTime.now();
    updateDate = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updateDate = LocalDateTime.now();
  }
}

