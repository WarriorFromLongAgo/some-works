package com.orhonit.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageUtils queryPage(Map<String, Object> params);

	PageUtils queryPageParty(Map<String, Object> params);

	List<SysUserEntity> listdept(Integer lowerId);

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
	 * 保存用户
	 */
	void save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);

	/**
	 * 用手机号登录
	 * @param mobile
	 * @return
	 */
	SysUserEntity queryByMobile(String mobile);

	/**
	 * 用身份证号登录
	 * @param userNickname
	 * @return
	 */
	SysUserEntity queryByIdCard(String userNickname);

	/*   
	*   @Description 所有用户
	*   @Author xiaobai
	*   @Date   2019/7/19 0019 上午 9:20
	*/
	List<SysUserEntity> getUser();
	/**
	 * 模糊搜索用户列表
	 * @author zy
	 * @serialData 2019年7月19日10:12:32
	 */
	PageUtils searchList(Map<String, Object> params);

	/**
	 * 修改用户头像
	 * @param headPortrait
	 * @param fileLocalName
	 */
	void updateHeadPortrait(String headPortrait, String fileLocalName);
/**
	 * APP端我的信息
	 */
	Map selectAppInfo();
	
	List<SysUserEntity> queryMobile(String mobile);
}
