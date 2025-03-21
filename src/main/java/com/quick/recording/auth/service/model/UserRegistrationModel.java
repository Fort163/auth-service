package com.quick.recording.auth.service.model;

import lombok.Data;

import java.util.List;

@Data
public class UserRegistrationModel {

    private String firstName;
    private String lastName;
    private String username;
    private String errorUserName;
    private String password;
    private String confirmPassword;
    private String errorConfirmPassword;
    private List<String> errorsPassword;

}
