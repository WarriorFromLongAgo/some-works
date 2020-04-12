package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;

import com.google.common.base.Optional;
import com.orhon.smartcampus.modules.base.mapper.SchoolsMapper;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.modules.base.service.IRegionsService;
import com.orhon.smartcampus.modules.core.graphql.gql.model.*;
import com.orhon.smartcampus.modules.core.graphql.gql.service.GQLUtil;
import com.orhon.smartcampus.modules.document.mapper.TeacherMapper;
import com.orhon.smartcampus.modules.teacher.service.TIInformationService;
import com.orhon.smartcampus.modules.teacher.mapper.TInformationMapper;
import com.orhon.smartcampus.modules.user.mapper.UsersMapper;
import com.orhon.smartcampus.utils.RemoveNullKeyValueUtils;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.beans.factory.annotation.Autowired;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

@Component

public class TeacherBaseFetcher {

    @Autowired
    private TIInformationService informationService;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private IRegionsService regionsService;


    private static TIInformationService staticTeacherService;

    private static IDictionaryService staticdictionaryService;

    private static IRegionsService staticregionsService;

    private static TeacherMapper staticTeacherMapper;



    @PostConstruct
    public void init(){
        staticTeacherMapper = teacherMapper;
        staticTeacherService = informationService;
        staticdictionaryService = dictionaryService;
        staticregionsService = regionsService;
    }
    public static DataFetcher teacherBaseFetcher = new DataFetcher() {

        @Override
        public Object get(DataFetchingEnvironment env) throws Exception {
            ArrayList<GQLTeacher> teacher = new ArrayList<>();

            Integer page = 0;
            Integer limit = 10;
            Integer id = null;

            if (env.containsArgument("page")){
                page = Integer.parseInt(env.getArgument("page").toString());
            }

            if (env.containsArgument("limit")){
                limit = Integer.parseInt(env.getArgument("limit").toString());
            }

            if (env.containsArgument("id")){
                id = Integer.parseInt(env.getArgument("id").toString());
            }

            if (page < 0) page = 0;
            if (page != 0) page = (page - 1) * limit;

            if(id != null){
                HashMap<String, Object> item = staticTeacherService.teacherDetails(id);
                GQLTeacher gqlTeacher = GQLUtil.Teacher(item);

                return gqlTeacher;
            }
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("page", page);
            map.put("limit", limit);
            map.put("withUser", env.getSelectionSet().contains("user"));
            map.put("withSchool", env.getSelectionSet().contains("school"));

            List<HashMap<String, Object>> list = staticTeacherService.PageListTeacher(map);
            list.forEach(item->{
                RemoveNullKeyValueUtils.removeNullValue(item);
                GQLTeacher gqlTeacher = GQLUtil.Teacher(item);
                teacher.add(gqlTeacher);
            });
            return teacher;
        }
    };
}
