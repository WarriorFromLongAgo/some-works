package com.orhonit.modules.sys.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.entity.StudyAssessEntity;
import com.orhonit.modules.sys.entity.StudyClassifyEntity;
import com.orhonit.modules.sys.entity.StudyGradeEntity;
import com.orhonit.modules.sys.entity.StudyUserEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.StudyAssessService;
import com.orhonit.modules.sys.service.StudyClassifyService;
import com.orhonit.modules.sys.service.StudyGradeService;
import com.orhonit.modules.sys.service.StudyUserService;
import com.orhonit.modules.sys.service.SysUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/study")
@Slf4j
public class StudyController {

	@Autowired
	private StudyUserService userService;

	@Autowired
	private StudyGradeService gradeService;

	@Autowired
	private StudyAssessService assessService;

	@Autowired
	private StudyClassifyService classifyService;

	@Autowired
	private SysUserService sysUserService;

	/**
	 *  保存用户和分数
	 *  @author bao
	 *  @data Aug 8, 2019 2:32:11 PM
	 */
	@PostMapping(value="/saveGrade")
	public R saveUserandGrade(StudyGradeEntity gradeEntity) {
		boolean insert = gradeService.insert(gradeEntity);
		if(insert) {
			R r = updateUser(gradeEntity);
			return r;
		}
		return R.error("保存失败");
	}

	/**
	 *  新增分数 更新用户总分和结果方法
	 *  @author bao
	 *  @data Aug 9, 2019 10:08:33 AM
	 */
	@PostMapping(value="/updateUser")
	public R updateUser(StudyGradeEntity gradeEntity) {
		R r = gradeService.updateUser(gradeEntity);	
		return r;
	}

	/**
	 *  查询sys_User用户数量
	 *  @author bao
	 *  @data Aug 8, 2019 2:32:11 PM
	 */
	@GetMapping(value="/statistics/user/count")
	public R userCount() {
		EntityWrapper<SysUserEntity> wrapper = new EntityWrapper<SysUserEntity>();
		int count = sysUserService.selectCount(wrapper);
		return R.ok().put("data", count);
	}

	/**
	 *  查询study_User用户数量
	 *  @author bao
	 *  @data Aug 8, 2019 2:32:11 PM
	 */
	@GetMapping(value="/statistics/myuser/count")
	public R myuserCount(StudyUserEntity userEntity) {
		R r=gradeService.myuserCount(userEntity);
		return r;
	}

	/**
	 *  查询study_User用户和sys_user用户数量 然后计算百分比
	 *  @author bao
	 *  @data Aug 8, 2019 2:32:11 PM
	 */
	@GetMapping(value="/statistics/liveness")
	public R liveness(StudyUserEntity userEntity) {	
		R r = gradeService.liveness(userEntity);
		return r;
	}

	/**
	 *  查询study_User用户和sys_user用户数量 完成比例
	 *  @author bao
	 *  @data Aug 8, 2019 2:32:11 PM
	 */
	@GetMapping(value="/statistics/Completion")
	public R Completion(StudyUserEntity userEntity) {
		R r = gradeService.Completion(userEntity);
		return r;
	}


	/**
	 *  top10用户信息
	 *  @author bao
	 *  @data Aug 8, 2019 2:32:11 PM
	 */
	@GetMapping(value="/statistics/Topten")
	public R topten() {
		List<StudyUserEntity>  top10 = gradeService.topten();
		return R.ok().put("data", top10);
	}

	/**
	 *  某个人的线上线下完成率
	 *  @author bao
	 *  @data Aug 12, 2019 4:46:18 PM
	 */
	@GetMapping(value="/statistics/user/complete")
	public R complete(StudyGradeEntity gradeEntity) {
		R r = gradeService.complete(gradeEntity);
		return r;


	}

	/**
	 * study_user列表
	 * @param dictionary
	 * @return
	 */
	@GetMapping("/getUser/list")
	@ResponseBody
	public R getUserList(StudyUserEntity userEntity) {
		R r = gradeService.getUserList(userEntity);
		return r;
	}






