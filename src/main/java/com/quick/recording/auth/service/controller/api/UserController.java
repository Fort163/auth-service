package com.quick.recording.auth.service.controller.api;

import com.quick.recording.auth.service.service.api.ApiUserService;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.dto.auth.Role2UserDto;
import com.quick.recording.gateway.dto.auth.SearchUserDto;
import com.quick.recording.gateway.service.auth.AuthServiceUserApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements AuthServiceUserApi {

    private final ApiUserService apiUserService;

    @PreAuthorize("hasAnyAuthority('ROLE_CHANGE_ME_INFO')")
    public ResponseEntity<AuthUserDto> byUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(apiUserService.byUuid(uuid));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Page<AuthUserDto> list(SearchUserDto searchUserDto, Pageable pageable) {
        return apiUserService.findAll(searchUserDto, pageable);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CHANGE_ME_INFO')")
    public ResponseEntity<AuthUserDto> patch(@RequestBody AuthUserDto user) {
        AuthUserDto userDto = apiUserService.patch(user);
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<AuthUserDto> put(@RequestBody @Valid AuthUserDto user) {
        AuthUserDto userDto = apiUserService.put(user);
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable UUID uuid) {
        Boolean result = apiUserService.delete(uuid);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CREATE_SPACE')")
    public ResponseEntity<Boolean> addRole(@RequestBody @Valid Role2UserDto dto) {
        Boolean result = apiUserService.addRole(dto);
        return ResponseEntity.ok(result);
    }

}
