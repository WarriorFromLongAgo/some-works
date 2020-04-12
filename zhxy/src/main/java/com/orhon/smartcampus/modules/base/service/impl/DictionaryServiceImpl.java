package com.orhon.smartcampus.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.modules.base.mapper.DictionaryMapper;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, Dictionary>implements IDictionaryService {


	@Autowired
	private DictionaryMapper mapper;

    @Autowired
    private InfoService infoService;
    
    

    @Override
    public List<HashMap<String, Object>>  getDicOptionsByCode(String dictionaryCode) {
        Long userId = infoService.getCurrentLoginUserId();

        return baseMapper.getDicOptionsByCode(dictionaryCode);
//        List<HashMap<String, Object>> dicOptions = dictionaryMapper.getDicOptionsByCode(dictionaryCode);

//        List<Integer> list=new ArrayList<Integer>();
//
//        QueryWrapper<OrgDepartments> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in("id",list);
//        return R.ok().put("data",dicOptions );

    }

	@Override
	public HashMap<String, Object> getDictionaryList(String dictionaryCode) {
		  List<Dictionary> dictionaryList = mapper.getDictionaryList1(dictionaryCode);
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  for (Dictionary dictionary : dictionaryList) {
			if(dictionary.getDictionary_code()!=null && dictionary.getValue_code()!=null) {
				map.put(dictionary.getValue_code(), dictionary.getDictionary_name());
			}
		}
		return map;
	}

	@Override
	public HashMap<String, Object> getDictionaryList() {
		  List<Dictionary> dictionaryList = mapper.getDictionaryList();
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  for (Dictionary dictionary : dictionaryList) {
			if(dictionary.getDictionary_code()!=null && dictionary.getValue_code()!=null) {
				map.put(dictionary.getDictionary_code()+dictionary.getValue_code(), dictionary.getDictionary_name());
			}
		}
		return map;
	}

	
}
