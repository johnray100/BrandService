package com.johnray100.io.brandservice.repository;

import com.johnray100.io.brandservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
