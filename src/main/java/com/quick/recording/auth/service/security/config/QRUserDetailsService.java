package com.quick.recording.auth.service.security.config;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class QRUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userService.findByEmail(username);
        if(user.isPresent()){
            return new QRPrincipalUser(user.get());
        }
        else {
            throw new UsernameNotFoundException("User with email "+ username +" not found");
        }
    }

}
