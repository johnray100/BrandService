package com.johnray100.io.brandservice.utils;

public enum GraphQLDocument {

    FIND_BRAND_GQL("findBrand.gql"),  // GraphQL query file for finding a brand
    CREATE_BRAND_GQL("createBrand.gql"), // GraphQL mutation file for creating a brand
    UPDATE_BRAND_GQL("updateBrand.gql"), // GraphQL mutation file for updating a brand
    DELETE_BRAND_GQL("deleteBrand.gql"); // GraphQL mutation file for deleting a brand

    private final String filename;

    GraphQLDocument(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}

