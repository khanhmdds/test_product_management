package com.case_product_management.service.impl;

import com.case_product_management.model.Role;
import com.case_product_management.model.dto.RoleDTO;
import com.case_product_management.repository.IRoleRepository;
import com.case_product_management.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<RoleDTO> getAllRoleDTO() {
        return roleRepository.getAllRoleDTO();
    }

    @Override
    public Optional<Role> findById(Long id) {

        return roleRepository.findById(id);
    }

    @Override
    public Role getById(Long id) {
        return null;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByCode(String name) {
        return roleRepository.findByCode(name);
    }
}
