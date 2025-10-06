package com.quick.recording.auth.service.repository.dto;

import com.quick.recording.gateway.dto.auth.PermissionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionDtoRepository extends JpaRepository<PermissionDto, UUID> {
}
