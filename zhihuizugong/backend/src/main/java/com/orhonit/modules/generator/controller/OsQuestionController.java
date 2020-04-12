package com.orhonit.modules.generator.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.dao.OsQuestionDao;
import com.orhonit.modules.generator.entity.OsQuestionEntity;
import com.orhonit.modules.generator.service.OsQuestionService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 问题表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 09:34:14
 */
@RestController
@RequestMapping("generator/osquestion")
public class OsQuestionController {
    @Autowired
    private OsQuestionService osQuestionService;
    @Autowired
    OsQuestionDao dao;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:osquestion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = osQuestionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{questionNo}")
    @RequiresPermissions("generator:osquestion:info")
    public R info(@PathVariable("questionNo") String questionNo){
		OsQuestionEntity osQuestion = osQuestionService.selectById(questionNo);

        return R.ok().put("osQuestion", osQuestion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:osquestion:save")
    public R save(@RequestBody OsQuestionEntity osQuestion){
    	osQuestion.setQuestionNo(UUID.randomUUID().toString().replaceAll("-",""));
    	osQuestion.setCreateTime(new Date());
    	osQuestion.setUpdateTime(new Date());
    	osQuestion.setIsDel(CommonParameters.isDel.IS_DEL_NO);
		dao.insertOsQuestion(osQuestion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:osquestion:update")
    public R update(@RequestBody OsQuestionEntity osQuestion){
    	osQuestion.setUpdateTime(new Date());
		osQuestionService.updateById(osQuestion);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@ResponseBody
    @RequiresPermissions("generator:osquestion:delete")
    public R delete(@RequestBody String questionNo){
    	System.out.println("questionNo========================"+questionNo);
		osQuestionService.deleteOsQuestion(questionNo);
        return R.ok();
    }
    
    
    /**
	 * 查询题库下的例题
	 * @param LibraryId
	 * @return
	 */
	@GetMapping("/selectByLibraryId")
	@RequiresPermissions("generator:osquestion:selectByLibraryId")
	public List<OsQuestionEntity> selectByLibraryId(@RequestParam("LibraryId")String LibraryId){
		return dao.selectByLibraryId(LibraryId);
	};
    
    

}
