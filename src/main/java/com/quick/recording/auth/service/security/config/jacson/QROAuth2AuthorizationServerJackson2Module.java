package com.quick.recording.auth.service.security.config.jacson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.quick.recording.auth.service.security.config.QRPrincipalUser;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import java.time.LocalDate;

public class QROAuth2AuthorizationServerJackson2Module extends SimpleModule {

    public QROAuth2AuthorizationServerJackson2Module() {
        super(QROAuth2AuthorizationServerJackson2Module.class.getName(), new Version(1, 0, 0, (String) null, (String) null, (String) null));
    }

    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping((ObjectMapper) context.getOwner());
        context.setMixInAnnotations(QRPrincipalUser.class, QRPrincipalMixin.class);
        context.setMixInAnnotations(LocalDate.class, LocalDateMixin.class);
    }

}
