package com.quick.recording.auth.service.service.api;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.mapper.PermissionMapper;
import com.quick.recording.auth.service.repository.api.ApiPermissionRepository;
import com.quick.recording.auth.service.service.RoleService;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiPermissionServiceImpl implements ApiPermissionService{

    private final PermissionMapper permissionMapper;
    private final ApiPermissionRepository apiPermissionRepository;
    private final RoleService roleService;


    @Override
    public PermissionDto byUuid(UUID uuid) {
        Assert.notNull(uuid, "Uuid cannot be null");
        PermissionEntity entity = apiPermissionRepository.findById(uuid).orElseThrow(() -> new NotFoundException(PermissionEntity.class, uuid));
        return permissionMapper.toPermissionDto(entity);
    }

    @Override
    public Page<PermissionDto> findAll(SearchPermissionDto searchPermissionDto, Pageable pageable) {
        List<PermissionEntity> list = apiPermissionRepository.search(searchPermissionDto, pageable);
        long count = apiPermissionRepository.searchCount(searchPermissionDto);
        List<PermissionDto> result = permissionMapper.toPermissionDtoList(list);
        return new PageImpl<PermissionDto>(result, pageable, count);
    }

    @Override
    @Transactional
    public PermissionDto post(PermissionDto permissionDto) {
        PermissionEntity entity = permissionMapper.toPermissionEntity(permissionDto);
        entity.setIsActive(true);
        PermissionEntity save = apiPermissionRepository.save(entity);
        return permissionMapper.toPermissionDto(save);
    }

    @Override
    public PermissionDto patch(PermissionDto permission) {
        Assert.notNull(permission.getUuid(), "Uuid cannot be null");
        PermissionEntity entity = apiPermissionRepository.findById(permission.getUuid()).orElseThrow(() -> new NotFoundException(PermissionEntity.class, permission.getUuid()));
        entity = permissionMapper.toPermissionEntityWithNull(permission,entity);
        return permissionMapper.toPermissionDto(apiPermissionRepository.save(entity));
    }

    @Override
    public PermissionDto put(PermissionDto permission) {
        Assert.notNull(permission.getUuid(), "Uuid cannot be null");
        PermissionEntity roleEntity = apiPermissionRepository.findById(permission.getUuid()).orElseThrow(() -> new NotFoundException(PermissionEntity.class, permission.getUuid()));
        roleEntity = permissionMapper.toPermissionEntity(permission,roleEntity);
        return permissionMapper.toPermissionDto(apiPermissionRepository.save(apiPermissionRepository.save(roleEntity)));
    }

    @Override
    public Boolean delete(UUID uuid) {
        Assert.notNull(uuid, "Uuid cannot be null");
        PermissionEntity roleEntity = apiPermissionRepository.findById(uuid).orElseThrow(() -> new NotFoundException(PermissionEntity.class, uuid));
        roleEntity.setIsActive(false);
        apiPermissionRepository.save(roleEntity);
        return true;
    }

}
