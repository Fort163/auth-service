package com.quick.recording.auth.service.entity;

import com.quick.recording.gateway.entity.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionEntity extends AuditEntity {

    @Column(name = "permission", unique = true)
    private String permission;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(mappedBy = "permissions")
    private List<RoleEntity> roleList;

}
