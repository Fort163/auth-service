package com.quick.recording.auth.service.entity;

import com.quick.recording.gateway.entity.AuditEntity;
import jakarta.persistence.*;
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
public class RoleEntity extends AuditEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Role2Permission",
            joinColumns = {@JoinColumn(name = "role2permission_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission2role_id")}
    )
    private List<PermissionEntity> permissions;

}
