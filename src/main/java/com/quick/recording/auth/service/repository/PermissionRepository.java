package com.quick.recording.auth.service.repository;

import com.quick.recording.auth.service.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {

    List<PermissionEntity> findAllByPermissionIn(Collection<String> permissions);

}
