package com.case_product_management.repository;

import com.case_product_management.model.CustomerAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerAvatarRepository extends JpaRepository<CustomerAvatar, String> {
}
