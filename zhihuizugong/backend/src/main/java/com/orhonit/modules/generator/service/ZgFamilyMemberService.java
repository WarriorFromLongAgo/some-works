package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgFamilyMemberEntity;

import java.util.Map;

/**
 * 用户主要家庭成员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-23 11:11:38
 */
public interface ZgFamilyMemberService extends IService<ZgFamilyMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ZgFamilyMemberEntity zgFamilyMember);
}

