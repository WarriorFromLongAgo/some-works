package com.orhon.smartcampus.modules.systemctl.service;

import com.orhon.smartcampus.modules.systemctl.entity.Schoolsettings;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 * 学校设置 服务类
 * </p>
 *
 * @author Orhon
 */
public interface ISchoolsettingsService extends BaseService<Schoolsettings> {

    HashMap selectSchoolSettingBySchoolID(Integer schoolID);
}
