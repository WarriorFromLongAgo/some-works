package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.orhon.smartcampus.modules.base.mapper.GradesMapper;
import com.orhon.smartcampus.modules.base.mapper.PeriodsMapper;
import com.orhon.smartcampus.modules.base.service.IGradesService;
import com.orhon.smartcampus.modules.base.service.IPeriodsService;
import com.orhon.smartcampus.modules.core.graphql.gql.model.*;
import com.orhon.smartcampus.modules.core.graphql.gql.service.GQLUtil;
import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.modules.student.mapper.ArrivesMapper;
import com.orhon.smartcampus.modules.student.service.IArrivesService;
import com.orhon.smartcampus.utils.RemoveNullKeyValueUtils;
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
public class ArriveBaseFetcher{

    @Autowired
    private IArrivesService informationService;

    @Autowired
    private IGradesService gradesService;
    
    @Autowired
    private ArrivesMapper arrivesMapper;

    @Autowired
    private GradesMapper gradesMapper;

    @Autowired
    private IPeriodsService periodsService;
    
    private static IArrivesService staticArrivesService;
    
    private static ArrivesMapper staticArrivesMapper;
    private static GradesMapper staticGradeMapper;

    private static IGradesService staticGradeService;
    private static IPeriodsService staticPeriodService;

    

    @PostConstruct
    public void init(){
        staticArrivesMapper = arrivesMapper;
        staticGradeService = gradesService;
        staticArrivesService = informationService;
        staticPeriodService = periodsService;
        staticGradeMapper = gradesMapper;
    }


    /**
     * 处理students请求...
     */
    public static DataFetcher arrivesFetcher = new DataFetcher() {

    	@Override
        public Object get(DataFetchingEnvironment env) throws Exception {
            ArrayList<GQLArrives> arrives = new ArrayList<>();

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
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("page", page);
            map.put("limit", limit);

            final Boolean selectedGrade;
            DataFetchingFieldSelectionSet selectionSet =  env.getSelectionSet();
            if (selectionSet.contains("grade/*")){
                selectedGrade = true;
            }else{
                selectedGrade = false;
            }

            final Boolean selectedPeriods;
            if (selectionSet.contains("grade/*")){
                selectedPeriods = true;
            }else{
                selectedPeriods = false;
            }

            map.put("withGrade" , selectedGrade);
            map.put("withPeriod" , selectedPeriods);


            List<HashMap<String, Object>> list = staticArrivesService.PageListArrives(map);
            list.forEach(item->{
                RemoveNullKeyValueUtils.removeNullValue(item);
                GQLArrives gqlArrives = GQLUtil.Arrives(item);
                arrives.add(gqlArrives);
            });
            return arrives;
        }
    };

}
