package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.orhon.smartcampus.modules.base.entity.Grades;
import com.orhon.smartcampus.modules.base.mapper.GradesMapper;
import com.orhon.smartcampus.modules.base.service.IGradesService;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLArrives;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLGrades;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLLang;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLPeriod;
import com.orhon.smartcampus.modules.core.graphql.gql.service.GQLUtil;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class GradeBaseFetcher {

    @Autowired
    private IGradesService informationService;
    
    @Autowired
    private GradesMapper gradesMapper;
    
    private static IGradesService staticGradeService;
    
    private static GradesMapper staticGradeMapper;
    
    

    @PostConstruct
    public void init(){
        staticGradeMapper = gradesMapper;
        staticGradeService = informationService;
    }


    /**
     * 处理students请求...
     */
    public static DataFetcher gradeFetcher = new DataFetcher() {

    	@Override
        public Object get(DataFetchingEnvironment env) throws Exception {
            ArrayList<GQLGrades> grade = new ArrayList<>();

            Integer page = 0;
            Integer limit = 10;

            if (env.containsArgument("page")){
                page = Integer.parseInt(env.getArgument("page").toString());
            }
            
            if (env.containsArgument("limit")){
            	limit = Integer.parseInt(env.getArgument("limit").toString());
            }
            if (page < 0) page = 0;
            if (page != 0) page = (page - 1) * limit;
            final Boolean withPeriod;
            DataFetchingFieldSelectionSet selectionSet =  env.getSelectionSet();
            if (selectionSet.contains("period/*")){
                withPeriod = true;
            }else{
                withPeriod = false;
            }
            List<HashMap<String,Object>> list = staticGradeMapper.getGradeList(page , limit , withPeriod);
            list.forEach(item->{
            	GQLGrades gqlGrades = GQLUtil.grades(item);
//                GQLGrades gqlGrades = new GQLGrades();
//                gqlGrades.setId((Long) item.get("id"));
//                gqlGrades.setMark((String)item.get("mark"));
//                gqlGrades.setName(JSON.toJSONString(item.get("grade_name")));
//                gqlGrades.setSlug((String) item.get("grade_slug"));
//                gqlGrades.setNumber((String) item.get("grade_number"));
//                gqlGrades.setOrder((Integer) item.get("grade_order"));
                if (withPeriod){
                    //安全转换integer类型到long类型。。。
                    Optional<Integer> periodIdold = Optional.fromNullable((Integer) item.get("period_id"));
                    Optional<Long> periodId = periodIdold.transform(new Function<Integer, Long>() {
                        @Override
                        public Long apply(Integer inputInt) {
                            if (inputInt != null)
                                return inputInt.longValue();
                            else
                                return null;
                        }
                    });
                    //建立返回类型数据
                    GQLPeriod periods = new GQLPeriod();
                    periods.setId(Long.valueOf(periodId.or(0L)));
                    gqlGrades.setPeriod(periods);
                }
                grade.add(gqlGrades);
            });
            return grade;
        }
    };

}
