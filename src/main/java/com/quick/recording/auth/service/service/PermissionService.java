package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.repository.PermissionRepository;
import com.quick.recording.auth.service.security.entity.PermissionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionEntity save(PermissionEntity entity){
        return permissionRepository.save(entity);
    }

}
