package com.case_product_management.service;

import com.case_product_management.model.Admin;
import com.case_product_management.model.LocationRegion;
import com.case_product_management.model.User;
import com.case_product_management.model.dto.AdminCreateDTO;
import com.case_product_management.model.dto.AdminDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IAdminService  extends IGeneralService<Admin> {
    List<AdminDTO> getAllStaffDTO();
    List<Admin> findAllByIdNot(long id);

    Admin saveWithUserRoleAndLocationRegion(Admin staff);

    Admin saveWithLocationRegion(Admin staff);

    Optional<AdminDTO> getByEmailDTO(String email);
    Optional<Admin> findByPhone(String phone);

    Boolean existsByPhoneAndIdNot(String phone, Long id);

    void softDelete(Long staffId);

    Admin createStaffWithAvatar(AdminCreateDTO staffCreateDTO,
                                LocationRegion locationRegion,
                                User user);

    Admin saveWithAvatar(Admin staff, MultipartFile file);

    List<AdminDTO> getAllStaffDTOWhereIdNot(Long staffId);
}
