package com.quick.recording.auth.service.controller.api;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.repository.dto.RoleDtoRepository;
import com.quick.recording.auth.service.service.RoleService;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.main.controller.CacheableMainControllerAbstract;
import com.quick.recording.gateway.service.auth.AuthServiceRoleApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleControllerImpl extends CacheableMainControllerAbstract
        <RoleDto,
                RoleEntity,
                RoleDtoRepository,
                RoleService>
        implements AuthServiceRoleApi {

    @Autowired
    public RoleControllerImpl(RoleService service, RoleDtoRepository repository) {
        super(service, repository);
    }


}
