package com.quick.recording.auth.service.security.config;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.service.UserService;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
public class QRUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userService.findByUsernameAndProvider(username, AuthProvider.local);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setLastVisit(LocalDateTime.now());
            UserEntity save = userService.save(userEntity);
            return new QRPrincipalUser(save);
        } else {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
    }

}
