package com.orhonit.modules.generator.controller;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.JiguangPushUtil;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.app.service.AppMeetingService;
import com.orhonit.modules.app.vo.AppTuiSongVo;
import com.orhonit.modules.generator.entity.MeetingEntity;
import com.orhonit.modules.generator.service.MeetFileService;
import com.orhonit.modules.generator.service.MeetPeopleService;
import com.orhonit.modules.generator.service.MeetingService;
import com.orhonit.modules.generator.vo.MeetPeopleVo;
import com.orhonit.modules.sys.dao.UserPhoneNumCardDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支部活动表
 *
 * @author zjw
 * @email chobitsyjwz@163.com
 * @date 2019-02-14 14:56:22
 */
@RestController
@RequestMapping("generator/meeting")
public class MeetingController extends AbstractController{
    @Autowired
    private MeetingService meetingService;

	@Autowired
	private MeetPeopleService meetPeopleService;

	@Autowired
	private MeetFileService meetFileService;
    
    @Autowired
    private AppMeetingService appMeetingService;

    @Autowired
    private UserPhoneNumCardDao userPhoneNumCardDao; 
    
    @Autowired	
    private UploadConfig uploadConfig;
    /**
     * 列表
     */
    @RequestMapping(value = "/list" ,method = RequestMethod.GET)
    //@RequiresPermissions("sys:meeting:list")
    public R list(@RequestParam Map<String, Object> params){
    	if(getUserId()!=null){
	        PageUtils page = meetingService.queryPage(params,getUserId());
	
	        return R.ok().put("page", page);
    	}
    	return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{meetId}")
    //@RequiresPermissions("sys:meeting:info")
    public R info(@PathVariable("meetId") String meetId){
        MeetingEntity meeting = meetingService.getById(meetId);

        return R.ok().put("meeting", meeting);
    }
    
    /**
     * 添加会议
     */
    @RequestMapping(value = "insertMeet",method = RequestMethod.POST)
    //@RequiresPermissions("sys:meeting:save")
    public R insertMeet(@RequestBody MeetingEntity meeting){
		String id = meeting.getMeetId();
    	SysUserEntity userEntity = getUser();
    	//支部Id是否为空
    	if(userEntity.getUserDept()!=null&&userEntity.getUserId()!=null) {
    		meeting.setUserId(userEntity.getUserId());
    		meeting.setDeptId(userEntity.getUserDept());
    		meeting.setMeetId(id);
    		//meeting.setOrgId(userEntity.getUserDept());
    		//添加会议
			meetingService.insertMeet(meeting);

			//发送推送信息
			AppTuiSongVo appTuiSongVo = new AppTuiSongVo();
			appTuiSongVo.setTypeCode(2);
			appTuiSongVo.setMeetId(id);
			String newTitle=meeting.getMeetTitle()+"将于【"+meeting.getMeetBeginTime()+"至"+meeting.getMeetEndTime()+"】举行。点击查看详情：";
			JiguangPushUtil.jiguangPush(userPhoneNumCardDao.selectByDeptId(userEntity.getUserDept()),newTitle,appTuiSongVo);
			return R.ok();
    	}
    	return R.parameterIsNul();
    }

    /**
     * 保存
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    //@RequiresPermissions("sys:meeting:save")
    public R save(@RequestBody MeetingEntity meeting){
//    	String id = UUIDUtils.generateUuid();
		String id = meeting.getMeetId();
    	SysUserEntity userEntity = getUser();
    	//支部Id是否为空
    	if(userEntity.getUserDept()!=null&&userEntity.getUserId()!=null) {
    		meeting.setUserId(userEntity.getUserId());
    		meeting.setDeptId(userEntity.getUserDept());
    		meeting.setMeetId(id);
    		//meeting.setOrgId(userEntity.getUserDept());
    		//添加会议
			meetingService.insertMeet(meeting);
			//添加参会人员，报错则回滚
			try {
	    		meetingService.setMeetUsers(id,userEntity.getUserDept());
			} catch (Exception e) {
				// TODO: handle exception
				//添加出错删除会议记录
				meetingService.deleteById(id);
				return R.error();
			}
			//发送推送信息
			AppTuiSongVo appTuiSongVo = new AppTuiSongVo();
			appTuiSongVo.setTypeCode(2);
			//appTuiSongVo.setMeetId(id);
			String newTitle=meeting.getMeetTitle()+"将于【"+meeting.getMeetBeginTime()+"至"+meeting.getMeetEndTime()+"】举行。点击查看详情：";
			JiguangPushUtil.jiguangPush(userPhoneNumCardDao.selectByDeptId(userEntity.getUserDept()),newTitle,appTuiSongVo);
	        return R.ok();
    	}
    	return R.parameterIsNul();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("sys:meeting:update")
    public R update(@RequestBody MeetingEntity meeting){
			meetingService.updateById(meeting);
			String meetId = meeting.getMeetId() + "";
        return R.ok(meetId);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{meetId}")
   //@RequiresPermissions("sys:meeting:delete")
    public R delete(@PathVariable("meetId") String meetId){
		meetingService.deleteBymeetId(meetId);
        return R.ok();
    }
    
   /**
    * 查询人员接送站点和签到情况
    */
   @RequestMapping("/getIsSigninAndStation")
  //@RequiresPermissions("sys:meeting:delete")
   public R getIsSigninAndStation(@RequestParam String meetId){
	   List<MeetPeopleVo> meetPeopleVo = meetingService.getIsSigninAndStation(meetId);

       return R.ok().put("meetPeopleVo", meetPeopleVo);
   }
   
	/**
	 * 修改签到状态为未签到
	 * @param peopleId
	 * @return
	 */
   @PutMapping("/updSignin")
   //@RequiresPermissions("sys:meeting:list")
   public R updSignin(@RequestParam Integer peopleId){
	      appMeetingService.updSignin(peopleId);
   	return R.ok();
   }
   
   /**
	 * 上传文件
	 */
	@PostMapping("/upload")
	//@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileLocalName = System.currentTimeMillis() + "";
		Date date = new Date();
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dformat.format(date);
		//String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
		String filePath = uploadConfig.getPath() + dateStr + "//";
		try {
			// 创建目录
			File dir = new File(filePath);
			
			dir.mkdirs();

			// 获取文件输入流	
			InputStream in = file.getInputStream();

			// 创建文件并保存
			File f = new File(filePath + fileLocalName + suffix);
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);				
			}
			fos.close();
			in.close();
//			oele.save(omf);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.error();
		}
		//保存文件信息
//		MeetingEntity meeting = new MeetingEntity();
//		meeting.setImage(filePath);
//		meeting.setCreateTime(new Date());
//		meetingService.insert(meeting);

		return R.ok().put("url","http://39.104.121.137:8080/ftp/" + dateStr + "/" + fileLocalName + suffix);
	}

}
