package com.orhon.smartcampus.modules.core.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.instrumentation.fieldvalidation.FieldValidationInstrumentation;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class GraphqlService {

    protected final static String TYPE_QUERY = "Query";
    protected final static String TYPE_MUTATION = "Mutation";

/*
    @Bean
    public GraphQLSchema schema() throws IOException {
        GraphQLSchema schema = loadSchma();
        return schema;
    }
*/

    /*
    public ExecutionResult service(String queryString) throws IOException {
        //String queryString = null;
        GraphQLSchema schema = loadSchma();
        GraphQL.Builder builder = GraphQL.newGraphQL(schema);

        if(validateRequest() != null) builder.instrumentation(validateRequest());

        GraphQL graphQL = builder.build();
        ExecutionResult executionResult = graphQL.execute(queryString);

        //Map<String, Object> result = executionResult.toSpecification();
        //List<GraphQLError> errors = executionResult.getErrors();

        return executionResult;

    }
    */

/*
    private GraphQLSchema loadSchma() throws IOException {
        SchemaParser parser = new SchemaParser();
        SchemaGenerator generator = new SchemaGenerator();
        String[] fileList = getSchema();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        for(String current: fileList) {
            //File schemaFile = loadSchema(current);
            String schemaSDL = loadSchema(current);
            //typeRegistry.merge(parser.parse(schemaFile));
            typeRegistry.merge(parser.parse(schemaSDL));
        }
        GraphQLSchema schema = generator.makeExecutableSchema(typeRegistry, buildDynamicRuntimeWiring());
        return schema;
    }


    protected String loadSchema(String fileName) throws IOException {
        URL url = Resources.getResource(fileName);
        String sdl = Resources.toString(url, Charsets.UTF_8);
        return sdl;
    }

    protected  abstract String[] getSchema();
    protected abstract RuntimeWiring buildDynamicRuntimeWiring();
    protected abstract FieldValidationInstrumentation validateRequest();
    */
}
