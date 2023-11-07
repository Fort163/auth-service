package com.quick.recording.auth.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.UUID;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {

  @Id
  @Column(name = "uuid")
  @GeneratedValue(strategy = GenerationType.UUID)
  protected UUID uuid;

}
