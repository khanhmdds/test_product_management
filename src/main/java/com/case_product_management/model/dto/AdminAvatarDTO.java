package com.case_product_management.model.dto;

import com.case_product_management.model.AdminAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AdminAvatarDTO {
    private String id;
    private String fileName;
    private String fileFolder;
    @NotEmpty(message = "Not Empty")
    private String fileUrl;
    private String fileType;
    private String cloudId;
    private Long ts;

    public AdminAvatar toStaffAvatar() {
        return new AdminAvatar()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setFileType(fileType)
                .setCloudId(cloudId)
                .setTs(ts);
    }
}
