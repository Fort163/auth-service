package com.quick.recording.auth.service.repository.dto;

import com.quick.recording.gateway.dto.auth.AuthUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDtoRepository extends JpaRepository<AuthUserDto, UUID> {
}
