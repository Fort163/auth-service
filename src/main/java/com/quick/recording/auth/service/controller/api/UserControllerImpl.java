package com.quick.recording.auth.service.controller.api;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.repository.dto.UserDtoRepository;
import com.quick.recording.auth.service.service.UserService;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.MethodNotSupported;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.dto.auth.Role2UserDto;
import com.quick.recording.gateway.main.controller.CacheableMainControllerAbstract;
import com.quick.recording.gateway.service.auth.AuthServiceUserApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserControllerImpl extends CacheableMainControllerAbstract
        <AuthUserDto,
        UserEntity,
        UserDtoRepository,
        UserService>
        implements AuthServiceUserApi {

    private final MessageUtil messageUtil;

    @Autowired
    public UserControllerImpl(UserService service, UserDtoRepository repository, MessageUtil messageUtil) {
        super(service, repository);
        this.messageUtil = messageUtil;
    }

    @Override
    public ResponseEntity<AuthUserDto> post(AuthUserDto dto) {
        throw new MethodNotSupported(this.messageUtil);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PERMISSION_ADMIN','ALL_EDIT','ROLE_EDIT')")
    public ResponseEntity<Boolean> addRole(@RequestBody @Valid Role2UserDto dto) {
        return ResponseEntity.ok(getService().addRole(dto));
    }

}
