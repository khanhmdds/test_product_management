package com.case_product_management.service;

import com.case_product_management.model.AdminAvatar;

public interface IAdminAvatarService extends IGeneralService<AdminAvatar> {
    void delete(String id);
}
