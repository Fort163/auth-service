package com.quick.recording.auth.service.controller.api;

import com.quick.recording.auth.service.service.api.ApiPermissionService;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.PostPermission;
import com.quick.recording.gateway.dto.auth.PutPermission;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import com.quick.recording.gateway.service.auth.AuthServicePermissionApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permission")
public class PermissionController implements AuthServicePermissionApi {

    private final ApiPermissionService apiPermissionService;

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_READ')")
    public ResponseEntity<PermissionDto> byUuid(UUID uuid) {
        return ResponseEntity.ok(apiPermissionService.byUuid(uuid));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public Page<PermissionDto> list(SearchPermissionDto searchPermissionDto, Pageable pageable) {
        return apiPermissionService.findAll(searchPermissionDto, pageable);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public ResponseEntity<PermissionDto> post(@RequestBody @Validated(PostPermission.class) PermissionDto permission) {
        PermissionDto permissionDto = apiPermissionService.post(permission);
        return ResponseEntity.ok(permissionDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public ResponseEntity<PermissionDto> patch(@RequestBody PermissionDto permission) {
        PermissionDto permissionDto = apiPermissionService.patch(permission);
        return ResponseEntity.ok(permissionDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public ResponseEntity<PermissionDto> put(@RequestBody @Validated(PutPermission.class) PermissionDto permission) {
        PermissionDto permissionDto = apiPermissionService.put(permission);
        return ResponseEntity.ok(permissionDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(UUID uuid) {
        Boolean result = apiPermissionService.delete(uuid);
        return ResponseEntity.ok(result);
    }

}
