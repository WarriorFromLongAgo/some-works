package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;



import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLSchool;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLUser;
import com.orhon.smartcampus.modules.core.graphql.orm.entity.User;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class UserBaseFetcher {

    @Autowired
    private RedisTemplate redisTemplate;
    private static RedisTemplate staticRedisTemplate;


    @Autowired
    private RedisConnectionFactory factory;
    private static RedisConnectionFactory staticFactory;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static JdbcTemplate staticJdbcTemplate;


    @Autowired
    private SessionFactory sessionFactory;
    private static SessionFactory statisSessionFactory;


    private static final String users_user_sorted_set = "users_user_sorted_set";

    private static Jedis jedis;

    @PostConstruct
    public void init(){
        staticRedisTemplate = redisTemplate;
        staticFactory = factory;
        staticJdbcTemplate = jdbcTemplate;
        statisSessionFactory = sessionFactory;
    }

    private static void createIdDescSet(){
        Jedis jedis = (Jedis) staticFactory.getConnection().getNativeConnection();
        String cursor = ScanParams.SCAN_POINTER_START;
        String key = "user_users{*";
        ScanParams scanParams = new ScanParams();
        scanParams.match(key);
        scanParams.count(80000);
        ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
        List<String> list = scanResult.getResult();
        System.out.println(list.size());
        list.forEach( item -> {
            Map data = jedis.hgetAll(item);
            jedis.zadd(users_user_sorted_set , Long.valueOf(data.get("id").toString()) , item);
        });
    }

    private static void deleteIdDescSet(){
        //System.out.println(jedis.get(users_user_sorted_set));

        /*
        if (exist){
            jedis.del(users_user_sorted_set);
        }
        */
    }


    public static DataFetcher usersDataFetcher = new DataFetcher() {




        @Override
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public Object get(DataFetchingEnvironment env) throws Exception {
			return env;


            //多对多测试
            /*
            String hql = "from Module m where m.category = 'normal'";
            Query q = statisSessionFactory.openSession().createQuery(hql);
            q.setMaxResults(10);
            q.setFirstResult(0);
            List<Module> list = q.list();
            List<School> schools = list.get(0).getSchools();
            System.out.println(schools);
            return null;
            */



            //一对一测试

            List<GQLUser> users = new ArrayList<>();

            String hql = "from User";

            Query q = statisSessionFactory.openSession().createQuery(hql);
            q.setMaxResults(10);
            q.setFirstResult(1);

            List<User> list = q.list();

            list.forEach(item -> {
                GQLUser user = new GQLUser();
                user.setId(item.getId());
                user.setUsername(item.getUsername());
                user.setCreated_at(item.getCreatedAt());


                GQLSchool school = new GQLSchool();
                school.setId(item.getSchool().getId());
                school.setName(item.getSchool().getSchoolNameWithGQLLang());

                user.setSchool(school);

                users.add(user);
            });


            return users;

        }



    };


}
