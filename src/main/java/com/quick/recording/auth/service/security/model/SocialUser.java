package com.quick.recording.auth.service.security.model;

import com.quick.recording.auth.service.entity.UserEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public abstract class SocialUser {

    protected Map<String, Object> attributes;
    protected OAuth2User user;

    public SocialUser(OAuth2User user) {
        this.attributes = user.getAttributes();
        this.user = user;
    }

    public OAuth2User getUser() {
        return this.user;
    }

    public Object getAttribute(String attribute) {
        return attributes.get(attribute);
    }

    public abstract UserEntity getUserEntity();
}
