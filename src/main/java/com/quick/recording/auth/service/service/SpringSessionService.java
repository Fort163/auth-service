package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.SpringSessionEntity;
import com.quick.recording.auth.service.repository.SpringSessionRepository;
import com.quick.recording.auth.service.security.config.QRSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpringSessionService {

    private final SpringSessionRepository springSessionRepository;

    public Boolean logout(){
        Optional<Set<SpringSessionEntity>> springSessions;
        String mainSession = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(QRSuccessHandler.MAIN_SESSION);
        if(Objects.nonNull(mainSession)){
            springSessions = springSessionRepository.findAllByPrincipalNameAndSessionId(SecurityContextHolder.getContext().getAuthentication().getName(),mainSession);
        }
        else {
            springSessions = springSessionRepository.findAllByPrincipalName(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        if(springSessions.isPresent()){
            springSessionRepository.deleteAll(springSessions.get());
            return true;
        }
        return false;
    }

}
