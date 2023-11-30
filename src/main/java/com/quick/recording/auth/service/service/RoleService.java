package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.repository.RoleRepository;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity save(RoleEntity entity) {
        return roleRepository.save(entity);
    }

    public RoleEntity findByName(String name) {
        Optional<RoleEntity> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new NotFoundException("Role with name " + name + " not found");
        }
    }


}
