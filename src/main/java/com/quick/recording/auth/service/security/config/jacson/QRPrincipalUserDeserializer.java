package com.quick.recording.auth.service.security.config.jacson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.quick.recording.auth.service.security.config.QRPrincipalUser;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class QRPrincipalUserDeserializer extends JsonDeserializer<QRPrincipalUser> {


    @Override
    public QRPrincipalUser deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper)jsonParser.getCodec();
        JsonNode root = (JsonNode)mapper.readTree(jsonParser);
        return this.deserialize(jsonParser, mapper, root);
    }
    private QRPrincipalUser deserialize(JsonParser parser, ObjectMapper mapper, JsonNode root) throws JsonParseException {
        AuthProvider provider = AuthProvider.valueOf(this.findStringValue(root, "provider"));
        String username = this.findStringValue(root, "username");
        String name = this.findStringValue(root, "name");
        String email = this.findStringValue(root, "email");
        String locale = this.findStringValue(root, "locale");
        Boolean enabled = Boolean.valueOf(this.findStringValue(root, "enabled"));
        LocalDate birthDay = null;
        if(Objects.nonNull(this.findStringValue(root, "birthDay"))){
            birthDay = LocalDate.parse(this.findStringValue(root, "birthDay"));
        }
        String userpic = this.findStringValue(root, "userpic");
        String fullName = this.findStringValue(root, "fullName");
        String password = this.findStringValue(root, "password");
        Gender genderEnum = Gender.valueOf(this.findStringValue(root, "genderEnum"));
        String phoneNumber = this.findStringValue(root, "phoneNumber");
        Boolean accountNonLocked = Boolean.valueOf(this.findStringValue(root, "accountNonLocked"));
        Boolean accountNonExpired = Boolean.valueOf(this.findStringValue(root, "accountNonExpired"));
        Boolean credentialsNonExpired = Boolean.valueOf(this.findStringValue(root, "credentialsNonExpired"));
        return QRPrincipalUser.builder()
                .provider(provider)
                .username(username)
                .name(name)
                .email(email)
                .locale(locale)
                .enabled(enabled)
                .birthDay(birthDay)
                .userpic(userpic)
                .fullName(fullName)
                .password(password)
                .genderEnum(genderEnum)
                .phoneNumber(phoneNumber)
                .accountNonLocked(accountNonLocked)
                .accountNonExpired(accountNonExpired)
                .credentialsNonExpired(credentialsNonExpired)
                .authorities(findAuthorities(root))
                .build();
    }

    private Collection<? extends GrantedAuthority>  findAuthorities(JsonNode jsonNode) {
        if (jsonNode != null) {
            JsonNode value = jsonNode.findValue("authorities");
            List<SimpleGrantedAuthority> result = new ArrayList<>();
            if(value.isArray()){
                ArrayNode arrayNode = (ArrayNode)value;
                for (final JsonNode objNode : arrayNode) {
                    if(objNode.isArray()){
                        for (final JsonNode authorityNode : objNode){
                            String authority = findStringValue(authorityNode, "authority");
                            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
                            result.add(simpleGrantedAuthority);
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }

    private String findStringValue(JsonNode jsonNode, String fieldName) {
        if (jsonNode == null) {
            return null;
        } else {
            JsonNode value = jsonNode.findValue(fieldName);
            return value != null && value.isTextual() ? value.asText() : null;
        }
    }

}
