package com.case_product_management.repository;

import com.case_product_management.model.LocationRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRegionRepository extends JpaRepository<LocationRegion, Long> {
}
