package com.case_product_management.utils;

import com.case_product_management.model.dto.CustomerDTO;
import com.case_product_management.model.dto.AdminDTO;
import com.case_product_management.model.dto.UserDTO;
import com.case_product_management.service.ICustomerService;
import com.case_product_management.service.IAdminService;
import com.case_product_management.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AppUtils {
    @Autowired
    private IUserService userService;

    @Autowired
    private IAdminService staffService;


    @Autowired
    private ICustomerService customerService;
    public ResponseEntity<?> mapErrorToResponse(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    public AdminDTO getStaff() {
//        String userName;
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            userName = ((UserDetails) principal).getUsername();
//        } else {
//            userName = principal.toString();
//        }
//        Optional<AdminDTO> staffDTOOptional = staffService.getByEmailDTO(userName);
//        return staffDTOOptional.get();
//    }
//
//
//    public CustomerDTO getCustomer() {
//        String userName;
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            userName = ((UserDetails) principal).getUsername();
//        } else {
//            userName = principal.toString();
//        }
//        Optional<CustomerDTO> customerDTOOptional = customerService.getByEmailDTO(userName);
//        return customerDTOOptional.get();
//    }

    public AdminDTO getStaff() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        Optional<AdminDTO> staffDTOOptional = staffService.getByEmailDTO(userName);
        return staffDTOOptional.orElse(null);
    }

    public CustomerDTO getCustomer() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        Optional<CustomerDTO> customerDTOOptional = customerService.getByEmailDTO(userName);
        return customerDTOOptional.orElse(null);
    }
}
