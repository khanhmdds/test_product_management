package com.case_product_management.repository;

import com.case_product_management.model.Role;
import com.case_product_management.model.dto.RoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT NEW com.case_product_management.model.dto.RoleDTO(" +
            "r.id, " +
            "r.code" +
            ") " +
            "FROM Role AS r " +
            "WHERE r.code <> 'CUSTOMER'"
    )
    List<RoleDTO> getAllRoleDTO();

    Role findByCode(String code);
}
