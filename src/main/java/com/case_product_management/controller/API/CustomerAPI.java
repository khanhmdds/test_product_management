package com.case_product_management.controller.API;

import com.case_product_management.exception.DataInputException;
import com.case_product_management.model.Customer;
import com.case_product_management.model.Admin;
import com.case_product_management.model.dto.CustomerDTO;
import com.case_product_management.model.dto.AdminDTO;
import com.case_product_management.service.ICustomerService;
import com.case_product_management.service.IRoleService;
import com.case_product_management.service.IUserService;
import com.case_product_management.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllByDeletedIsFalse() {
        List<CustomerDTO> customerDTOS = customerService.getAllCustomerDTO();
        if (customerDTOS.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getById(@PathVariable String customerId) {
        long sid;
        try {
            sid = Long.parseLong(customerId);
        } catch (NumberFormatException e) {
            throw new DataInputException("ID invalid.");
        }

        Optional<Customer> customerOptional = customerService.findById(sid);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("ID invalid.");
        }

        return new ResponseEntity<>(customerOptional.get().toCustomerDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long customerId) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("ID invalid.");
        }

        try {
            customerService.softDelete(customerId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("contact Administrator.");
        }
    }
}
