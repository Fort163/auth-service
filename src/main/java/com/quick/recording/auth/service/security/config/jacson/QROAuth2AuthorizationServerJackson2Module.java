package com.quick.recording.auth.service.security.config.jacson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.quick.recording.auth.service.security.config.QRPrincipalUser;

public class QROAuth2AuthorizationServerJackson2Module extends SimpleModule {

    public QROAuth2AuthorizationServerJackson2Module() {
        super(QROAuth2AuthorizationServerJackson2Module.class.getName(), new Version(1, 0, 0, (String)null, (String)null, (String)null));
    }

    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(QRPrincipalUser.class, QRPrincipalMixin.class);
    }

}
