//package com.orhon.smartcampus.modules.user.gql.service;
//
//import com.orhon.smartcampus.modules.core.graphql.GraphqlService;
//import com.orhon.smartcampus.modules.user.gql.fetcher.UserBaseFetcher;
//import graphql.execution.instrumentation.fieldvalidation.FieldValidationInstrumentation;
//import graphql.schema.StaticDataFetcher;
//import graphql.schema.idl.RuntimeWiring;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserGqlService extends GraphqlService {
//
//    public final static String SCHEMA_ROOT = "graphql/root.graphqls";
//    public final static String SCHEMA_USER = "graphql/user.graphqls";
//
//    @Override
//    protected String[] getSchema() {
//        return new String[]{SCHEMA_ROOT};
//    }
//
//    @Override
//    protected RuntimeWiring buildDynamicRuntimeWiring() {
//        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
//        builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("hello" , new StaticDataFetcher("world")));
////        builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("hello2" , new StaticDataFetcher("world2")));
////        builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("user" , UserBaseFetcher.usernameFetcher));
////        builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("users" , UserBaseFetcher.usersDataFetcher));
////        builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("test" , UserBaseFetcher.getDatabyTest));
//        return builder.build();
//    }
//
//    @Override
//    protected FieldValidationInstrumentation validateRequest() {
//        return null;
//    }
//}
