package com.quick.recording.auth.service.repository.api;


import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiPermissionRepository extends JpaRepository<PermissionEntity, UUID>, PagingAndSortingRepository<PermissionEntity, UUID> {

    @Query("SELECT count(DISTINCT permission.uuid) FROM PermissionEntity permission " +
            "LEFT JOIN permission.roleList role " +
            "WHERE (UPPER(permission.permission) LIKE UPPER(:#{'%' + #search.permission + '%'}) OR :#{#search.permission} IS NULL) " +
            "AND (permission.isActive = :#{#search.isActive})" +
            "AND (role.uuid IN :#{#search.roleUuid} OR :#{#search.roleUuid} IS NULL) " +
            "AND (role.name IN :#{#search.roleName} OR :#{#search.roleName} IS NULL)")
    long searchCount(@Param("search") SearchPermissionDto search);

    @Query("SELECT DISTINCT permission FROM PermissionEntity permission " +
            "LEFT JOIN permission.roleList role " +
            "WHERE (UPPER(permission.permission) LIKE UPPER(:#{'%' + #search.permission + '%'}) OR :#{#search.permission} IS NULL) " +
            "AND (permission.isActive = :#{#search.isActive})" +
            "AND (role.uuid IN :#{#search.roleUuid} OR :#{#search.roleUuid} IS NULL) " +
            "AND (role.name IN :#{#search.roleName} OR :#{#search.roleName} IS NULL)")
    List<PermissionEntity> search(@Param("search") SearchPermissionDto search, Pageable pageable);

}
