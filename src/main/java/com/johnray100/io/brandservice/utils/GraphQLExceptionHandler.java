package com.johnray100.io.brandservice.utils;

import com.johnray100.io.brandservice.exception.BrandNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    /**
     * Handles exceptions thrown during GraphQL query execution.
     * Maps specific exceptions to custom GraphQL error responses.
     *
     * @param ex  the thrown exception
     * @param env the GraphQL data fetching environment
     * @return a GraphQL-formatted error
     */
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        String fieldName = env.getField().getName();

        if (ex instanceof ObjectOptimisticLockingFailureException) {
            log.error("Optimistic locking error in field '{}': {}", fieldName, ex.getMessage(), ex);
            return buildError(env,
                    GraphQLCustomError.OPTIMISTIC_LOCKING_FAILURE,
                    "Row was updated or deleted by another transaction",
                    409);

        } else if (ex instanceof BrandNotFoundException) {
            log.error("Brand not found in field '{}': {}", fieldName, ex.getMessage(), ex);
            return buildError(env,
                    GraphQLCustomError.BRAND_NOT_FOUND_ERROR,
                    ex.getMessage(),
                    404);

        } else {
            log.error("Unhandled exception in field '{}': {}", fieldName, ex.getMessage(), ex);
            return buildError(env,
                    GraphQLCustomError.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred",
                    500);
        }
    }

    /**
     * Builds a GraphQL error response.
     *
     * @param env     the GraphQL data fetching environment
     * @param type    the custom error type
     * @param message the error message
     * @param status  the HTTP status code for extensions
     * @return the built GraphQLError
     */
    private GraphQLError buildError(DataFetchingEnvironment env,
                                    GraphQLCustomError type,
                                    String message,
                                    int status) {
        return GraphqlErrorBuilder.newError()
                .errorType(type)
                .message(message)
                .extensions(Map.of("status", status))
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
