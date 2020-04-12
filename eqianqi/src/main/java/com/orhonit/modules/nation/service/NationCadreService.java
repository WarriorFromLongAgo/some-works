package com.orhonit.modules.nation.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.R;
import com.orhonit.modules.nation.entity.NationCadre;


public interface NationCadreService extends IService<NationCadre> {

	/**
	 * 查询所有民族干部
	 * @param idcard
	 * @return
	 */
	public Map<String, Object> selectNationCadreAll(Map<String, Object> params,int page,int pageSize);
	
	/**
	 * 通过idcard查询民族干部信息
	 * @param idCard
	 * @return
	 */
	public NationCadre selectNationCadreByIdCard(String idCard);

	/**
	 * 插入民族干部信息
	 * @param nationCadre
	 * @return
	 */
	public R insertNationCadre(NationCadre nationCadre);
	
	
	
}
