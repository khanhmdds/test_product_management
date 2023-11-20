package com.case_product_management.service;

import com.case_product_management.model.CustomerAvatar;
import com.case_product_management.service.IGeneralService;

public interface ICustomerAvatarService extends IGeneralService<CustomerAvatar> {
    void delete(String id);
}
