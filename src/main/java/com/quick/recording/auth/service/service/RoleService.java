package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.repository.RoleRepository;
import com.quick.recording.auth.service.security.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity save(RoleEntity entity){
        return roleRepository.save(entity);
    }


}
