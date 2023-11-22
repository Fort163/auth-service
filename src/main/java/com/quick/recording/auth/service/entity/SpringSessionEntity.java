package com.quick.recording.auth.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "spring_session")
@Data
public class SpringSessionEntity {

    @Id
    @Column(name = "primary_id")
    private String primaryId;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "creation_time")
    private Long creationTime;
    @Column(name = "last_access_time")
    private Long lastAccessTime;
    @Column(name = "max_inactive_interval")
    private Integer maxInactiveInterval;
    @Column(name = "expiry_time")
    private Long expiryTime;
    @Column(name = "principal_name")
    private String principalName;

}
