package com.orhon.smartcampus.modules.student.gql.fetcher;

import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.student.gql.model.GqSInformation;
import com.orhon.smartcampus.modules.student.mapper.SInformationMapper;
import com.orhon.smartcampus.modules.student.service.SIInformationService;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.gql.model.GQLUser;
import com.orhon.smartcampus.modules.user.mapper.UsersMapper;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class GqSInformationBaseFetcher {

    @Autowired
    private SIInformationService informationService;

    private static SIInformationService staticInformationService;

    @Autowired
    private SInformationMapper informationMapper;

    private static SInformationMapper staticSInformationMapper;

    @PostConstruct
    public void init(){
        staticInformationService = informationService;
        staticSInformationMapper = informationMapper;
    }

    public static DataFetcher getStudent = new DataFetcher() {
        @Override
        public Object get(DataFetchingEnvironment environment) throws Exception {
        	List<SInformation> list = staticInformationService.list();
            return list;
        }
    };

//    public static DataFetcher usersDataFetcher = new DataFetcher() {
//        @Override
//        public Object get(DataFetchingEnvironment env) throws Exception {
//            ArrayList<GQLUser> users = new ArrayList<>();
//            //分页开始
//            if (env.containsArgument("page") || env.containsArgument("size")){
//
//            }
//            else {
//                List<Users> list = staticUsersService.list();
//                list.forEach(item -> {
//                    GQLUser user = new GQLUser();
//                    user.setUsername(item.getUsername());
//                    users.add(user);
//                });
//            }
//            return users;
//        }
//    };
}
