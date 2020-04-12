package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.dao.*;
import com.orhonit.modules.generator.entity.*;
import com.orhonit.modules.generator.service.ZgTaskService;
import com.orhonit.modules.sys.dao.SysUserDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service("zgTaskService")
public class ZgTaskServiceImpl extends ServiceImpl<ZgTaskDao, ZgTaskEntity> implements ZgTaskService {

    @Autowired
    private ZgTaskDao zgTaskDao;
    
    @Autowired
    private ZgTaskFinishDao zgTaskFinishDao;
    
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
	private EjSchedulingPeopleDao ejSchedulingPeopleDao;
	@Autowired
	private EjSchedulingDao ejSchedulingDao;
    @Autowired
    private EjSchedulingFileDao ejSchedulingFileDao;
    

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	int currPage = 1;
    	int limit = 10;

    	 if(params.get("page") != null){
    		 currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
         int page = (currPage-1)*limit;
         List<ZgTaskEntity> listSize= zgTaskDao.selectCount(params);
         params.put("page", page);
         params.put("limit", limit);
         Page<ZgTaskEntity> infPage = new Page<>(currPage,limit);
         List<ZgTaskEntity> list= zgTaskDao.selectAllList(params);
         if(list.size() > 0){
             for (ZgTaskEntity zgTaskEntity:list) {
                 if(zgTaskEntity.getCompletionTime() != null){
                 	if(StringUtils.isNotBlank(zgTaskEntity.getTaskType())){
                 		if(zgTaskEntity.getTaskType().equals("4") && zgTaskEntity.getStatus() != null){
							if(zgTaskEntity.getCompletionTime().getTime() < new Date().getTime() && zgTaskEntity.getStatus() != 3){
								zgTaskEntity.setStatus(4);
								zgTaskDao.updateById(zgTaskEntity);
							}
						}
					}
                 }
             }
         }
		 infPage.setTotal(listSize.size());
		 infPage.setRecords(list);
		return new PageUtils(infPage);

    }

    @Override
    public void save(ZgTaskEntity zgTask) {
        zgTask.setCreateTime(new Date());
        zgTask.setStatus(2);
        zgTaskDao.save(zgTask);
        ZgTaskFinishEntity taskFinishEntity = new ZgTaskFinishEntity();
        taskFinishEntity.setTaskId(zgTask.getId());
        taskFinishEntity.setContent("任务开始");
        taskFinishEntity.setSchedule(1);
        taskFinishEntity.setCreateTime(new Date());
        taskFinishEntity.setCreateBy(zgTask.getUserId());
        SysUserEntity userEntity = sysUserDao.selectInfo(zgTask.getUserId());
        taskFinishEntity.setCreateName(userEntity.getUserTrueName());
        taskFinishEntity.setContentType("1");
        zgTaskFinishDao.insert(taskFinishEntity);
    }

	@Override
	public ZgTaskEntity selectTaskInfo(Integer id,String schedulingId) {
//		ZgTaskEntity zgTaskEntityVo = new ZgTaskEntity();
		zgTaskDao.updateIsRead(id);
		ZgTaskEntity zgTask = zgTaskDao.selectTask(id);
		if(StringUtils.isNotBlank(schedulingId)){
			List<Long> joinPeople = ejSchedulingPeopleDao.selectJoinPeople(schedulingId);
			EjSchedulingEntity ejSchedulingEntity = ejSchedulingDao.selectById(schedulingId);
			zgTask.setJoinPeopleList(joinPeople);
			zgTask.setSchedulingCompere(ejSchedulingEntity.getCompere());
			zgTask.setSchedulingCreateUser(ejSchedulingEntity.getCreateUser());
		}
//		zgTaskEntityVo.setId(zgTask.getId());
//		zgTaskEntityVo.setCreateTime(zgTask.getCreateTime());
//		zgTaskEntityVo.setTaskType(zgTask.getTaskType());
//		zgTaskEntityVo.setUserId(zgTask.getUserId());
//		zgTaskEntityVo.setWorkTask(zgTask.getWorkTask());
		//查询完成情况
		List<ZgTaskFinishEntity> completionList = zgTaskFinishDao.selectCompletion(id);
		//查询督办情况
		List<ZgTaskFinishEntity> rigorousList = zgTaskFinishDao.selectRigorous(id);
		zgTask.setCompletionList(completionList);
		zgTask.setRigorousList(rigorousList);
		List<ZgTaskFinishEntity> finishList = zgTaskFinishDao.selectList(new EntityWrapper<ZgTaskFinishEntity>().and("task_id ="+zgTask.getId()).and("schedule != 0").orderBy("create_time asc"));
		if(finishList.size() > 0){
            for (ZgTaskFinishEntity zgTaskFinishEntity:finishList) {
                List<EjSchedulingFileEntity> fileList = ejSchedulingFileDao.selectList(new EntityWrapper<EjSchedulingFileEntity>().and("finish_id ="+zgTaskFinishEntity.getId()));
                zgTaskFinishEntity.setFileList(fileList);
            }
        }
		zgTask.setFinishList(finishList);
		return zgTask;
	}

