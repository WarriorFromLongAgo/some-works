package com.orhonit.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;

import java.util.List;
import java.util.Map;

/**
 * 科室表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-30 17:07:15
 */
public interface TaDepartmentMemberService extends IService<TaDepartmentMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<TaDepartmentMemberEntity> selecLowerList(Integer userDept);

    PageUtils queryPageDepart(Map<String, Object> params);

    List<TaDepartmentMemberEntity> selectLowerUserList();
}

