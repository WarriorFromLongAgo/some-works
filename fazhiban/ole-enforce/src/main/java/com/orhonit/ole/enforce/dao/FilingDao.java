package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.dto.RightDTO;
import com.orhonit.ole.enforce.dto.api.ApiBaseLawInfo;
import com.orhonit.ole.enforce.dto.api.ApiBaseLawMapDTO;
import com.orhonit.ole.enforce.dto.ps.AreaAndPotAndProPotDTO;
import com.orhonit.ole.enforce.dto.ps.AreaAndPotDTO;
import com.orhonit.ole.enforce.dto.ps.PotenceListDTO;
import com.orhonit.ole.enforce.dto.ps.RigAndPotListDTO;

@Mapper
public interface FilingDao {

	//根据当前登录人部门获取事项权责数据列表
	List<RightDTO> rightAll(String dept_id);
	
	//根据事项权责获取执法依据
	List<RightDTO> zfyjList(String id);
	
	//根据事项权责获取处罚依据
	List<RightDTO> cfyjList(String id);
	/**
	 * ps 权责列表查询
	 */
	List<PotenceListDTO> selectPotenceList(@Param("plMap") Map<String, Object> plMap);
	/**
	 * ps 权责列表查询
	 */
	List<PotenceListDTO> selectPotenceListBydeptId(@Param("plMap") Map<String, Object> plMap);
	/**
	 * ps 权责列表查询统计
	 */
	int selectPotenceListBydeptIdCount(@Param("plMap") Map<String, Object> plMap);

	/**
	 * ps
	 * 各个地区权责清单统计
	 * @return
	 */
	List<AreaAndPotDTO> AreaAndPotSelect();
	/**
	 * ps
	 * 一个地区下的一个部门或全部部门的权责分类统计
	 * @param areaId 区域ID
	 * @param deptId 部门ID
	 * @return
	 */
	List<AreaAndPotAndProPotDTO> getAreaAndPotAndProPot(@Param("areaAndPotAndProPotMap") Map<String, Object> areaAndPotAndProPotMap);
	/**
	 * ps
	 * 权责清单（条形图）
	 * @return
	 */
	List<RigAndPotListDTO> RigAndPotList();
	
	/**
	 * 根据权责id获取权责依据
	 * */
	List<ApiBaseLawMapDTO> wzList(String id);
	
	/**
	 * ps 权责详细信息
	 */
	AreaAndPotDTO potenceDetail(@Param("map") Map<String, Object> map);

	List<ApiBaseLawInfo> getLawList(String deptId);

}
