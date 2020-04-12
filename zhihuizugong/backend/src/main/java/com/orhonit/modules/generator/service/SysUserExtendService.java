package com.orhonit.modules.generator.service;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.SysUserExtendEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户扩展表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 10:46:50
 */
public interface SysUserExtendService extends IService<SysUserExtendEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(SysUserExtendEntity sysUserExtendEntity);

    Map<String, Object> findUser(String idCard);

    Map<String, Object> findLeaderUserList(Map<String, Object> params);

    Map<String, Object> findPartyUserList(Map<String, Object> params);

    Map<String, Object> findPartyOneList();

    Map<String, Object> findAllData(Map<String, Object> params);

    Map<String,Object> findDeptData();

    Map<String, Object> findOrgData();

    Map<String, Object> findCommonSelect(Map<String, Object> params);

    Map<String, Object> findCommonSelectNotPage(Map<String, Object> params);
}

