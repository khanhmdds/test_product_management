package com.case_product_management.service.impl;

import com.case_product_management.model.AdminAvatar;
import com.case_product_management.repository.IAdminAvatarRepository;
import com.case_product_management.service.IAdminAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminAvatarServiceImpl implements IAdminAvatarService {
    @Autowired
    private IAdminAvatarRepository staffAvatarRepository;

    @Override
    public List<AdminAvatar> findAll() {
        return null;
    }

    @Override
    public AdminAvatar getById(Long id) {
        return null;
    }

    @Override
    public Optional<AdminAvatar> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public AdminAvatar save(AdminAvatar staffAvatar) {
        return staffAvatarRepository.save(staffAvatar);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void delete(String id){
        staffAvatarRepository.deleteById(id);
    }
}
