package com.orhon.smartcampus.modules.core.graphql.gql.fetcher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.base.entity.Periods;
import com.orhon.smartcampus.modules.base.mapper.PeriodsMapper;
import com.orhon.smartcampus.modules.base.service.IPeriodsService;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLArrives;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLGrades;
import com.orhon.smartcampus.modules.core.graphql.gql.model.GQLPeriod;
import com.orhon.smartcampus.modules.core.graphql.gql.service.GQLUtil;
import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.modules.student.mapper.ArrivesMapper;
import com.orhon.smartcampus.modules.student.service.IArrivesService;
import com.orhon.smartcampus.utils.ObjectToMap;
import com.orhon.smartcampus.utils.RemoveNullKeyValueUtils;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class PeriodBaseFetcher {

    @Autowired
    private IPeriodsService informationService;
    
    @Autowired
    private PeriodsMapper periodsMapper;
    
    private static IPeriodsService staticPeriodService;
    
    private static PeriodsMapper staticPeriodMapper;
    
    

    @PostConstruct
    public void init(){
        staticPeriodMapper = periodsMapper;
        staticPeriodService = informationService;
    }


    /**
     * 处理students请求...
     */
    public static DataFetcher periodFetcher = new DataFetcher() {

    	@Override
        public Object get(DataFetchingEnvironment env) throws Exception {
            ArrayList<GQLPeriod> periods = new ArrayList<>();

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
            
            QueryWrapper<Periods> queryWrapper = new QueryWrapper<>();
            queryWrapper.isNull("deleted_at");
            IPage<Periods> p = new Page<>(page,limit);
            List<Periods> list = staticPeriodService.page(p, queryWrapper).getRecords();
            list.forEach(item->{
                GQLPeriod gqlPeriod = GQLUtil.period(ObjectToMap.to(item));
                periods.add(gqlPeriod);
            });
            return periods;
        }
    };

}
