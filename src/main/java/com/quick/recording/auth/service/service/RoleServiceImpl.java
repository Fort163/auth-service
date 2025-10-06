package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.mapper.RoleMapper;
import com.quick.recording.auth.service.repository.entity.RoleRepository;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.main.service.local.CacheableMainServiceAbstract;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CacheableMainServiceAbstract<RoleEntity, RoleDto, RoleRepository, RoleMapper>
        implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleRepository repository,
                           RoleMapper mapper,
                           MessageUtil messageUtil,
                           StreamBridge streamBridge) {
        super(repository, mapper, messageUtil, RoleEntity.class, streamBridge);
    }

    @Override
    public Class<RoleDto> getType() {
        return RoleDto.class;
    }

    @Override
    @CircuitBreaker(name = "database")
    public RoleEntity findByName(String name) {
        Optional<RoleEntity> role = getRepository().findByName(name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new NotFoundException("Role with name " + name + " not found");
        }
    }


    @Override
    public ExampleMatcher prepareExampleMatcher(ExampleMatcher exampleMatcher) {
        return exampleMatcher;
    }
}
