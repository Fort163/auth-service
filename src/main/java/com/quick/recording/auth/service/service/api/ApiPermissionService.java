package com.quick.recording.auth.service.service.api;

import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface ApiPermissionService {

    PermissionDto byUuid(@PathVariable UUID uuid);

    Page<PermissionDto> findAll(SearchPermissionDto searchPermissionDto, Pageable pageable);

    PermissionDto post(PermissionDto permissionDto);

    PermissionDto patch(PermissionDto permissionDto);

    PermissionDto put(PermissionDto permissionDto);

    Boolean delete(UUID uuid);



}
