package com.quick.recording.auth.service.controller.authorization;

import com.quick.recording.auth.service.service.SpringSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2/logout")
public class LogoutController {

    private final SpringSessionService springSessionService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> logout() {
        return ResponseEntity.ok(springSessionService.logout());
    }

}
