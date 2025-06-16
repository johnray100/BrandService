package com.johnray100.io.brandservice.response;

import com.johnray100.io.brandservice.entity.Brand;
import lombok.Data;

import java.util.List;

@Data
public class BrandQueryResponse {

    private DataWrapper data;
    private List<GraphQLError> errors;

    @Data
    public static class DataWrapper {
        private Brand findBrand;
    }

    @Data
    public static class GraphQLError {
        private String message;
        private List<Location> locations;
        private List<String> path;

        /**
         * Represents the location in the GraphQL query where the error occurred.
         */
        @Data
        public static class Location {
            private int line;
            private int column;
        }
    }
}
