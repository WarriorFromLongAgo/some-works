package com.orhonit.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.vo.UserAndOrgVO;
import com.orhonit.modules.sys.entity.SysUserEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(@Param("userId") Long userId,@Param("menuType") Integer menuType);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	
	/**
	 * 根据用户名，查询用户id
	 */
	long userIdByUserName(String username);

	@Select("SELECT su.`user_id` AS userId , su.`user_true_name` AS userTrueName FROM `sys_user` AS su WHERE su.`lower_id` = #{lowerId}")
	List<SysUserEntity> listdept(Integer lowerId);

	/**
	 * 手机号登录
	 * @param mobile
	 * @return
	 */
	SysUserEntity queryByMobile(String mobile);

	/**
	 * 身份证号登录
	 * @param userNickname
	 * @return
	 */
	SysUserEntity queryByIdCard(String userNickname);

	@Select("select user_id , user_true_name from sys_user")
	List<SysUserEntity> getUser();
	/**
	 * 模糊搜索用户列表总条数
	 * @author zy
	 * @param params
	 * @return
	 */
	List<SysUserEntity> searchListCount(@Param("params")Map<String, Object> params);
	/**
	 * 查询用详细
	 * @author zy
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> searchList(@Param("params")Map<String, Object> params);
	@Select("select user_id userId,user_true_name userTrueName,user_nickname userNickname,mobile,is_party_member isPartyMember,is_public isPublic,is_unit_leadership isUnitLeadership from sys_user where user_id = #{createBy}")
	SysUserEntity selectInfo(@Param("createBy")Long createBy);
	@Update("update sys_user set user_head_picture = #{headPortrait} where user_nickname = #{fileLocalName}")
	void updateHeadPortrait(@Param("headPortrait")String headPortrait,@Param("fileLocalName") String fileLocalName);
	
	/**
	 * 查询用户信息和所在单位名称
	 * @param userTrueName
	 * @param userNickname
	 * @return
	 */
	List<UserAndOrgVO>getUserSelectList(@Param("params")Map<String,Object>params);

	@Select("select * from sys_user where mobile = #{mobile}")
	List<SysUserEntity> queryMobile(String mobile);
}
