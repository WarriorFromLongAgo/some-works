package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;

import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLSchool;
import com.orhon.smartcampus.modules.core.graphql.orm.entity.School;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class SchoolBaseFetcher {

    @Autowired
    private SessionFactory sessionFactory;
    private static SessionFactory factory;

    @PostConstruct
    public void init(){
        factory = sessionFactory;
    }

    public static DataFetcher schoolFetcher = new DataFetcher() {
        @Override
        public Object get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {

            List<GQLSchool> schools = new ArrayList<>();

            String hql = "from School";
            Query query = factory.openSession().createQuery(hql);
            List<School> schoolList = query.list();

            schoolList.forEach(school -> {
                GQLSchool gqlSchool = new GQLSchool();
                gqlSchool.setId(school.getId());



                schools.add(gqlSchool);
            });

            return schools;
        }
    };
}
