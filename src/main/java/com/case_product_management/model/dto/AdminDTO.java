package com.case_product_management.model.dto;

import com.case_product_management.model.Admin;
import com.case_product_management.model.AdminAvatar;
import com.case_product_management.model.LocationRegion;
import com.case_product_management.model.User;

import com.case_product_management.model.dto.LocationRegionDTO;

import com.case_product_management.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AdminDTO {
    private Long id;

    @NotEmpty(message = "Not empty")
    @Size(min = 5, max = 100, message = "5 - 100 characters")
    private String fullName;

    @NotEmpty(message = "Not empty")
    @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "Invalid phone number")
    private String phone;

    @Valid
    private LocationRegionDTO locationRegion;

    @Valid
    private UserDTO user;

    @Valid
    private AdminAvatarDTO adminAvatar;

    public AdminDTO(Long id, String fullName, String phone, LocationRegion locationRegion, User user, AdminAvatar adminAvatar) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.user = user.toUserDTO();
        this.adminAvatar = adminAvatar.toStaffAvatarDTO();
    }

    public Admin toStaff() {
        return new Admin()
                .setId(id)
                .setFullName(fullName)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setUser(user.toUser())
                .setAdminAvatar(adminAvatar.toStaffAvatar());
    }
}
