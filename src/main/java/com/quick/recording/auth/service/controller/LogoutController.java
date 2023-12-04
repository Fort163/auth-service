package com.quick.recording.auth.service.controller;

import com.quick.recording.auth.service.service.SpringSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2/logout")
public class LogoutController {

    private final SpringSessionService springSessionService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_READ')")
    public ResponseEntity<Boolean> logout(){
        return ResponseEntity.ok(springSessionService.logout());
    }

}
