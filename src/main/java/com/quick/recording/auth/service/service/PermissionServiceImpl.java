package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.mapper.PermissionMapper;
import com.quick.recording.auth.service.repository.entity.PermissionRepository;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.main.service.local.CacheableMainServiceAbstract;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class PermissionServiceImpl
        extends CacheableMainServiceAbstract<PermissionEntity, PermissionDto, PermissionRepository, PermissionMapper>
        implements PermissionService {

    @Autowired
    public PermissionServiceImpl(PermissionRepository repository,
                                 PermissionMapper mapper,
                                  MessageUtil messageUtil,
                                  StreamBridge streamBridge) {
        super(repository, mapper, messageUtil, PermissionEntity.class, streamBridge);
    }

    @Override
    public Class<PermissionDto> getType() {
        return PermissionDto.class;
    }

    @Override
    @CircuitBreaker(name = "database")
    public List<PermissionEntity> findAllByUuids(List<UUID> uuids) {
        return repository.findAllById(uuids);
    }

    @Override
    @CircuitBreaker(name = "database")
    public List<PermissionEntity> saveAll(Collection<PermissionEntity> permissions) {
        return repository.saveAll(permissions);
    }

    @Override
    @CircuitBreaker(name = "database")
    public List<PermissionEntity> findAll(){
        return repository.findAll();
    }

    @Override
    @CircuitBreaker(name = "database")
    public List<PermissionEntity> findAllByPermission(Collection<String> permissions) {
        return getRepository().findAllByPermissionIn(permissions);
    }

    @Override
    public ExampleMatcher prepareExampleMatcher(ExampleMatcher exampleMatcher) {
        return exampleMatcher;
    }

}
