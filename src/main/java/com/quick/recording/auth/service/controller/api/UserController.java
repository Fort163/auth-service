package com.quick.recording.auth.service.controller.api;

import com.quick.recording.auth.service.service.api.ApiUserService;
import com.quick.recording.gateway.dto.auth.SearchUserDto;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.service.auth.AuthServiceUserApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController implements AuthServiceUserApi {

    private final ApiUserService apiUserService;

    public ResponseEntity<AuthUserDto> byUuid(@PathVariable UUID uuid){
        return ResponseEntity.ok(apiUserService.byUuid(uuid));
    }

    public Page<AuthUserDto> list(SearchUserDto searchUserDto, Pageable pageable){
        return apiUserService.findAll(searchUserDto,pageable);
    }

    public ResponseEntity<AuthUserDto> patch(@RequestBody AuthUserDto user){
        AuthUserDto userDto = apiUserService.patch(user);
        return ResponseEntity.ok(userDto);
    }

    public ResponseEntity<AuthUserDto> put(@RequestBody @Valid AuthUserDto user){
        AuthUserDto userDto = apiUserService.put(user);
        return ResponseEntity.ok(userDto);
    }

    public ResponseEntity<Boolean> delete(@PathVariable UUID uuid){
        Boolean result = apiUserService.delete(uuid);
        return ResponseEntity.ok(result);
    }

}
