package com.orhon.smartcampus.modules.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.base.entity.Dictionary;
import com.orhon.smartcampus.modules.base.service.IDictionaryService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.modules.teacher.mapper.TInformationMapper;
import com.orhon.smartcampus.modules.teacher.service.TIInformationService;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教职工表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class TInformationServiceImpl extends BaseServiceImpl<TInformationMapper, TInformation> implements TIInformationService {


    @Autowired
    private TIInformationService service;


    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private InfoService infoService;
    @Autowired
    private TInformationMapper mapper;

    @Override
    public List<TInformation> getTeacherInfomation(HashMap<String, Object> maps, Map teacher_name) {
        List<TInformation> teacherInfomation = mapper.getTeacherInfomation(maps, teacher_name);
        List<TInformation> list = new ArrayList<TInformation>();
        for (TInformation tInformation : teacherInfomation) {
            list.add(tInformation);
        }
        return list;
    }

    @Override
    public List<TInformation> getTeacherInfomation(HashMap<String, Object> maps, PageDto dto, Map teacher_name) {
        // TODO Auto-generated method stub
        return mapper.getTeacherInfomationpage(maps, dto, teacher_name);
    }

    //总体统计
    public HashMap<String, Object> getStatistics() {
        HashMap<String, Object> my_information = infoService.getCurrentUser();
        QueryWrapper<TInformation> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Dictionary> dictionaryQueryWrapper = new QueryWrapper<>();
        List<TInformation> list = service.list(queryWrapper.eq("school_id", my_information.get("school_id")));
        //获取男女人数
        List<Dictionary> gender = dictionaryService.list(dictionaryQueryWrapper.eq("dictionary_code", "gender"));
        ArrayList<Object> sex = new ArrayList<>();
        for (Dictionary item : gender) {
            QueryWrapper<TInformation> queryWrappers = new QueryWrapper<>();
            Integer count = service.count(queryWrappers.eq("gender", item.getId()).eq("school_id", my_information.get("school_id")));
            HashMap<String, Object> ret = new HashMap<>();
            ret.put("id", item.getId());
            ret.put("count", count);
            ret.put("gender", item.getDictionary_name());
            sex.add(ret);
        }
        ArrayList<Object> Nature = new ArrayList<>();
        if (list.isEmpty()) {

        } else {
            //统计民族人数
            ArrayList<Object> nature = new ArrayList<>();
            for (TInformation item : list) {
                if (nature.contains(item.getNation())) {

                } else {
                    nature.add(item.getNation());
                }
            }
            ;
            for (Object item : nature) {
                Integer a = (Integer) item;
            }
            QueryWrapper<Dictionary> natureQueryWrapper = new QueryWrapper<>();
            List<Dictionary> natures = dictionaryService.list(natureQueryWrapper.in("id", nature));
            for (Dictionary item : natures) {
                QueryWrapper<TInformation> natureCountWrapper = new QueryWrapper<>();
                Integer count = service.count(natureCountWrapper.eq("nation", item.getId()).eq("school_id", my_information.get("school_id")));
                HashMap<String, Object> ret = new HashMap<>();
                ret.put("id", item.getId());
                ret.put("count", count);
                ret.put("nature", item.getDictionary_name());
                Nature.add(ret);
            }
        }
        //统计职称
        QueryWrapper<Dictionary> dictionaryJottitleQueryWrapper = new QueryWrapper<>();
        //获取职称人数
        List<Dictionary> jobtitle = dictionaryService.list(dictionaryJottitleQueryWrapper.eq("dictionary_code", "jobtitle"));
        ArrayList<Object> Jobtitle = new ArrayList<>();
        for (Dictionary item : jobtitle) {
            QueryWrapper<TInformation> queryWrappers = new QueryWrapper<>();
            Integer count = service.count(queryWrappers.eq("jobtitle", item.getId()).eq("school_id", my_information.get("school_id")));
            HashMap<String, Object> ret = new HashMap<>();
            ret.put("id", item.getId());
            ret.put("count", count);
            ret.put("jobtitle", item.getDictionary_name());
            Jobtitle.add(ret);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("sex", sex);
        data.put("nature", Nature);
        data.put("jobtitle", Jobtitle);
        return data;
    }


    //在职统计
    public HashMap<String, Object> getWorkStatistics() {
        HashMap<String, Object> my_information = infoService.getCurrentUser();
        QueryWrapper<TInformation> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Dictionary> dictionaryQueryWrapper = new QueryWrapper<>();
        List<TInformation> list = service.list(queryWrapper.eq("workstatus", "on-the-job").eq("school_id", my_information.get("school_id")));
        //获取男女人数
        List<Dictionary> gender = dictionaryService.list(dictionaryQueryWrapper.eq("dictionary_code", "gender"));
        ArrayList<Object> sex = new ArrayList<>();
        for (Dictionary item : gender) {
            QueryWrapper<TInformation> queryWrappers = new QueryWrapper<>();
            Integer count = service.count(queryWrappers.eq("workstatus", "on-the-job").eq("gender", item.getId()).eq("school_id", my_information.get("school_id")));
            HashMap<String, Object> ret = new HashMap<>();
            ret.put("id", item.getId());
            ret.put("count", count);
            ret.put("gender", item.getDictionary_name());
            sex.add(ret);
        }
        ArrayList<Object> Nature = new ArrayList<>();
        if (list.isEmpty()) {

        } else {
            //统计民族人数
            ArrayList<Object> nature = new ArrayList<>();
            for (TInformation item : list) {
                if (nature.contains(item.getNation())) {

                } else {
                    nature.add(item.getNation());
                }
            }
            ;
            for (Object item : nature) {
                Integer a = (Integer) item;
            }
            QueryWrapper<Dictionary> natureQueryWrapper = new QueryWrapper<>();
            List<Dictionary> natures = dictionaryService.list(natureQueryWrapper.in("id", nature));
            for (Dictionary item : natures) {
                QueryWrapper<TInformation> natureCountWrapper = new QueryWrapper<>();
                Integer count = service.count(natureCountWrapper.eq("workstatus", "on-the-job").eq("nation", item.getId()).eq("school_id", my_information.get("school_id")));
                HashMap<String, Object> ret = new HashMap<>();
                ret.put("id", item.getId());
                ret.put("count", count);
                ret.put("nature", item.getDictionary_name());
                Nature.add(ret);
            }
        }
        //统计职称
        QueryWrapper<Dictionary> dictionaryJottitleQueryWrapper = new QueryWrapper<>();
        //获取职称人数
        List<Dictionary> jobtitle = dictionaryService.list(dictionaryJottitleQueryWrapper.eq("dictionary_code", "jobtitle"));
        ArrayList<Object> Jobtitle = new ArrayList<>();
        for (Dictionary item : jobtitle) {
            QueryWrapper<TInformation> queryWrappers = new QueryWrapper<>();
            Integer count = service.count(queryWrappers.eq("workstatus", "on-the-job").eq("jobtitle", item.getId()).eq("school_id", my_information.get("school_id")));
            HashMap<String, Object> ret = new HashMap<>();
            ret.put("id", item.getId());
            ret.put("count", count);
            ret.put("jobtitle", item.getDictionary_name());
            Jobtitle.add(ret);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("sex", sex);
        data.put("nature", Nature);
        data.put("jobtitle", Jobtitle);
        return data;
    }


    //退休统计
    public HashMap<String, Object> getNoWorkStatistics() {
        HashMap<String, Object> my_information = infoService.getCurrentUser();
        QueryWrapper<TInformation> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Dictionary> dictionaryQueryWrapper = new QueryWrapper<>();
        List<TInformation> list = service.list(queryWrapper.eq("workstatus", "retired").eq("school_id", my_information.get("school_id")));
        //获取男女人数
        List<Dictionary> gender = dictionaryService.list(dictionaryQueryWrapper.eq("dictionary_code", "gender"));
        ArrayList<Object> sex = new ArrayList<>();
        for (Dictionary item : gender) {
            QueryWrapper<TInformation> queryWrappers = new QueryWrapper<>();
            Integer count = service.count(queryWrappers.eq("workstatus", "retired").eq("gender", item.getId()).eq("school_id", my_information.get("school_id")));
            HashMap<String, Object> ret = new HashMap<>();
            ret.put("id", item.getId());
            ret.put("count", count);
            ret.put("gender", item.getDictionary_name());
            sex.add(ret);
        }
        //统计民族人数
        ArrayList<Object> Nature = new ArrayList<>();
        if (list.isEmpty()) {

        } else {
            ArrayList<Object> nature = new ArrayList<>();
            for (TInformation item : list) {
                if (nature.contains(item.getNation())) {

                } else {
                    nature.add(item.getNation());
                }
            }
            ;
            for (Object item : nature) {
                Integer a = (Integer) item;
            }
            QueryWrapper<Dictionary> natureQueryWrapper = new QueryWrapper<>();
            List<Dictionary> natures = dictionaryService.list(natureQueryWrapper.in("id", nature));
            for (Dictionary item : natures) {
                QueryWrapper<TInformation> natureCountWrapper = new QueryWrapper<>();
                Integer count = service.count(natureCountWrapper.eq("workstatus", "retired").eq("nation", item.getId()).eq("school_id", my_information.get("school_id")));
                HashMap<String, Object> ret = new HashMap<>();
                ret.put("id", item.getId());
                ret.put("count", count);
                ret.put("nature", item.getDictionary_name());
                Nature.add(ret);
            }
        }
        //统计职称
        QueryWrapper<Dictionary> dictionaryJottitleQueryWrapper = new QueryWrapper<>();
        //获取职称人数
        List<Dictionary> jobtitle = dictionaryService.list(dictionaryJottitleQueryWrapper.eq("dictionary_code", "jobtitle"));
        ArrayList<Object> Jobtitle = new ArrayList<>();
        for (Dictionary item : jobtitle) {
            QueryWrapper<TInformation> queryWrappers = new QueryWrapper<>();
            Integer count = service.count(queryWrappers.eq("workstatus", "retired").eq("jobtitle", item.getId()).eq("school_id", my_information.get("school_id")));
            HashMap<String, Object> ret = new HashMap<>();
            ret.put("id", item.getId());
            ret.put("count", count);
            ret.put("jobtitle", item.getDictionary_name());
            Jobtitle.add(ret);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("sex", sex);
        data.put("nature", Nature);
        data.put("jobtitle", Jobtitle);
        return data;
    }

    @Override
    public HashMap selectTeacherInfoById(Long teacherId) {
        return this.baseMapper.selectTeacherInfoById(teacherId);
    }

    //
    @Override
    public List<HashMap<String, Object>> getEclassIds(String user_id) {
        // TODO Auto-generated method stub
        return mapper.getEclassIds(user_id);
    }

    @Override
    public HashMap<String, Object> getSexCount(String id) {
        // TODO Auto-generated method stub
        return mapper.getSexCount(id);
    }

    @Override
    public List<HashMap<String, Object>> getNationCount(String id) {
        // TODO Auto-generated method stub
        return mapper.getNationCount(id);
    }


    /**
     * 通过user_id 获取教师姓名
     *
     * @param user_id
     * @return
     */
    @Override
    public String getTeacherName(String user_id) {
        String teachername = mapper.getTeacherName(user_id);
        return teachername;
    }


    @Override
    public List<HashMap<String, Object>> PageListTeacher(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        return mapper.PageListTeacher(map);
    }

//    @Override
    public HashMap<String, Object> teacherDetails(Integer id) {
        // TODO Auto-generated method stub
        return mapper.teacherDetails(id);
    }

}
