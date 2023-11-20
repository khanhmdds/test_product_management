package com.case_product_management.service;

import com.case_product_management.model.Customer;
import com.case_product_management.model.LocationRegion;
import com.case_product_management.model.User;
import com.case_product_management.model.dto.CustomerCreateDTO;
import com.case_product_management.model.dto.CustomerDTO;
import com.case_product_management.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> {
    Customer saveWithLocationRegion(Customer customer);

    List<CustomerDTO> getAllCustomerDTO();

    Optional<CustomerDTO> getByEmailDTO(String email);

    Optional<Customer> findByPhone(String phone);

    void softDelete(Long customerId);

    Customer createCustomerWithAvatar(CustomerCreateDTO customerCreateDTO,
                                      LocationRegion locationRegion,
                                      User user);

    Customer saveWithAvatar(Customer customer, MultipartFile file);
}
