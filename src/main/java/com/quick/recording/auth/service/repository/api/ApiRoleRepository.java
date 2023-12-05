package com.quick.recording.auth.service.repository.api;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiRoleRepository extends JpaRepository<RoleEntity, UUID>, PagingAndSortingRepository<RoleEntity, UUID> {

    @Query("SELECT count(DISTINCT role.uuid) FROM RoleEntity role " +
            "INNER JOIN role.permissions permission " +
            "WHERE (UPPER(role.name) LIKE UPPER(:#{'%' + #search.name + '%'}) OR :#{#search.name} IS NULL)" +
            "AND (role.isActive = :#{#search.isActive})" +
            "AND (permission.uuid IN :#{#search.permissionUuid} OR :#{#search.permissionUuid} IS NULL) " +
            "AND (permission.permission IN :#{#search.permissionName} OR :#{#search.permissionName} IS NULL)")
    long searchRoleCount(@Param("search") SearchRoleDto search);

    @Query("SELECT DISTINCT role FROM RoleEntity role " +
            "INNER JOIN role.permissions permission " +
            "WHERE (UPPER(role.name) LIKE UPPER(:#{'%' + #search.name + '%'}) OR :#{#search.name} IS NULL) " +
            "AND (role.isActive = :#{#search.isActive})" +
            "AND (permission.uuid IN :#{#search.permissionUuid} OR :#{#search.permissionUuid} IS NULL) " +
            "AND (permission.permission IN :#{#search.permissionName} OR :#{#search.permissionName} IS NULL)")
    List<RoleEntity> searchRole(@Param("search") SearchRoleDto search, Pageable pageable);

}
