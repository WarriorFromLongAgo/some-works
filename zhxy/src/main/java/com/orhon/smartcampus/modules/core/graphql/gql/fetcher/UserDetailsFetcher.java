package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;


import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsFetcher {


    public static DataFetcher detailFetcher = new DataFetcher() {
        @Override
        public Object get(DataFetchingEnvironment environment) throws Exception {
            return null;
        }
    };
}



