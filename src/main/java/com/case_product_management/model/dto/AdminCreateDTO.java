package com.case_product_management.model.dto;

import com.case_product_management.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AdminCreateDTO {
    private Long id;

    @NotEmpty(message = "Not empty")
    @Size(min = 5, max = 100, message = "5 - 100 characters")
    private String fullName;

    @NotEmpty(message = "Not empty")
    private String phone;

    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email invalid")
    @NotEmpty(message = "Not empty")
    private String username;

    private String password;

    private String roleId;

    MultipartFile file;

    private String fileType;
    @Pattern(regexp = "^\\d+$", message = "Invalid")
    @NotEmpty(message = "Not empty")
    private String provinceId;
    @NotEmpty(message = "Not empty")
    private String provinceName;
    @Pattern(regexp = "^\\d+$", message = "Invalid")
    @NotEmpty(message = "Not empty")
    private String districtId;
    @NotEmpty(message = "Not empty")
    private String districtName;
    @Pattern(regexp = "^\\d+$", message = "Invalid")
    @NotEmpty(message = "Not empty")
    private String wardId;
    @NotEmpty(message = "Not empty")
    private String wardName;

    @NotEmpty(message = "Not empty")
    @Size(min = 5, max = 100, message = "5 - 100 characters")
    private String address;

    public User toUser(Role role){
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role);
    }

    public LocationRegion toLocationRegion(){
        return new LocationRegion()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address);
    }

    public Admin toStaff(User user, LocationRegion locationRegion, AdminAvatar adminAvatar){
        return new Admin()
                .setId(id)
                .setFullName(fullName)
                .setPhone(phone)
                .setLocationRegion(locationRegion)
                .setUser(user)
                .setAdminAvatar(adminAvatar);
    }
}