	@Override
	public void deleteTask(Integer id) {
		zgTaskDao.deleteById(id);
		zgTaskFinishDao.deleteTaskFinish(id);
	}

	@Override
	public void updateStatus(Integer id) {
		ZgTaskEntity zgTask = zgTaskDao.selectTask(id);
		List<ZgTaskFinishEntity> recordList = zgTaskFinishDao.selectList(new EntityWrapper<ZgTaskFinishEntity>().and("task_id ="+zgTask.getId()).and("schedule = 100").orderBy("create_time desc"));
		if(recordList.size() == 0){
			List<ZgTaskFinishEntity> taskFinishList = zgTaskFinishDao.selectList(new EntityWrapper<ZgTaskFinishEntity>().and("task_id ="+zgTask.getId()).and("content_type = '1'").groupBy("create_by").orderBy("create_time desc"));
			SysUserEntity user = sysUserDao.selectInfo(taskFinishList.get(0).getCreateBy());
			ZgTaskFinishEntity finish = new ZgTaskFinishEntity();
			finish.setContent("任务完成");
			finish.setCreateTime(new Date());
			finish.setCreateBy(taskFinishList.get(0).getCreateBy());
			finish.setTaskId(id);
			finish.setCreateName(user.getUserTrueName());
			finish.setSchedule(100);
			finish.setContentType("1");
			zgTaskFinishDao.insert(finish);
		}
		zgTaskDao.updateStatus(id);
	}

	@Override
	public Map<String, Object> statistiList(Map<String, Object> params) {
		List<ZgTaskEntity> taskList = zgTaskDao.statistiList(params);
		List<ZgTaskEntity> pieList = zgTaskDao.selectPieChart(params);
		Map<String,Object> map = new HashMap<>();
		map.put("taskStatis", taskList.get(0));
//		map.put("pie", pieList);
		return map;
	}

	@Override
	public void updateTaskClaim(ZgTaskEntity zgTaskEntity) {
		zgTaskEntity.setClaimTime(new Date());
        zgTaskEntity.setTaskType("3");
        zgTaskEntity.setCreateTime(new Date());
		zgTaskDao.save(zgTaskEntity);
		List<EjSchedulingPeopleEntity> peopleList = ejSchedulingPeopleDao.selectList(new EntityWrapper<EjSchedulingPeopleEntity>().and("id ="+zgTaskEntity.getPeopleId()));
		if(peopleList.size() > 0){
			ZgTaskFinishEntity taskFinishEntity = new ZgTaskFinishEntity();
			taskFinishEntity.setTaskId(zgTaskEntity.getId());
			taskFinishEntity.setCreateTime(new Date());
			taskFinishEntity.setCreateBy(peopleList.get(0).getUserId());
			SysUserEntity userEntity = sysUserDao.selectInfo(peopleList.get(0).getUserId());
			taskFinishEntity.setCreateName(userEntity.getUserTrueName());
			taskFinishEntity.setContentType("1");
			zgTaskFinishDao.insert(taskFinishEntity);
		}

	}

	@Override
	public PageUtils selectReportList(Map<String, Object> params) {
		int currPage = 1;
		int limit = 10;

		if(params.get("page") != null){
			currPage = Integer.parseInt((String)params.get("page"));
		}
		if(params.get("limit") != null){
			limit = Integer.parseInt((String)params.get("limit"));
		}
		int page = (currPage-1)*limit;
		List<ZgTaskEntity> listSize= zgTaskDao.selectReportCount(params);
		params.put("page", page);
		params.put("limit", limit);
		Page<ZgTaskEntity> infPage = new Page<>(currPage,limit);
		List<ZgTaskEntity> list= zgTaskDao.selectReportList(params);
		infPage.setTotal(listSize.size());
		infPage.setRecords(list);
		return new PageUtils(infPage);
	}

}