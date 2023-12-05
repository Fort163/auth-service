package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.repository.PermissionRepository;
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
    public PermissionEntity save(PermissionEntity entity) {
        return permissionRepository.save(entity);
    }

    @Override
    public List<PermissionEntity> findAllByUuids(List<UUID> uuids) {
        return permissionRepository.findAllById(uuids);
    }

    @Override
    public List<PermissionEntity> saveAll(List<PermissionEntity> permissions) {
        return permissionRepository.saveAll(permissions);
    }


}
