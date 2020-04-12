package com.orhon.smartcampus.modules.document.service.impl;

import com.orhon.smartcampus.modules.document.entity.Teacher;
import com.orhon.smartcampus.modules.document.mapper.ExamineMapper;
import com.orhon.smartcampus.modules.document.mapper.TeacherMapper;
import com.orhon.smartcampus.modules.document.service.ITeacherService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class TeacherServiceImpl extends BaseServiceImpl<TeacherMapper, Teacher>implements ITeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public List<HashMap<String, Object>> getDocument(HashMap<String, Object> maps) {
        List<HashMap<String, Object>> base = teacherMapper.getDocument(maps);
        return base;
    }
    @Override
    public List<HashMap<String, Object>> getDocument(HashMap<String, Object> maps, PageDto dto) {
        // TODO Auto-generated method stub
        return teacherMapper.getDocumentpage(maps,dto);
    }

}