	/**
	 *  保存用户信息返回id
	 *  @author bao
	 *  @data Aug 8, 2019 2:32:11 PM
	 */
	@PostMapping(value="/saveUser")
	public R saveUser(@RequestBody (required=false) StudyUserEntity userEntity) {
		boolean insert = userService.insert(userEntity);
		if(insert) {
			return R.ok().put("data", userEntity);
		}
		return R.error("保存失败");
	}

	@PostMapping(value="/saveAssess")
	public R saveAssess(@RequestBody (required=false) StudyAssessEntity assessEntity) {
		boolean insert = assessService.insert(assessEntity);
		if(insert) {
			return R.ok().put("data", assessEntity);
		}
		return R.error("保存失败");
	}

	@PostMapping(value="/saveClassify")
	public R saveClassify(@RequestBody (required=false) StudyClassifyEntity classifyEntity) {
		boolean insert = classifyService.insert(classifyEntity);
		if(insert) {
			return R.ok().put("data", classifyEntity);
		}
		return R.error("保存失败");
	}

	@DeleteMapping("/delClassify/{id}")
	public R delClassify(@PathVariable("id") Long id) {
		boolean byId = classifyService.deleteById(id);
		if(byId) {
			return R.ok();
		}
		return R.error("删除失败");
	}

	@DeleteMapping("/delAssess/{id}")
	public R delAssess(@PathVariable("id") Long id) {
		boolean byId = assessService.deleteById(id);
		if(byId) {
			return R.ok();
		}
		return R.error("删除失败");
	}

	@DeleteMapping("/delGrade/{id}")
	public R delGrade(@PathVariable("id") Long id) {
		boolean byId = gradeService.deleteById(id);
		if(byId) {
			return R.ok();
		}
		return R.error("删除失败");
	}

	@DeleteMapping("/delUser/{id}")
	public R delUser(@PathVariable("id") Long id) {
		boolean byId = userService.deleteById(id);
		if(byId) {
			return R.ok();
		}
		return R.error("删除失败");
	}

	@PutMapping("/update/user")
	public R updateUser(@RequestBody (required=false) StudyUserEntity userEntity) {
		Wrapper<StudyUserEntity> wrapper = null;
		boolean update = userService.update(userEntity, wrapper);
		if(update) {
			return R.ok().put("data", userEntity);
		}
		return R.error("修改失败");
	}

	@PutMapping("/update/grade")
	public R updateGrade(@RequestBody (required=false) StudyGradeEntity gradeEntity) {
		Wrapper<StudyGradeEntity> wrapper = null;
		boolean update = gradeService.update(gradeEntity, wrapper);
		if(update) {
			return R.ok().put("data", gradeEntity);
		}
		return R.error("修改失败");
	}

	@PutMapping("/update/Assess")
	public R updateAssess(@RequestBody (required=false) StudyAssessEntity assessEntity) {
		Wrapper<StudyAssessEntity> wrapper = null;
		boolean update = assessService.update(assessEntity, wrapper);
		if(update) {
			return R.ok().put("data", assessEntity);
		}
		return R.error("修改失败");
	}

	@PutMapping("/update/classify")
	public R updateClassify(@RequestBody (required=false) StudyClassifyEntity classifyEntity) {
		Wrapper<StudyClassifyEntity> wrapper = null;
		boolean update = classifyService.update(classifyEntity, wrapper);
		if(update) {
			return R.ok().put("data", classifyEntity);
		}
		return R.error("修改失败");
	}

	/**
	 * 列表
	 * @param dictionary
	 * @return
	 */
	@GetMapping("/getAssess/list")
	@ResponseBody
	public R getAssessList(StudyAssessEntity assessEntity) {
		Wrapper<StudyAssessEntity> wrapper = null;
		List<StudyAssessEntity> list = assessService.selectList(wrapper);
		return R.ok().put("data", list);
	}
	
