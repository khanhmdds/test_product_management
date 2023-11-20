package com.case_product_management.repository;

import com.case_product_management.model.Customer;
import com.case_product_management.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT NEW com.case_product_management.model.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.phone, " +
            "c.locationRegion, " +
            "c.user, " +
            "c.customerAvatar" +
            ") " +
            "FROM Customer AS c "
            +
            "WHERE c.deleted = false "
    )
    List<CustomerDTO> getAllCustomerDTO();

    @Query("SELECT NEW com.case_product_management.model.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.phone, " +
            "c.locationRegion, " +
            "c.user, " +
            "c.customerAvatar" +
            ") " +
            "FROM Customer AS c " +
            "WHERE " +
            "c.deleted = false " +
            "AND " +
            "c.user.username = :email"
    )
    Optional<CustomerDTO> getByEmailDTO(@Param("email") String email);

    @Modifying
    @Query("UPDATE Customer AS s " +
            "SET s.deleted = true " +
            "WHERE s.id = :customerId")
    void softDelete(@Param("customerId") Long customerId);
//    void deleteCustomerById (Long customerId);

    Boolean existsByPhoneAndIdNot(String phone, Long id);

    Optional<Customer> findByPhone(String phone);
}
