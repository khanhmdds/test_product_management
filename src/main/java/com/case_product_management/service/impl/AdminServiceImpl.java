package com.case_product_management.service.impl;

import com.case_product_management.exception.DataInputException;
import com.case_product_management.model.Admin;
import com.case_product_management.model.AdminAvatar;
import com.case_product_management.model.LocationRegion;
import com.case_product_management.model.User;
import com.case_product_management.model.dto.AdminCreateDTO;
import com.case_product_management.model.dto.AdminDTO;
import com.case_product_management.model.enums.FileType;
import com.case_product_management.repository.IAdminRepository;
import com.case_product_management.service.IAdminAvatarService;
import com.case_product_management.service.IAdminService;
import com.case_product_management.service.ILocationRegionService;
import com.case_product_management.service.IUserService;
import com.case_product_management.upload.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.case_product_management.utils.UploadUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private IAdminRepository staffRepository;

    @Autowired
    private ILocationRegionService locationRegionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAdminAvatarService staffAvatarService;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private UploadUtil uploadUtil;

    @Override
    public List<Admin> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Admin getById(Long id) {
        return staffRepository.getById(id);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Boolean existsByPhoneAndIdNot(String phone, Long id) {
        return staffRepository.existsByPhoneAndIdNot(phone, id);
    }

    @Override
    public Admin save(Admin staff) {
        userService.save(staff.getUser());
        locationRegionService.save(staff.getLocationRegion());
        return staffRepository.save(staff);
    }

    @Override
    public Admin saveWithUserRoleAndLocationRegion(Admin staff){
        userService.saveNoPassWord(staff.getUser());
        locationRegionService.save(staff.getLocationRegion());
        return staffRepository.save(staff);
    }

    @Override
    public Admin saveWithLocationRegion(Admin staff) {
        locationRegionService.save(staff.getLocationRegion());
        return staffRepository.save(staff);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<AdminDTO> getAllStaffDTO() {
        return staffRepository.getAllStaffDTO();
    }

    @Override
    public List<AdminDTO> getAllStaffDTOWhereIdNot(Long staffId){
        return staffRepository.getAllStaffDTOWhereIdNot(staffId);
    }

    @Override
    public void softDelete(Long staffId) {
        staffRepository.softDelete(staffId);
    }

    @Override
    public Optional<AdminDTO> getByEmailDTO(String email) {
        return staffRepository.getByEmailDTO(email);
    }

    @Override
    public List<Admin> findAllByIdNot(long id) {
        return staffRepository.findAllByIdNot(id);
    }

    @Override
    public Optional<Admin> findByPhone(String phone) {
        return staffRepository.findByPhone(phone);
    }

    @Override
    public Admin createStaffWithAvatar(AdminCreateDTO staffCreateDTO,
                                       LocationRegion locationRegion,
                                       User user) {
        Admin staff = new Admin();
        String fullName = staffCreateDTO.getFullName();
        String phone = staffCreateDTO.getPhone();

        locationRegion = locationRegionService.save(locationRegion);
        user = userService.save(user);

        MultipartFile file = staffCreateDTO.getFile();
        String fileType = file.getContentType();
        assert fileType != null;
        fileType = fileType.substring(0, 5);

        AdminAvatar staffAvatar = new AdminAvatar();
        staffAvatar.setFileType(fileType);
        staffAvatar = staffAvatarService.save(staffAvatar);

        if (fileType.equals(FileType.IMAGE.getValue())) {
            staffAvatar = uploadAndSaveStaffAvatar(file, staffAvatar);
        }
        staff.setId(null)
                .setFullName(fullName)
                .setPhone(phone)
                .setLocationRegion(locationRegion)
                .setUser(user)
                .setAdminAvatar(staffAvatar);
        staff = staffRepository.save(staff);
        return staff;
    }

    private AdminAvatar uploadAndSaveStaffAvatar(MultipartFile file, AdminAvatar staffAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtil.buildImageUploadParams(staffAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            staffAvatar.setFileName(staffAvatar.getId() + "." + fileFormat);
            staffAvatar.setFileUrl(fileUrl);
            staffAvatar.setFileFolder(UploadUtil.STAFF_IMAGE_UPLOAD_FOLDER);
            staffAvatar.setCloudId(staffAvatar.getFileFolder() + "/" + staffAvatar.getId());
            return staffAvatarService.save(staffAvatar);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload fail");
        }
    }

    @Override
    public Admin saveWithAvatar(Admin staff, MultipartFile file) {

        AdminAvatar oldStaffAvatar = staff.getAdminAvatar();
        try {
            uploadService.destroyImage(oldStaffAvatar.getCloudId(), uploadUtil.buildImageDestroyParams(staff, oldStaffAvatar.getCloudId()));
            staffAvatarService.delete(oldStaffAvatar.getId());
            String fileType = file.getContentType();
            assert fileType != null;
            fileType = fileType.substring(0, 5);
            AdminAvatar staffAvatar = new AdminAvatar();
            staffAvatar.setFileType(fileType);
            staffAvatar = staffAvatarService.save(staffAvatar);

            if (fileType.equals(FileType.IMAGE.getValue())) {
                staffAvatar = uploadAndSaveStaffAvatar(file, staffAvatar);
            }
            staff.setAdminAvatar(staffAvatar);
            staff = staffRepository.save(staff);
            return staff;
        } catch (IOException e) {
            throw new DataInputException("Delete fail");
        }
    }
}
