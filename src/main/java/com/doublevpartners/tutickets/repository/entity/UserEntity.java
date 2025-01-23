package com.doublevpartners.tutickets.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String name;
  private String lastname;

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
