package com.orhonit.modules.generator.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.MeetFileDao;
import com.orhonit.modules.generator.entity.MeetFileEntity;
import com.orhonit.modules.generator.service.MeetFileService;


@Service("meetFileService")
public class MeetFileServiceImpl extends ServiceImpl<MeetFileDao, MeetFileEntity> implements MeetFileService {

	@Autowired
	MeetFileDao meetFileDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String meetId = (String) params.get("meetId");
        Page<MeetFileEntity> page = this.selectPage(
                new Query<MeetFileEntity>(params).getPage(),
                new EntityWrapper<MeetFileEntity>().and("meet_id="+meetId)
        );
        page.setTotal(this.selectCount(new EntityWrapper<MeetFileEntity>().where("meet_id="+meetId)));
        return new PageUtils(page);
    }

	@Override
	public void insertFile(List<MeetFileEntity> meetFile){
		MeetFileEntity Entity = new MeetFileEntity();
		for (int i=0; i<meetFile.size();i++) {
			Date date = new Date();
			int ts = (int) date.getTime();
			Entity.setId(ts);
			Entity = meetFile.get(i);
			/*String s = Entity.getFilePath();
			System.out.println("------------"+s);
			String ss = StringUtils.substringAfterLast(s,".");
			System.out.println("++++++++++++"+ss);
				Integer str = null;
				try {
					str = FileTypeJudge.isFileType(
					        FileTypeJudge.getType(
					                new FileInputStream(
					                        new File(s))));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Entity.setFileSuffix(ss);
				System.out.println("*********"+str);
				Entity.setFileType(str);*/
			
			meetFileDao.insertFile(Entity);
		}
		
		
	}

	@Override
	public int deleteBymeetId(String meetId) {
		// TODO Auto-generated method stub
		return meetFileDao.deleteBymeetId(meetId);
	}

	@Override
	public void updateByMeetFile(MeetFileEntity meetFile) {
		meetFileDao.updateByMeetFile(meetFile);
		
	}

	@Override
	public List<MeetFileEntity> getById(String meetId) {
		return meetFileDao.getById(meetId);
	}
}