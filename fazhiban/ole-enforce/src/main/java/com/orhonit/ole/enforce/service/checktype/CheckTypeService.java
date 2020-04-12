package com.orhonit.ole.enforce.service.checktype;

import java.util.List;
import java.util.Map;
import com.orhonit.ole.enforce.entity.CheckTypeEntity;

public interface CheckTypeService {
	
	/**
	 * 获取检查类型列表
	 * @param params
	 * @return
	 */
	List<CheckTypeEntity> getCheckTypeList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * 获取检查类型总数
	 * @param params
	 * @return
	 */
	Integer getCheckTypeCount(Map<String, Object> params);
	
	/**
	 * 添加检查类型
	 * @param CheckTypeEntity
	 * @return
	 * */
	public void save(CheckTypeEntity checkTypeEntity);
	
	/**
	 * 修改检查类型
	 * @param CheckTypeEntity
	 * @return
	 * */
	public void update(CheckTypeEntity checkTypeEntity);
	
	/**
	 * 删除检查类型
	 * @param CheckTypeEntity
	 * @return
	 * */
	public void delete(CheckTypeEntity checkTypeEntity);
	
	/**
	 * 根据ID查询
	 * */
	public CheckTypeEntity finCheckTypeById(int id);
	
	/**
	 * 根据部门ID查找title
	 * @param deptID
	 * @return
	 */
	public List<CheckTypeEntity> checkTypeByDeotId(String deptID);
}
