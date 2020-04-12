package com.orhonit.modules.generator.vo;

import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.UserOrgEntity;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class CadreVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //单位
    private List<UserOrgEntity> orgList;
    //领导班子人员
    private List<SysUserEntity> leaderUserList;
    //党员人员
    private List<SysUserEntity> partyUserList;
    //职能职务
    private String orgContent;

}
