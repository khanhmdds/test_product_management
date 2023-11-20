package com.case_product_management.service;

import com.case_product_management.model.Role;
import com.case_product_management.model.dto.RoleDTO;

import java.util.List;

public interface IRoleService extends IGeneralService<Role> {
    List<RoleDTO> getAllRoleDTO();

    Role findByCode(String code);
}
