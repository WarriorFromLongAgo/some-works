package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.*;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.UserDeptEntity;
import com.orhonit.modules.sys.entity.UserOrgEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户扩展表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 10:46:50
 */
@Mapper
public interface SysUserExtendDao extends BaseMapper<SysUserExtendEntity> {

    List<SysUserEntity> findUser(String userNickname);

    List<SysUserExtendEntity> findExUser(Long userId);
    //根据单位类型获取单位
    List<UserOrgEntity> findOrgList(Integer orgType);
    //根据单位获取人员
    List<SysUserEntity> findUserList(@Param("orgId")Integer orgId,@Param("isPublic") Integer isPublic);
    //根据支部获取人员
    List<SysUserEntity> findPartyList(@Param("deptId")Integer deptId,@Param("isPublic") Integer isPublic);
    //根据上级id获取支部列表
    List<UserDeptEntity> findDeptList(Integer deptOneId);
    //党员一级下拉框
    List<UserDeptEntity> findOneDeptList();
    //根据用户id获取系统用户信息
    List<SysUserEntity> findUserInfo(Long userId);
    //根据身份证号获取数据
    List<ZgNearlyThreeYearsEntity> findNearThree(@Param("params") Map<String, Object> params);
    //根据用id获取数据
    List<ZgTaskEntity> findTask(@Param("params")Map<String, Object> params);
    //根据任务id获取数据
    List<ZgTaskFinishEntity> findTaskFinish(@Param("params")Map<String, Object> params);
    //根据用id获取数据
    List<SysUserExtendEntity> findExtend(@Param("params")Map<String, Object> params);
    //根据用户id获取家庭重要成员信息
    List<ZgFamilyMemberEntity> findFamilyEntity(Long userIddd);
    //三会一课统计数据
    Map<String, Object> findSanMeet(@Param("userId")Long userId);
    //承诺践诺/工作任务 统计
    Map<String, Object> findWorkData(@Param("params")Map<String, Object> params);
    //处罚情况(从联席单位获取)
    List<Map<String,Object>> findPunish(Long userIddd);
    //返回支部列表(党建地图用)
    List<Map<String,Object>> findDeptData();
    //返回单位列表(组织分布用)
    List<Map<String, Object>> findOrgData();

    //获取所有父级支部
    List<Map<String, Object>> findCommonSelect(@Param("params")Map<String, Object> params);
    //获取e家用户和公用用户党员
    List<Map<String, Object>> findPartyUser(@Param("params")Map<String, Object> params);
    //获取e家用户和公用用户干部
    List<Map<String, Object>> findOrgUser(@Param("params")Map<String, Object> params);
    //查询所有父级单位
    @Select("SELECT * FROM tb_user_org WHERE org_supper_id = -2")
    List<Map<String, Object>> findOrg(@Param("params")Map<String, Object> params);
    //获取父级底下所有子级支部人员
    List<Map<String, Object>> findDeptOneUser(@Param("params")Map<String, Object> params);
    //获取指定父级底下所有子级支部
    @Select("SELECT * FROM tb_user_dept WHERE dept_supper_id = #{params.deptOneId}")
    List<Map<String, Object>> findDeptZiList(@Param("params")Map<String, Object> params);
    //获取父级底下所有子级单位人员
    List<Map<String, Object>> findOrgOneUser(@Param("params")Map<String, Object> params);
    //获取指定父级底下所有子级单位
    @Select("SELECT * FROM tb_user_org WHERE org_type = #{params.orgOneId}")
    List<Map<String, Object>> findOrgZi(@Param("params")Map<String, Object> params);
    //指定支部下人员
    List<Map<String, Object>> findDeptTwoUser(@Param("params")Map<String, Object> params);
    //指定单位下人员
    List<Map<String, Object>> findOrgTwoUser(@Param("params")Map<String, Object> params);

    List<SysUserEntity> findPartyList2(@Param("deptId")Integer deptId,@Param("isPublic") Integer isPublic);
}
