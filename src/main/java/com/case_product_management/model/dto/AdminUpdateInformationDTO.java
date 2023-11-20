package com.case_product_management.model.dto;

import com.case_product_management.model.LocationRegion;
import com.case_product_management.model.Admin;
import com.case_product_management.model.AdminAvatar;
import com.case_product_management.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AdminUpdateInformationDTO {
    private Long id;
    @NotEmpty(message = "Input fullname")
    @Size(min = 5, max = 100, message = "Name between 5 and 100 characters")
    private String fullName;
    @NotEmpty(message = "Not empty")
    private String phone;


    private String fileType;

    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;

    @NotEmpty(message = "Not empty")
    @Size(min = 5, max = 100, message = "5 - 100 characters")
    private String address;

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
