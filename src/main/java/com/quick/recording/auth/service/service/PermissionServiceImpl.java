package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.repository.PermissionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    @CircuitBreaker(name = "database")
    public PermissionEntity save(PermissionEntity entity) {
        return permissionRepository.save(entity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public List<PermissionEntity> findAllByUuids(List<UUID> uuids) {
        return permissionRepository.findAllById(uuids);
    }

    @Override
    @CircuitBreaker(name = "database")
    public List<PermissionEntity> saveAll(List<PermissionEntity> permissions) {
        return permissionRepository.saveAll(permissions);
    }


}
