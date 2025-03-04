package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.OAuthAuthorizationEntity;
import com.quick.recording.auth.service.repository.OAuthAuthorizationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OAuthAuthorizationServiceImpl implements OAuthAuthorizationService {

    private final OAuthAuthorizationRepository oAuthAuthorizationRepository;

    @Override
    @CircuitBreaker(name = "database")
    public Boolean existsByUuid(UUID uuid){
        return oAuthAuthorizationRepository.existsByUuid(uuid);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Optional<OAuthAuthorizationEntity> findByUuid(UUID uuid){
        return oAuthAuthorizationRepository.findByUuid(uuid);
    }

    @Override
    @CircuitBreaker(name = "database")
    public OAuthAuthorizationEntity save(OAuthAuthorizationEntity entity){
        return oAuthAuthorizationRepository.save(entity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Optional<OAuthAuthorizationEntity> findByToken(String token, String tokenType){
        switch (tokenType){
            case "access_token" : {
                return oAuthAuthorizationRepository.findByAccessTokenValue(token);
            }
            case "refresh_token" : {
                return oAuthAuthorizationRepository.findByRefreshTokenValue(token);
            }
            case "code" : {
                return oAuthAuthorizationRepository.findByAuthorizationCodeValue(token);
            }
            case "state" : {
                return oAuthAuthorizationRepository.findByState(token);
            }
            default:{
                return oAuthAuthorizationRepository.findByAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrState(
                        token,token,token,token);
            }
        }
    }

    @Override
    @CircuitBreaker(name = "database")
    public void delete(UUID uuid){
        oAuthAuthorizationRepository.deleteById(uuid);
    }

}
