package com.orhon.smartcampus.modules.student.service.impl;

import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.student.entity.EclassRecords;
import com.orhon.smartcampus.modules.student.mapper.EclassRecordsMapper;
import com.orhon.smartcampus.modules.student.service.IEclassRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bao
 */
@Service
public class EclassRecordsServiceImpl extends BaseServiceImpl<EclassRecordsMapper, EclassRecords>implements IEclassRecordsService {

    @Autowired
    EclassRecordsMapper eclassRecordsMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String eclassRecordsSave(HashMap<String, Object> maps) {
        String studentIds =maps.get("studentIds").toString();
//        Integer goEclass = (Integer) maps.get("goEclass");
        String[] arry = studentIds.split(",");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String createTime = dateFormat.format( date );
        for (int i = 0; i < arry.length; i++) {
            Integer id = Integer.valueOf(arry[i]);
            EclassRecords entity = eclassRecordsMapper.findStudentId(id);
           if(entity == null){
               eclassRecordsMapper.eclassRecordsSave(maps , createTime , id);
           }else{
               eclassRecordsMapper.eclassRecordsUpdate(id);
               Integer originalId = entity.getGo_eclass();
               eclassRecordsMapper.eclassRecordsSave2(maps , originalId , createTime , id);
           }
        }
        return "ok";
    }
}
