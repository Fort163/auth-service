package com.quick.recording.auth.service.service.api;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.mapper.RoleMapper;
import com.quick.recording.auth.service.repository.api.ApiRoleRepository;
import com.quick.recording.auth.service.service.PermissionService;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiRoleServiceImpl implements ApiRoleService {

    private final ApiRoleRepository apiRoleRepository;
    private final PermissionService permissionService;
    private final RoleMapper roleMapper;
    private final MessageUtil messageUtil;

    @Override
    @CircuitBreaker(name = "database")
    public RoleDto byUuid(UUID uuid) {
        Assert.notNull(uuid, "Uuid cannot be null");
        RoleEntity entity = apiRoleRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(messageUtil, RoleEntity.class, uuid)
        );
        return roleMapper.toRoleDto(entity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Page<RoleDto> findAll(SearchRoleDto searchRoleDto, Pageable pageable) {
        List<RoleEntity> list = apiRoleRepository.searchRole(searchRoleDto, pageable);
        long count = apiRoleRepository.searchRoleCount(searchRoleDto);
        List<RoleDto> result = roleMapper.toRoleDtoList(list);
        return new PageImpl<RoleDto>(result, pageable, count);
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "database")
    public RoleDto post(RoleDto role) {
        List<PermissionEntity> permissionByUuids = permissionService.findAllByUuids(role.getPermissions().stream().map(PermissionDto::getUuid).toList());
        RoleEntity roleEntity = roleMapper.toRoleEntity(role);
        roleEntity.setPermissions(permissionByUuids);
        roleEntity.setIsActive(true);
        RoleEntity save = apiRoleRepository.save(roleEntity);
        return roleMapper.toRoleDto(save);
    }

    @Override
    @CircuitBreaker(name = "database")
    public RoleDto patch(RoleDto role) {
        Assert.notNull(role.getUuid(), "Uuid cannot be null");
        RoleEntity roleEntity = apiRoleRepository.findById(role.getUuid()).orElseThrow(
                () -> new NotFoundException(messageUtil, RoleEntity.class, role.getUuid())
        );
        roleEntity = roleMapper.toRoleEntityWithNull(role, roleEntity);
        LinkedList<UUID> uuids = role.getPermissions().stream().map(r -> r.getUuid()).collect(Collectors.toCollection(LinkedList::new));
        List<UUID> currentUuids = roleEntity.getPermissions().stream().map(r -> r.getUuid()).toList();
        for (UUID uuid : currentUuids) {
            if (uuids.contains(uuid)) {
                uuids.remove(uuid);
            }
        }
        if (!uuids.isEmpty()) {
            List<PermissionEntity> permissionByUuids = permissionService.findAllByUuids(uuids);
            roleEntity.getPermissions().addAll(permissionByUuids);
        }
        roleEntity = apiRoleRepository.save(roleEntity);
        return roleMapper.toRoleDto(roleEntity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public RoleDto put(RoleDto role) {
        Assert.notNull(role.getUuid(), "Uuid cannot be null");
        RoleEntity roleEntity = apiRoleRepository.findById(role.getUuid()).orElseThrow(
                () -> new NotFoundException(messageUtil, RoleEntity.class, role.getUuid())
        );
        roleEntity = roleMapper.toRoleEntity(role, roleEntity);
        List<PermissionEntity> permissionByUuids = permissionService.findAllByUuids(role.getPermissions().stream().map(r -> r.getUuid()).toList());
        roleEntity.setPermissions(permissionByUuids);
        roleEntity = apiRoleRepository.save(roleEntity);
        return roleMapper.toRoleDto(apiRoleRepository.save(roleEntity));
    }

    @Override
    @CircuitBreaker(name = "database")
    public Boolean delete(UUID uuid) {
        Assert.notNull(uuid, "Uuid cannot be null");
        RoleEntity roleEntity = apiRoleRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(messageUtil, RoleEntity.class, uuid)
        );
        roleEntity.setIsActive(false);
        apiRoleRepository.save(roleEntity);
        return true;
    }
}
