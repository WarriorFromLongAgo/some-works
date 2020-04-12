package com.orhonit.modules.sys.service.impl;


import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.dao.StudyGradeDao;
import com.orhonit.modules.sys.entity.StudyAssessEntity;
import com.orhonit.modules.sys.entity.StudyGradeEntity;
import com.orhonit.modules.sys.entity.StudyUserEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.StudyAssessService;
import com.orhonit.modules.sys.service.StudyGradeService;
import com.orhonit.modules.sys.service.StudyUserService;
import com.orhonit.modules.sys.service.SysUserService;


@Service
public class StudyGradeServiceImpl extends ServiceImpl<StudyGradeDao, StudyGradeEntity> implements StudyGradeService {

	@Autowired
	private StudyGradeDao gradeDao;

	@Autowired
	private StudyGradeService gradeService;

	@Autowired
	private StudyUserService userService;

	@Autowired
	private StudyAssessService assessService;
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	public HashMap<String,Object> getSumGrade(StudyGradeEntity gradeEntity) {
		// TODO Auto-generated method stub
		return gradeDao.getSumGrade(gradeEntity);
	}

	@Override
	public HashMap<String,Object> getCountGrade(StudyGradeEntity gradeEntity) {
		// TODO Auto-generated method stub
		return gradeDao.getCountGrade(gradeEntity);
	}

	@Override
	public List<StudyUserEntity> topten() {
		// TODO Auto-generated method stub
		return gradeDao.topten();
	}

	@Override
	public List<StudyGradeEntity> getGradeList(StudyGradeEntity gradeEntity) {
		// TODO Auto-generated method stub
		return gradeDao.getGradeList(gradeEntity);
	}

	@Override
	public R complete(StudyGradeEntity gradeEntity) {
		String data = gradeEntity.getData();
		String newdata = data.substring(0, 4);
		gradeEntity.setData(newdata);



		List<StudyGradeEntity> list =  gradeService.getGradeList(gradeEntity);
		EntityWrapper<StudyUserEntity> wrapper = new EntityWrapper<StudyUserEntity>();
		wrapper.eq("userid", gradeEntity.getUserId());
		StudyUserEntity userEntity = userService.selectOne(wrapper);
		HashMap<String,Object> map = new HashMap<String, Object>();
		if(userEntity!=null) {
			StudyAssessEntity assessEntity = assessService.selectById(userEntity.getAssessid());
			double upassess = Double.parseDouble(assessEntity.getUpassess());//需要学习的线上分数
			double nextassess = Double.parseDouble(assessEntity.getNextassess());//需要学习的线下分数

			HashMap<String,Object> gradesum = gradeService.getSumGrade(gradeEntity);
			double up = 0;
			double enxt = 0;
			if(gradesum!=null) {
				up = (double)gradesum.get("xianshang");//已学习线上总分
				enxt = (double)gradesum.get("xianxia");//已学习线下总分
			}

			//线上分数
			double a = up/upassess;
			NumberFormat nt = NumberFormat.getPercentInstance();
			//设置百分数精确度2即保留两位小数
			nt.setMinimumFractionDigits(2);
			a = Math.abs(a);  //如果是负数,怎么转出正数 例如:-0.999 转成 0.999
			String xs = nt.format(a);

			//线下分数
			double b = enxt/nextassess;
			//设置百分数精确度2即保留两位小数
			nt.setMinimumFractionDigits(2);
			b = Math.abs(b);  //如果是负数,怎么转出正数 例如:-0.999 转成 0.999
			String xx = nt.format(b);

			map.put("xianshang", xs);
			map.put("xianxia", xx);
			map.put("fenshu", list);
			return R.ok().put("data", map);
		}else {
			map.put("xianshang", "0%");
			map.put("xianxia", "0%");
			map.put("fenshu", list);
			return R.ok().put("data", map);
		}

	}

