package com.johnray100.io.brandservice.resolver;

import com.johnray100.io.brandservice.entity.Brand;
import com.johnray100.io.brandservice.exception.BrandNotFoundException;
import com.johnray100.io.brandservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BrandResolver {

    @Autowired
    private BrandService brandService;

    /**
     * Mutation to add a new brand.
     */
    @MutationMapping
    public Brand addBrand(@Argument String name, @Argument String description, @Argument String urlWebsite) {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setDescription(description);
        brand.setUrlWebsite(urlWebsite);
        return brandService.saveBrand(brand);
    }

    /**
     * Query to fetch all brands.
     */
    @QueryMapping
    public List<Brand> brands() {
        return brandService.getAllBrands();
    }

    /**
     * Query to find a brand by its ID.
     */
    @QueryMapping
    public Brand findBrand(@Argument Long id) {
        return brandService.getBrandById(id).orElseThrow(() -> new BrandNotFoundException("Brand with ID " + id + " not found."));
    }

    /**
     * Mutation to update a brand's details.
     */
    @MutationMapping
    public Brand updateBrand(@Argument Long id, @Argument String name, @Argument String description, @Argument String urlWebsite) {
        Brand updatedBrand = new Brand();
        updatedBrand.setName(name);
        updatedBrand.setDescription(description);
        updatedBrand.setUrlWebsite(urlWebsite);
        return brandService.updateBrand(id, updatedBrand);
    }

    /**
     * Mutation to delete a brand by its ID.
     */
    @MutationMapping
    public String deleteBrand(@Argument Long id) {
        brandService.deleteBrand(id);
        return "Brand with ID " + id + " has been deleted.";
    }
}
