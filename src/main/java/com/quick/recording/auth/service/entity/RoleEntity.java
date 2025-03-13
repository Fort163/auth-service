package com.quick.recording.auth.service.entity;

import com.quick.recording.gateway.entity.SmartEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class RoleEntity extends SmartEntity {

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Role2Permission",
            joinColumns = {@JoinColumn(name = "role2permission_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission2role_id")}
    )
    private List<PermissionEntity> permissions;

    public RoleEntity() {
    }

    @Builder
    public RoleEntity(UUID uuid, String createdBy, LocalDateTime createdWhen,
                      String updatedBy, LocalDateTime updatedWhen, Boolean isActive,
                      String name, List<PermissionEntity> permissions) {
        super(uuid, createdBy, createdWhen, updatedBy, updatedWhen, isActive);
        this.name = name;
        this.permissions = permissions;
    }

}
