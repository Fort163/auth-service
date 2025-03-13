package com.quick.recording.auth.service.entity;

import com.quick.recording.gateway.entity.SmartEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PermissionEntity extends SmartEntity {

    @Column(name = "permission", unique = true)
    private String permission;

    @ManyToMany(mappedBy = "permissions")
    private List<RoleEntity> roleList;

    public PermissionEntity() {
    }

    @Builder
    public PermissionEntity(UUID uuid, String createdBy, LocalDateTime createdWhen, String updatedBy,
                            LocalDateTime updatedWhen, Boolean isActive, String permission,
                            List<RoleEntity> roleList) {
        super(uuid, createdBy, createdWhen, updatedBy, updatedWhen, isActive);
        this.permission = permission;
        this.roleList = roleList;
    }
}
