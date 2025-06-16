package com.johnray100.io.brandservice.utils;

import graphql.ErrorClassification;

public enum GraphQLCustomError implements ErrorClassification {

    /** Represents an unexpected internal server error. */
    INTERNAL_SERVER_ERROR,

    /** Indicates an optimistic locking failure during concurrent updates. */
    OPTIMISTIC_LOCKING_FAILURE,

    /** Error thrown when a requested brand is not found. */
    BRAND_NOT_FOUND_ERROR,

    /** Error thrown when there is an issue with brand data integrity (e.g., invalid brand name). */
    BRAND_DATA_INTEGRITY_ERROR
}

