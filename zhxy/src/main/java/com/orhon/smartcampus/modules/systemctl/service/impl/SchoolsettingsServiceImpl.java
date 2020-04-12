package com.orhon.smartcampus.modules.systemctl.service.impl;

import com.orhon.smartcampus.modules.systemctl.entity.Schoolsettings;
import com.orhon.smartcampus.modules.systemctl.mapper.SchoolsettingsMapper;
import com.orhon.smartcampus.modules.systemctl.service.ISchoolsettingsService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 学校设置 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class SchoolsettingsServiceImpl extends BaseServiceImpl<SchoolsettingsMapper, Schoolsettings>implements ISchoolsettingsService {

    @Override
    public HashMap selectSchoolSettingBySchoolID(Integer schoolID) {
        return this.baseMapper.getBySchoolID(schoolID);
    }
}
