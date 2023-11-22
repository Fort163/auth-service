package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.SpringSessionEntity;
import com.quick.recording.auth.service.repository.SpringSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpringSessionService {

    private final SpringSessionRepository springSessionRepository;

    public Boolean logout(){
        Optional<SpringSessionEntity> byPrincipalName = springSessionRepository.findByPrincipalName(SecurityContextHolder.getContext().getAuthentication().getName());
        if(byPrincipalName.isPresent()){
            springSessionRepository.deleteById(byPrincipalName.get().getPrimaryId());
            return true;
        }
        return false;
    }

}
