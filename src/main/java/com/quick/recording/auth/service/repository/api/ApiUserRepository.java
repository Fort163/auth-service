package com.quick.recording.auth.service.repository.api;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.gateway.dto.auth.SearchUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiUserRepository extends JpaRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID> {

    @Query("SELECT count(userQr.uuid) FROM qr_user userQr " +
            "WHERE (UPPER(userQr.firstName) LIKE UPPER(:#{'%' + #search.firstName + '%'}) OR :#{#search.firstName} IS NULL) " +
            " AND (UPPER(userQr.lastName) LIKE UPPER(:#{'%' + #search.lastName + '%'}) OR :#{#search.lastName} IS NULL) " +
            " AND (UPPER(userQr.email) LIKE UPPER(:#{'%' + #search.email + '%'}) OR :#{#search.email} IS NULL) " +
            " AND (UPPER(userQr.username) LIKE UPPER(:#{'%' + #search.username + '%'}) OR :#{#search.username} IS NULL) " +
            " AND (userQr.gender =:#{#search.gender} OR true = :#{#search.gender == null} ) " +
            " AND (userQr.phoneNumber LIKE :#{'%' + (#search.phoneNumber != null ? #search.phoneNumber.trim() : null )  + '%'} OR :#{#search.phoneNumber} IS NULL) " +
            " AND (userQr.birthDay =:#{#search.birthDay} OR true = :#{#search.birthDay == null }) " +
            " AND (userQr.provider =:#{#search.provider} OR true = :#{#search.provider == null}) ")
    long searchUserCount(@Param("search") SearchUserDto search);

    //because https://hibernate.atlassian.net/browse/HHH-17006

    @Query("SELECT userQr FROM qr_user userQr " +
            "WHERE (UPPER(userQr.firstName) LIKE UPPER(:#{'%' + #search.firstName + '%'}) OR :#{#search.firstName} IS NULL) " +
            " AND (UPPER(userQr.lastName) LIKE UPPER(:#{'%' + #search.lastName + '%'}) OR :#{#search.lastName} IS NULL) " +
            " AND (UPPER(userQr.email) LIKE UPPER(:#{'%' + #search.email + '%'}) OR :#{#search.email} IS NULL) " +
            " AND (UPPER(userQr.username) LIKE UPPER(:#{'%' + #search.username + '%'}) OR :#{#search.username} IS NULL) " +
            " AND (userQr.gender =:#{#search.gender} OR true = :#{#search.gender == null} ) " +
            " AND (userQr.phoneNumber LIKE :#{'%' + (#search.phoneNumber != null ? #search.phoneNumber.trim() : null ) + '%'} OR :#{#search.phoneNumber} IS NULL) " +
            " AND (userQr.birthDay =:#{#search.birthDay} OR true = :#{#search.birthDay == null }) " +
            " AND (userQr.provider =:#{#search.provider} OR true = :#{#search.provider == null}) ")
    List<UserEntity> searchUser(@Param("search") SearchUserDto search, Pageable pageable);


}
