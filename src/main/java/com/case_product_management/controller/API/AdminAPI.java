package com.case_product_management.controller.API;

import com.case_product_management.exception.DataInputException;
import com.case_product_management.exception.EmailExistsException;
import com.case_product_management.model.*;
import com.case_product_management.model.dto.AdminCreateDTO;
import com.case_product_management.model.dto.AdminDTO;
import com.case_product_management.model.dto.AdminUpdateDTO;
import com.case_product_management.model.dto.AdminUpdateInformationDTO;
import com.case_product_management.service.IRoleService;
import com.case_product_management.service.IAdminService;
import com.case_product_management.service.IUserService;
import com.case_product_management.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staffs")
public class AdminAPI {
    private static final String DEFAULT_PASSWORD = "admin";
    @Autowired
    private IAdminService staffService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllByDeletedIsFalse() {
        List<AdminDTO> staffDTOS = staffService.getAllStaffDTO();
        if (staffDTOS.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(staffDTOS, HttpStatus.OK);
    }

    @GetMapping("/get-all-staffs-where-id-not/{staffId}")
    public ResponseEntity<?> getAllByDeletedIsFalseAndIdNot(@PathVariable String staffId) {
        Long sid = Long.parseLong(staffId);
        List<AdminDTO> staffDTOS = staffService.getAllStaffDTOWhereIdNot(sid);
        if (staffDTOS.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(staffDTOS, HttpStatus.OK);
    }

    @GetMapping("/{staffId}")
    public ResponseEntity<?> getById(@PathVariable String staffId) {
        long sid;
        try {
            sid = Long.parseLong(staffId);
        } catch (NumberFormatException e) {
            throw new DataInputException("ID invalid");
        }

        Optional<Admin> staffOptional = staffService.findById(sid);

        if (!staffOptional.isPresent()) {
            throw new DataInputException("ID invalid");
        }

        return new ResponseEntity<>(staffOptional.get().toStaffDTO(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> create(@Validated AdminCreateDTO staffCreateDTO, BindingResult bindingResult) {

        MultipartFile imageFile = staffCreateDTO.getFile();

        if (imageFile == null) {
            throw new DataInputException("image pls!");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<User> userOptional = userService.findByUserName(staffCreateDTO.getUsername());

        if (userOptional.isPresent()) {
            throw new EmailExistsException("Email already exist");
        }

        Optional<Admin> staffOptional = staffService.findByPhone(staffCreateDTO.getPhone());

        if (staffOptional.isPresent()) {
            throw new DataInputException("phone number already exist");
        }

        LocationRegion locationRegion = staffCreateDTO.toLocationRegion();
        locationRegion.setId(null);

        Optional<Role> optRole = roleService.findById(Long.parseLong(staffCreateDTO.getRoleId()));

        if (!optRole.isPresent()) {
            throw new DataInputException("Role invalid");
        }

        Role role = optRole.get();

        User user = staffCreateDTO.toUser(role);
        user.setId(null);
        user.setPassword(DEFAULT_PASSWORD);

        Admin newStaff = staffService.createStaffWithAvatar(staffCreateDTO, locationRegion, user);

        return new ResponseEntity<>(newStaff.toStaffDTO(), HttpStatus.CREATED);
    }

    @PatchMapping("/{staffId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long staffId, MultipartFile file, @Validated AdminUpdateDTO staffUpdateDTO, BindingResult bindingResult) {
        Optional<Admin> staffOptional = staffService.findById(staffId);
        if (!staffOptional.isPresent()) {
            throw new DataInputException("ID admin not found");
        }
        Admin staff = staffOptional.get();

        String phone = staffUpdateDTO.getPhone();

        if (staffService.existsByPhoneAndIdNot(phone, staffId)) {
            throw new DataInputException("phone number already exist");
        }
        Long roleId;
        try {
            roleId = Long.parseLong(staffUpdateDTO.getRoleId());
        } catch (Exception e) {
            throw new DataInputException("Role invalid");
        }
        Optional<Role> roleOptional = roleService.findById(roleId);

        if (!roleOptional.isPresent()) {
            throw new DataInputException("Role not found");
        }

        Role role = roleOptional.get();

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        LocationRegion newLocationRegion = staffUpdateDTO.toLocationRegion();

        staff.setFullName(staffUpdateDTO.getFullName());
        staff.setPhone(phone);
        staff.getUser().setRole(role);
        staff.getLocationRegion()
                .setProvinceId(newLocationRegion.getProvinceId())
                .setProvinceName(newLocationRegion.getProvinceName())
                .setDistrictId(newLocationRegion.getDistrictId())
                .setDistrictName(newLocationRegion.getDistrictName())
                .setWardId(newLocationRegion.getWardId())
                .setWardName(newLocationRegion.getWardName())
                .setAddress(newLocationRegion.getAddress());
        staff = staffService.saveWithUserRoleAndLocationRegion(staff);
        if(file != null){
            staff = staffService.saveWithAvatar(staff, file);
        }
        return new ResponseEntity<>(staff.toStaffDTO(), HttpStatus.OK);
    }

    @PatchMapping("/update-information/{staffId}")
    public ResponseEntity<?> update(@PathVariable Long staffId, MultipartFile file, @Validated AdminUpdateInformationDTO staffUpdateInformationDTO, BindingResult bindingResult) {

        Optional<Admin> staffOptional = staffService.findById(staffId);
        if (!staffOptional.isPresent()) {
            throw new DataInputException("ID admin not found");
        }

        String phone = staffUpdateInformationDTO.getPhone();

        if (staffService.existsByPhoneAndIdNot(phone, staffId)) {
            throw new DataInputException("phone already exist");
        }

        Admin staff = staffOptional.get();

        LocationRegion locationRegion = staffUpdateInformationDTO.toLocationRegion();
        locationRegion.setId(staff.getLocationRegion().getId());

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        staff.setFullName(staffUpdateInformationDTO.getFullName());
        staff.setPhone(phone);
        staff.setLocationRegion(locationRegion);
        staff = staffService.saveWithLocationRegion(staff);
        if (file != null) {
            staff = staffService.saveWithAvatar(staff, file);
        }

        return new ResponseEntity<>(staff.toStaffDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{staffId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long staffId) {

        Optional<Admin> staffOptional = staffService.findById(staffId);

        if (!staffOptional.isPresent()) {
            throw new DataInputException("ID invalid");
        }

        Admin staff = staffOptional.get();

        if (staff.getUser().getRole().getCode().equals("ADMIN")) {
            throw new DataInputException("Cannot delete ADMIN.");
        }

        try {
            staffService.softDelete(staffId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("contact Administrator.");
        }
    }
}