	@Override
	public R updateUser(StudyGradeEntity gradeEntity) {
		String data = gradeEntity.getData();
		String newdata = data.substring(0, 4);
		gradeEntity.setData(newdata);

		EntityWrapper<StudyUserEntity> wrapper1 = new EntityWrapper<StudyUserEntity>();
		wrapper1.eq("userid", gradeEntity.getUserId());
		wrapper1.eq("data", newdata);
		StudyUserEntity userEntity = userService.selectOne(wrapper1);
		StudyAssessEntity assessEntity = assessService.selectById(userEntity.getAssessid());
		double upassess = Double.parseDouble(assessEntity.getUpassess())/12;//每个月需要学习的线上分数
		double nextassess = Double.parseDouble(assessEntity.getNextassess())/12;//每个月需要学习的线下分数

		HashMap<String,Object> gradesum = gradeService.getSumGrade(gradeEntity);
		double up = (double)gradesum.get("xianshang");//已学习线上总分
		double enxt = (double)gradesum.get("xianxia");//已学习线下总分


		HashMap<String,Object> gradecount = gradeService.getCountGrade(gradeEntity);
		int up1 = Integer.parseInt(gradecount.get("xianshang").toString());//已学习线上月数
		int enxt1 = Integer.parseInt(gradecount.get("xianxia").toString());//已学习线下月数


		if(upassess*up1>=up) {
			userEntity.setUpresult("不合格");
		}else {
			userEntity.setUpresult("合格");
		}

		if(nextassess*enxt1>=enxt) {
			userEntity.setNextresult("不合格");
		}else {
			userEntity.setNextresult("合格");
		}

		//最终结果
		if(upassess*up1>=up || nextassess*enxt1>=enxt) {
			userEntity.setEndresult("不合格");
		}else {
			userEntity.setEndresult("合格");
		}
		userEntity.setUpgrade(up+"");
		userEntity.setNextgrade(enxt+"");
		EntityWrapper<StudyUserEntity> wrapper = new EntityWrapper<StudyUserEntity>();
		wrapper.eq("userid", userEntity.getUserid());
		wrapper.eq("data", userEntity.getData());
		userService.update(userEntity, wrapper);
		return R.ok().put("data", "保存和更新用户成功");
	}

	@Override
	public R liveness(StudyUserEntity userEntity) {
		EntityWrapper<StudyUserEntity> wrapper = new EntityWrapper<StudyUserEntity>();
		if(userEntity.getUserid()!=null) {
			wrapper.in("userid", userEntity.getUserid());
			
		}
		if(userEntity.getData()!=null) {
			wrapper.in("data", userEntity.getData());
		}
		int myusercount = userService.selectCount(wrapper);

		EntityWrapper<SysUserEntity> wrappers = new EntityWrapper<SysUserEntity>();
		int sysusercount = sysUserService.selectCount(wrappers);

		NumberFormat numberFormat = NumberFormat.getInstance();   
		// 设置精确到小数点后2位   
		numberFormat.setMaximumFractionDigits(2);   
		String result = numberFormat.format((float)myusercount/(float)sysusercount*100);	

		return R.ok().put("data", result+"%");
	}

	@Override
	public R myuserCount(StudyUserEntity userEntity) {
		EntityWrapper<StudyUserEntity> wrapper = new EntityWrapper<StudyUserEntity>();
		if(userEntity.getUserid()!=null) {
			wrapper.in("userid", userEntity.getUserid());
			
		}
		if(userEntity.getData()!=null) {
			wrapper.in("data", userEntity.getData());
		}
		int count = userService.selectCount(wrapper);
		return R.ok().put("data", count);
	}

	@Override
	public R Completion(StudyUserEntity userEntity) {
		EntityWrapper<StudyUserEntity> wrapper = new EntityWrapper<StudyUserEntity>();
		wrapper.eq("endresult", "合格");
		if(userEntity.getUserid()!=null) {
			wrapper.in("userid", userEntity.getUserid());
			
		}
		if(userEntity.getData()!=null) {
			wrapper.in("data", userEntity.getData());
		}
		int myusercount = userService.selectCount(wrapper);

		EntityWrapper<SysUserEntity> wrappers = new EntityWrapper<SysUserEntity>();
		int sysusercount = sysUserService.selectCount(wrappers);

		NumberFormat numberFormat = NumberFormat.getInstance();   
		// 设置精确到小数点后2位   
		numberFormat.setMaximumFractionDigits(2);   
		String result = numberFormat.format((float)myusercount/(float)sysusercount*100);	

		return R.ok().put("data", result+"%");
	}
	
	@Override
	public R getUserList(StudyUserEntity userEntity) {
		EntityWrapper<StudyUserEntity> wrapper = new EntityWrapper<StudyUserEntity>();
		List<StudyUserEntity> list = null;
		if(userEntity.getUserid()!=null) {
			wrapper.in("userid", userEntity.getUserid());
		}
		if(userEntity.getData()!=null) {
			wrapper.in("data", userEntity.getData());
		}
		
		list = userService.selectList(wrapper);
		return R.ok().put("data", list);
	}




}