	/**
	 * 列表
	 * @param dictionary
	 * @return
	 */
	@GetMapping("/getClassify/list")
	@ResponseBody
	public R getClassifyList(StudyClassifyEntity classifyEntity) {
		Wrapper<StudyClassifyEntity> wrapper = null;
		List<StudyClassifyEntity> list = classifyService.selectList(wrapper);
		return R.ok().put("data", list);
	}





	/**
	 * 导入
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	@PostMapping(value = "/toLead")
	public R xlsx(HttpServletRequest request, Model model,@RequestParam(required = false) MultipartFile file) throws IOException, ParseException {
		if(file.isEmpty()!=true){
			InputStream stream = file.getInputStream();
			try {
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
				// 循环工作表Sheet         
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
					if (hssfSheet == null) {
						continue;
					}
					// 循环行Row
					for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
						HSSFRow hssfRow1 = hssfSheet.getRow(rowNum);
						StudyUserEntity userEntity = new StudyUserEntity();
						StudyGradeEntity gradeEntity = new StudyGradeEntity();

						if (hssfRow1 != null) {
							if(hssfRow1.getCell(0)==null) {
								continue;
							}

							String phone = getValue(hssfRow1.getCell(1));
							if(sysUserService.queryByMobile(phone)==null) {
								continue;
							}
							userEntity.setUserid(sysUserService.queryByMobile(phone).getUserId().toString());
							userEntity.setPhone(phone);
							String username = getValue(hssfRow1.getCell(0));
							userEntity.setUsername(username);

							String assess = getValue(hssfRow1.getCell(2));
							EntityWrapper<StudyAssessEntity> wrapper1 = new EntityWrapper<StudyAssessEntity>();
							wrapper1.eq("name",assess );
							StudyAssessEntity assessEntity = assessService.selectOne(wrapper1);
							userEntity.setAssessid(assessEntity.getId().toString());

							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
							String data = format.format(hssfRow1.getCell(3).getDateCellValue());
							userEntity.setData(data.substring(0, 4));

							String classify = getValue(hssfRow1.getCell(4));
							EntityWrapper<StudyClassifyEntity> wrapper = new EntityWrapper<StudyClassifyEntity>();
							wrapper.eq("classname",classify );
							StudyClassifyEntity selectOne = classifyService.selectOne(wrapper);

							String grade = getValue(hssfRow1.getCell(5));
							String xgrade = getValue(hssfRow1.getCell(6));

							gradeEntity.setClassId(selectOne.getId().toString());
							gradeEntity.setClassifyId(selectOne.getClassify());
							gradeEntity.setData(data);
							gradeEntity.setUserId(sysUserService.queryByMobile(phone).getUserId());
							gradeEntity.setGrade(grade);
							gradeEntity.setXgrade(xgrade);
						}

						EntityWrapper<StudyUserEntity> wrapper2 = new EntityWrapper<StudyUserEntity>();
						wrapper2.eq("userid",userEntity.getUserid() );
						wrapper2.eq("data", userEntity.getData());
						StudyUserEntity selectOne = userService.selectOne(wrapper2);
						if(selectOne==null) {
							boolean insert = userService.insert(userEntity);
							if(insert) {
								boolean insert2 = gradeService.insert(gradeEntity);
								if(!insert2) {
									userService.deleteById(userEntity.getId());
								}else {
									updateUser(gradeEntity);
								}
							}
						}else {
							boolean insert2 = gradeService.insert(gradeEntity);
							if(insert2) {
								updateUser(gradeEntity);
							}
						}
					}
				}        


			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return R.ok().put("data", "导入成功！");
		}
		return R.ok().put("data", "导入失败！");
	}

	public String getValue(HSSFCell hssfcell){
		String str;
		DecimalFormat df = new DecimalFormat("0");  
		if(hssfcell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
			str = String.valueOf(hssfcell.getBooleanCellValue());
			return str;
		} else if(hssfcell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			str = df.format(hssfcell.getNumericCellValue());  
			return str;
		} else{
			str = hssfcell.getStringCellValue();  
			return str;
		}
	}




}
