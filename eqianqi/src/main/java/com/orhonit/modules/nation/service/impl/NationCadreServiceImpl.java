package com.orhonit.modules.nation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.orhonit.common.utils.DateUtils;
import com.orhonit.common.utils.R;
import com.orhonit.common.utils.ShiroUtils;
import com.orhonit.modules.nation.entity.NationCadre;
import com.orhonit.modules.nation.mapper.NationCadreMapper;
import com.orhonit.modules.nation.service.NationCadreService;

import io.swagger.annotations.OAuth2Definition;

@Service
public class NationCadreServiceImpl extends ServiceImpl<NationCadreMapper, NationCadre> implements NationCadreService {

	@Autowired
	private NationCadreMapper nationCadreMapper;

	@Override
	public R insertNationCadre(NationCadre nationCadre) {
		if (nationCadre != null) {
			// 插入的时候通过idcard查询是否存在
			NationCadre dataNationCadre = nationCadreMapper.selectNationCadreByIdCard(nationCadre.getIdCard());
			if (null != dataNationCadre) {
				return R.Repeat();
			}
			nationCadre.getIdCard();
			nationCadre.setCreateUser(ShiroUtils.getUserId());
			nationCadre.setCreateTime(DateUtils.getNowTime());
			nationCadreMapper.insert(nationCadre);
		}
		return R.ok();
	}

	@Override
	public NationCadre selectNationCadreByIdCard(String idCard) {
		return nationCadreMapper.selectNationCadreByIdCard(idCard);
	}

	@Override
	public Map<String, Object>  selectNationCadreAll(Map<String, Object> params,int page,int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<NationCadre> listSize = nationCadreMapper.selectNationCadreAll(params);
		
		List<NationCadre> list = nationCadreMapper.selectNationCadreAll(params);
		Map<String, Object> results =new HashMap<>();
		results.put("size", listSize.size());
		results.put("list", list);
		return results;
	}

}
