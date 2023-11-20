package com.case_product_management.repository;

import com.case_product_management.model.Admin;
import com.case_product_management.model.dto.AdminDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {

    @Query("SELECT NEW com.case_product_management.model.dto.AdminDTO (" +
            "s.id, " +
            "s.fullName, " +
            "s.phone, " +
            "s.locationRegion, " +
            "s.user, " +
            "s.adminAvatar" +
            ") " +
            "FROM Admin AS s " +
            "WHERE s.deleted = false "
    )
    List<AdminDTO> getAllStaffDTO();
    @Query("SELECT NEW com.case_product_management.model.dto.AdminDTO (" +
            "s.id, " +
            "s.fullName, " +
            "s.phone, " +
            "s.locationRegion, " +
            "s.user, " +
            "s.adminAvatar" +
            ") " +
            "FROM Admin AS s " +
            "WHERE s.deleted = false " +
            "AND s.id <> :staffId " +
            "AND s.user.role.code <> 'CUSTOMER'"
    )
    List<AdminDTO> getAllStaffDTOWhereIdNot(@Param("staffId") Long staffId);

    @Query("SELECT NEW com.case_product_management.model.dto.AdminDTO (" +
            "s.id, " +
            "s.fullName, " +
            "s.phone, " +
            "s.locationRegion, " +
            "s.user, " +
            "s.adminAvatar" +
            ") " +
            "FROM Admin AS s " +
            "WHERE s.deleted = false " +
            "AND s.user.username = :email"
    )
    Optional<AdminDTO> getByEmailDTO(@Param("email") String email);

    @Modifying
    @Query("UPDATE Admin AS s SET s.deleted = true WHERE s.id = :staffId")
    void softDelete(@Param("staffId") long staffId);

    Boolean existsByPhoneAndIdNot(String phone, Long id);

    Optional<Admin> findByPhone(String phone);

    List<Admin> findAllByIdNot(long id);
}
