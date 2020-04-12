package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.*;
import com.orhonit.modules.generator.entity.*;
import com.orhonit.modules.generator.service.EjSchedulingService;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("ejSchedulingService")
public class EjSchedulingServiceImpl extends ServiceImpl<EjSchedulingDao, EjSchedulingEntity> implements EjSchedulingService {

    @Autowired
    private EjSchedulingDao ejSchedulingDao;
    @Autowired
    private EjSchedulingPeopleDao ejSchedulingPeopleDao;
    @Autowired
    private EjSchedulingFileDao ejSchedulingFileDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private ZgTaskDao zgTaskDao;
    @Autowired
    private ZgTaskFinishDao zgTaskFinishDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long createUser = null;
        if((String)params.get("createUser") != null && !"".equals((String)params.get("createUser"))){
            createUser = Long.parseLong((String)params.get("createUser"));
        }
        Long myUser = null;
        if((String)params.get("myUser") != null && !"".equals((String)params.get("myUser"))){
            myUser = Long.parseLong((String)params.get("myUser"));
        }
        String createTime = (String)params.get("createTime");
        String title = (String)params.get("title");
        Page<EjSchedulingEntity> page = this.selectPage(
                new Query<EjSchedulingEntity>(params).getPage(),
                new EntityWrapper<EjSchedulingEntity>().and(createUser != null && createUser != 0,"create_user="+createUser)
                .and(myUser != null && myUser != 0,"id = (select scheduling_id from ej_scheduling_people where user_id = "+myUser+")")
                .and(StringUtils.isNotBlank(createTime)," DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_FORMAT("+createTime+",'%Y-%m-%d')")
                .and(StringUtils.isNotBlank(title),"title = "+"'"+title+"'")
        );
        page.setTotal(this.selectCount(new EntityWrapper<EjSchedulingEntity>()
                        .and(createUser != null && createUser != 0,"create_user="+createUser)
                        .and(myUser != null && myUser != 0,"id = (select scheduling_id from ej_scheduling_people where user_id = "+myUser+")")
                        .and(StringUtils.isNotBlank(createTime)," DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_FORMAT("+createTime+",'%Y-%m-%d')")
                        .and(StringUtils.isNotBlank(title),"title = "+"'"+title+"'"))
                        );
        List<EjSchedulingEntity> list= ejSchedulingDao.selectList(new EntityWrapper<EjSchedulingEntity>());
        if(list.size() > 0){
            for (EjSchedulingEntity scheduling:list) {
                if(scheduling.getSatrtTime() != null){
                    if(scheduling.getSatrtTime().getTime() < new Date().getTime() && scheduling.getStats() != 3){
                        ejSchedulingDao.updateStats(scheduling.getId());
                    }
                }
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void save(EjSchedulingEntity ejScheduling) {
        SysUserEntity compereUser = sysUserDao.selectInfo(ejScheduling.getCompere());
        ejScheduling.setCreateTime(new Date());
        ejScheduling.setCompereName(compereUser.getUserTrueName());
        ejSchedulingDao.save(ejScheduling);
    }

    @Override
    public EjSchedulingEntity selectSchedulingInfo(String id) {
        EjSchedulingEntity ejSchedulingEntity = ejSchedulingDao.selectById(id);
//        List<EjSchedulingPeopleEntity> peopleList = ejSchedulingPeopleDao.selectList(new EntityWrapper<EjSchedulingPeopleEntity>().and("scheduling_id ="+"'"+id+"'"));
//        if(peopleList.size() > 0){
//            for (EjSchedulingPeopleEntity peopleEntity: peopleList) {
//                List<ZgTaskEntity> taskList = zgTaskDao.selectList(new EntityWrapper<ZgTaskEntity>().and("people_id ="+peopleEntity.getId()));
//                if(taskList.size() > 0){
//                    for (ZgTaskEntity task:taskList) {
//                        List<ZgTaskFinishEntity> taskFinishList = zgTaskFinishDao.selectList(new EntityWrapper<ZgTaskFinishEntity>().and("task_id ="+task.getId()).orderBy("schedule desc,create_time desc"));
//                        if(taskFinishList.size() > 0){
//                            task.setSchedule(taskFinishList.get(0).getSchedule());
//                        }
//                    }
//                }
//                peopleEntity.setTaskList(taskList);
//            }
//        }
//        ejSchedulingEntity.setPeopleList(peopleList);
        List<EjSchedulingFileEntity> fileList = ejSchedulingFileDao.selectList(new EntityWrapper<EjSchedulingFileEntity>().and("scheduling_id ="+"'"+id+"'"));
        ejSchedulingEntity.setFileList(fileList);
        return ejSchedulingEntity;
    }

    @Override
    public EjSchedulingEntity selectschedulingTaskInfo(String id,Long userId) {
        EjSchedulingEntity ejSchedulingEntity = ejSchedulingDao.selectById(id);
        List<EjSchedulingPeopleEntity> people = ejSchedulingPeopleDao.selectList(new EntityWrapper<EjSchedulingPeopleEntity>().and("scheduling_id ="+"'"+id+"'").and("user_id ="+userId));
        if(people.size() > 0){
            ejSchedulingEntity.setPeople(people.get(0));
        }
        List<EjSchedulingPeopleEntity> peopleList = ejSchedulingPeopleDao.selectList(new EntityWrapper<EjSchedulingPeopleEntity>().and("scheduling_id ="+"'"+id+"'"));
        if(peopleList.size() > 0){
            for (EjSchedulingPeopleEntity peopleEntity: peopleList) {
                List<ZgTaskEntity> taskList = zgTaskDao.selectList(new EntityWrapper<ZgTaskEntity>().and("people_id ="+peopleEntity.getId()));
                if(taskList.size() > 0){
                    for (ZgTaskEntity task:taskList) {
                        List<ZgTaskFinishEntity> taskFinishList = zgTaskFinishDao.selectList(new EntityWrapper<ZgTaskFinishEntity>().and("task_id ="+task.getId()).orderBy("schedule desc,create_time desc"));
                        if(taskFinishList.size() > 0){
                            task.setSchedule(taskFinishList.get(0).getSchedule());
                        }
                    }
                }
                peopleEntity.setTaskList(taskList);
            }
        }
        ejSchedulingEntity.setPeopleList(peopleList);
        List<EjSchedulingFileEntity> fileList = ejSchedulingFileDao.selectList(new EntityWrapper<EjSchedulingFileEntity>().and("scheduling_id ="+"'"+id+"'"));
        ejSchedulingEntity.setFileList(fileList);
        return ejSchedulingEntity;
    }

    @Override
    public void updateStatus(String schedulingId) {
        ejSchedulingDao.updateStatus(schedulingId);
    }

}