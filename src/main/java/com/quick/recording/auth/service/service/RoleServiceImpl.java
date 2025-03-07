package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.repository.RoleRepository;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MessageUtil messageUtil;

    @Override
    @CircuitBreaker(name = "database")
    public RoleEntity save(RoleEntity entity) {
        return roleRepository.save(entity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public RoleEntity findByName(String name) {
        Optional<RoleEntity> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new NotFoundException("Role with name " + name + " not found");
        }
    }

    @Override
    @CircuitBreaker(name = "database")
    public RoleEntity findByUuid(UUID uuid) {
        return roleRepository.findById(uuid).orElseThrow(() -> new NotFoundException(messageUtil, RoleEntity.class, uuid));
    }


}
