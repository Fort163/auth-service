package com.quick.recording.auth.service.entity;

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
public class PermissionEntity extends BaseEntity {

    @Column(name = "permission",unique = true)
    private String permission;

    @ManyToMany(mappedBy = "permissionList")
    private List<RoleEntity> roleList;

}
