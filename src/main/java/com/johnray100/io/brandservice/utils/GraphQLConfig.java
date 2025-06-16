package com.johnray100.io.brandservice.utils;

import com.apollographql.federation.graphqljava.Federation;
import com.johnray100.io.brandservice.repository.BrandRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class GraphQLConfig {

    private final BrandRepository brandRepository;

    @Bean
    public GraphQL graphQL() throws IOException {
        InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("graphql/schema.graphqls");

        if (schemaStream == null) {
            throw new IOException("Schema file not found: graphql/schema.graphqls");
        }

        String sdl = new String(schemaStream.readAllBytes(), StandardCharsets.UTF_8);

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring wiring = buildWiring();

        GraphQLSchema schema = Federation.transform(typeRegistry, wiring)
                .resolveEntityType(env -> {
                    Map<String, Object> entity = env.getObject();
                    String typename = (String) entity.get("__typename");

                    if ("Brand".equals(typename)) {
                        return env.getSchema().getObjectType("Brand");
                    }

                    return null;
                })
                .fetchEntities(env -> {
                    List<Map<String, Object>> representations = env.getArgument("representations");
                    return representations.stream()
                            .map(rep -> {
                                String typename = (String) rep.get("__typename");

                                if ("Brand".equals(typename)) {
                                    Long id = Long.valueOf(rep.get("id").toString());
                                    return brandRepository.findById(id).orElse(null);
                                }

                                return null;
                            })
                            .collect(Collectors.toList());
                })
                .build();

        return GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                // Add data fetchers here if needed
                .build();
    }
}
