//package com.orhon.smartcampus.modules.user.gql.fetcher;
//
//import com.orhon.smartcampus.modules.user.entity.Users;
//import com.orhon.smartcampus.modules.user.gql.model.GQLUser;
//import com.orhon.smartcampus.modules.user.mapper.UsersMapper;
//import com.orhon.smartcampus.modules.user.service.IUsersService;
//import graphql.schema.DataFetcher;
//import graphql.schema.DataFetchingEnvironment;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Component
//public class UserBaseFetcher {
//
//    @Autowired
//    private IUsersService usersService;
//
//    private static IUsersService staticUsersService;
//
//    @Autowired
//    private UsersMapper usersMapper;
//
//    private static UsersMapper staticUserMapper;
//
//    @PostConstruct
//    public void init(){
//        staticUsersService = usersService;
//        staticUserMapper = usersMapper;
//    }
//
//    public static DataFetcher usernameFetcher = new DataFetcher() {
//        @Override
//        public Object get(DataFetchingEnvironment environment) throws Exception {
//            GQLUser user = new GQLUser();
//            user.setUsername("test123123");
//            return user;
//        }
//    };
//
//    public static DataFetcher getByIdFetcher = new DataFetcher() {
//        @Override
//        public Object get(DataFetchingEnvironment environment) throws Exception {
//
//
//            return null;
//        }
//    };
//
//    public static DataFetcher getDatabyTest = new DataFetcher() {
//        @Override
//        public Object get(DataFetchingEnvironment environment) throws Exception {
//            Integer page = (Integer)environment.getArgument("page");
//            GQLUser user = new GQLUser();
//            user.setUsername(String.valueOf(page));
//            return user;
//        }
//    };
//
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
//}
