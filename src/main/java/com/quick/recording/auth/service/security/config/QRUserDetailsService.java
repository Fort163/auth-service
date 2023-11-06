package com.quick.recording.auth.service.security.config;

import com.quick.recording.auth.service.security.entity.UserEntity;
import com.quick.recording.auth.service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
public class QRUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userService.findByEmail(username);
        if(user.isPresent()){
            return user.get();
        }
        else {
            throw new UsernameNotFoundException("User with email "+ username +" not found");
        }
    }

}
