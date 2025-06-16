package com.johnray100.io.brandservice.service;

import com.johnray100.io.brandservice.entity.Brand;
import com.johnray100.io.brandservice.exception.BrandNotFoundException;
import com.johnray100.io.brandservice.repository.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand updateBrand(Long id, Brand updatedBrand) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException("Brand with ID: " + id + " not found."));

        brand.setName(updatedBrand.getName());
        brand.setDescription(updatedBrand.getDescription());
        brand.setUrlWebsite(updatedBrand.getUrlWebsite());

        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new BrandNotFoundException("Brand with ID: " + id + " not found.");
        }
        brandRepository.deleteById(id);
    }
}
