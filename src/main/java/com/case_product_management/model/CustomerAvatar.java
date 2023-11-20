package com.case_product_management.model;

import com.case_product_management.model.dto.CustomerAvatarDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name = "customer_avatar")
public class CustomerAvatar {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_folder")
    private String fileFolder;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "cloud_id")
    private String cloudId;

    @Column(columnDefinition = "BIGINT(20) DEFAULT 0")
    private Long ts = new Date().getTime();

    public CustomerAvatarDTO toCustomerAvatarDTO(){
//        CustomerAvatarDTO customerAvatarDTO = new CustomerAvatarDTO()
//        customerAvatarDTO.setId(id)
//        .setFileName(fileName)
//        customerAvatarDTO.setFileFolder(fileFolder)
//        customerAvatarDTO.setFileUrl(fileUrl)
//        customerAvatarDTO.setFileType(fileType)
//        customerAvatarDTO.setCloudId(cloudId)
//        customerAvatarDTO.setTs(ts);

        return new CustomerAvatarDTO()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setFileType(fileType)
                .setCloudId(cloudId)
                .setTs(ts);
    }
}
