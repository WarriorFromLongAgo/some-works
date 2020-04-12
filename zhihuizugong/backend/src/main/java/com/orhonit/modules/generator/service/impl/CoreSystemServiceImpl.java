package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreSystemDao;
import com.orhonit.modules.generator.entity.CoreSystemEntity;
import com.orhonit.modules.generator.service.CoreSystemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("coreSystemService")
public class CoreSystemServiceImpl extends ServiceImpl<CoreSystemDao, CoreSystemEntity> implements CoreSystemService {

	@Autowired
	CoreSystemDao coreSystemDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		String titleType =  (String) params.get("titleType");
    	Page<CoreSystemEntity> page = this.selectPage(
                new Query<CoreSystemEntity>(params).getPage(),
                new EntityWrapper<CoreSystemEntity>().and(StringUtils.isNotBlank(titleType) , "title_type="+titleType)
        );
		page.setTotal(this.selectCount(new EntityWrapper<CoreSystemEntity>().and(StringUtils.isNotBlank(titleType) , "title_type="+titleType)));
        return new PageUtils(page);


    }

	@Override
	public List<CoreSystemEntity> queryPage1() {
		List<CoreSystemEntity> list = coreSystemDao.select();
		Map map = new HashMap<>();
		for (CoreSystemEntity coreSystemEntity : list) {
			if(coreSystemEntity.getTitleType() == coreSystemEntity.getTitleType()){
				String content = coreSystemEntity.getContent();
//				list.add();
			}
			System.out.println(coreSystemEntity.getContent());
			/*int type = coreSystemEntity.getTitleType();
			map.put("type",type);
			String content = coreSystemEntity.getContent();
			map.put("content",content);*/
		}
		return list;
	}
}