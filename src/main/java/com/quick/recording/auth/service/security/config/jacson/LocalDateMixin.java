package com.quick.recording.auth.service.security.config.jacson;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.quick.recording.gateway.config.LocalDateDeserialize;
import com.quick.recording.gateway.config.LocalDateSerializer;

@JsonDeserialize(
        using = LocalDateDeserialize.class
)
@JsonSerialize(
        using = LocalDateSerializer.class
)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
abstract class LocalDateMixin {
}
