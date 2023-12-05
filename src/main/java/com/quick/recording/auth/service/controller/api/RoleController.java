package com.quick.recording.auth.service.controller.api;

import com.quick.recording.auth.service.service.api.ApiRoleService;
import com.quick.recording.gateway.dto.auth.PostRole;
import com.quick.recording.gateway.dto.auth.PutRole;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;
import com.quick.recording.gateway.service.auth.AuthServiceRoleApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController implements AuthServiceRoleApi {

    private final ApiRoleService apiRoleService;

    @Override
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public ResponseEntity<RoleDto> byUuid(@PathVariable UUID uuid){
        return ResponseEntity.ok(apiRoleService.byUuid(uuid));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public Page<RoleDto> list(SearchRoleDto searchRoleDto, Pageable pageable){
        return apiRoleService.findAll(searchRoleDto,pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public ResponseEntity<RoleDto> post(@Validated(PostRole.class) @RequestBody RoleDto role) {
        RoleDto roleDto = apiRoleService.post(role);
        return ResponseEntity.ok(roleDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SPACE_ADMIN')")
    public ResponseEntity<RoleDto> patch(@RequestBody RoleDto role){
        RoleDto roleDto = apiRoleService.patch(role);
        return ResponseEntity.ok(roleDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<RoleDto> put(@RequestBody @Validated(PutRole.class) RoleDto role){
        RoleDto roleDto = apiRoleService.put(role);
        return ResponseEntity.ok(roleDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable UUID uuid){
        Boolean result = apiRoleService.delete(uuid);
        return ResponseEntity.ok(result);
    }

}
