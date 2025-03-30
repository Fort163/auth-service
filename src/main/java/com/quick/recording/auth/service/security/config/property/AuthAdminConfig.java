package com.quick.recording.auth.service.security.config.property;

import com.quick.recording.auth.service.security.exception.AuthAdminException;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConfigurationProperties(value = "auth.admin", ignoreInvalidFields = true)
@Data
public class AuthAdminConfig {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @PostConstruct
    private void check() throws AuthAdminException {
        if(Objects.isNull(userName)){
            throw new AuthAdminException("Property \"auth.admin.userName\" was not set");
        }
        if(Objects.isNull(password)){
            throw new AuthAdminException("Property \"auth.admin.password\" was not set");
        }
        if(userName.length() < 3){
            throw new AuthAdminException("Property \"auth.admin.password\" less than 3 characters");
        }
        if(password.length() < 5){
            throw new AuthAdminException("Property \"auth.admin.password\" less than 5 characters");
        }
    }

}
