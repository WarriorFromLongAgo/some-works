package com.orhonit.modules.generator.vo;

import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.UserDeptEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PartyVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //支部列表
    private List<UserDeptEntity> deptList;

    //领导班子人员
    private List<SysUserEntity> leaderUserList;
    //党员人员
    private List<SysUserEntity> partyUserList;
}
