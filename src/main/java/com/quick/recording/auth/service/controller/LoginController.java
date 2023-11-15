package com.quick.recording.auth.service.controller;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.mapper.UserMapper;
import com.quick.recording.auth.service.model.UserRegistrationModel;
import com.quick.recording.auth.service.service.UserService;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.regex.Pattern;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/registration")
    public ModelAndView registration(ModelAndView modelAndView, @ModelAttribute("model") UserRegistrationModel userRegistrationModel){
        UserRegistrationModel model = new UserRegistrationModel();
        modelAndView.addObject("model",model);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/login/registration")
    public ModelAndView registrationSubmit(ModelAndView modelAndView, @ModelAttribute("model") UserRegistrationModel userRegistrationModel){
        modelAndView.addObject("model",userRegistrationModel);
        modelAndView.setViewName("registration");
        if(userRegistrationModel.getUsername().length() < 3){
            userRegistrationModel.setErrorUserName("Логин должен содержать хотябы 3 символа");
        }
        Boolean existsByUsername = userService.existsByUsername(userRegistrationModel.getUsername());
        if(existsByUsername){
            userRegistrationModel.setErrorUserName("Логин уже занят");
        }
        if(!Objects.equals(userRegistrationModel.getPassword(),userRegistrationModel.getConfirmPassword())){
            userRegistrationModel.setErrorPassword("Пороли не совпадают");
        }
        else {
            String password = userRegistrationModel.getPassword();
            Pattern pattern = Pattern.compile("(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{5,}");
            if(!pattern.matcher(password).matches()){
                Pattern specChars = Pattern.compile("(?=.*[!@#$%^&*])");
                Pattern upper = Pattern.compile("(?=.*[A-Z])");
                Pattern lower = Pattern.compile("(?=.*[a-z])");
                String errorPassword = "Пороль должен содержать";
                if(password.length() < 5){
                    errorPassword += " больше 5 символов; ";
                }
                if(!specChars.matcher(password).matches()){
                    errorPassword += " спец символ « !@#$%^&* »; ";
                }
                if(!upper.matcher(password).matches()){
                    errorPassword += " латинскую букву в верхнем регистре; ";
                }
                if(!lower.matcher(password).matches()){
                    errorPassword += " латинскую букву в нижнем регистре; ";
                }
                userRegistrationModel.setErrorPassword(errorPassword);
            }
            UserEntity userEntity = userMapper.toUserEntity(userRegistrationModel);
            userEntity.setPassword(passwordEncoder.encode(userRegistrationModel.getPassword()));
            userEntity.setProvider(AuthProvider.local);
            userEntity.setEnabled(true);
            userEntity.setAccountNonExpired(true);
            userEntity.setCredentialsNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userService.save(userEntity);
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

}
