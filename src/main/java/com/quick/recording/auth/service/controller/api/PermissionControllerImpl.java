package com.quick.recording.auth.service.controller.api;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.repository.dto.PermissionDtoRepository;
import com.quick.recording.auth.service.service.PermissionService;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.main.controller.CacheableMainControllerAbstract;
import com.quick.recording.gateway.service.auth.AuthServicePermissionApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permission")
public class PermissionControllerImpl extends CacheableMainControllerAbstract
        <PermissionDto,
        PermissionEntity,
        PermissionDtoRepository,
        PermissionService>
        implements AuthServicePermissionApi {

    @Autowired
    public PermissionControllerImpl(PermissionService service, PermissionDtoRepository repository) {
        super(service, repository);
    }

}
