package com.quick.recording.auth.service.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class QRSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public final static String MAIN_SESSION = "main_session";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (Arrays.stream(request.getCookies()).filter(cookie -> MAIN_SESSION.equals(cookie.getName())).toList().isEmpty()) {
            Cookie cookie = this.createCookie(request);
            response.addCookie(cookie);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Cookie createCookie(HttpServletRequest request) {
        Cookie cookie = new Cookie(MAIN_SESSION, request.getSession().getId());
        cookie.setMaxAge(Integer.MAX_VALUE);
        return cookie;
    }

}
