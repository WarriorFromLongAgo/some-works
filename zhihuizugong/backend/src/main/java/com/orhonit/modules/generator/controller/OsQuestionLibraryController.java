package com.orhonit.modules.generator.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.modules.generator.dao.OsQuestionLibraryDao;
import com.orhonit.modules.generator.entity.OsQuestionLibraryEntity;
import com.orhonit.modules.generator.entity.OsQuestionTypeVariableEntity;
import com.orhonit.modules.generator.service.OsQuestionLibraryService;
import com.orhonit.modules.generator.vo.QuestionLibraryVo;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 题库表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 10:58:55
 */
@RestController
@RequestMapping("generator/osquestionlibrary")
public class OsQuestionLibraryController {
    @Autowired
    private OsQuestionLibraryService osQuestionLibraryService;
    @Autowired
    OsQuestionLibraryDao dao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:osquestionlibrary:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = osQuestionLibraryService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    

    /**
     * 信息
     */
    @RequestMapping("/info/{libraryId}")
   // @RequiresPermissions("generator:osquestionlibrary:info")
    public R info(@PathVariable("libraryId") String libraryId){
		OsQuestionLibraryEntity osQuestionLibrary = osQuestionLibraryService.selectById(libraryId);

        return R.ok().put("osQuestionLibrary", osQuestionLibrary);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("generator:osquestionlibrary:save")
    public R save(@RequestBody OsQuestionLibraryEntity osQuestionLibrary){
    	osQuestionLibrary.setLibraryCreateTime(new Date());
    	osQuestionLibrary.setLibraryUpdateTime(new Date());
		osQuestionLibraryService.save(osQuestionLibrary);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("generator:osquestionlibrary:update")
    public R update(@RequestBody OsQuestionLibraryEntity osQuestionLibrary){
    	osQuestionLibrary.setLibraryUpdateTime(new Date());
		osQuestionLibraryService.updateOs(osQuestionLibrary);
        return R.ok();
    }

    /**
         * 删除题库
     */
    @RequestMapping("/delete/{libraryId}")
   // @ResponseBody
   // @RequiresPermissions("generator:osquestionlibrary:delete")
    public R delete( @PathVariable("libraryId")String libraryId){
		osQuestionLibraryService.deleteAllOsQuestions(libraryId);

        return R.ok();
    }
    
    
    /**
         * 查询所有题库题目
     * @param params
     * @return
     */
    @GetMapping("/allSelectTm")
    //@RequiresPermissions("generator:osquestionlibrary:allSelectTm")
    public List<OsQuestionLibraryEntity> allSelectTm() {
    	return dao.allSelect();
    }
    
    
    /**
         * 题库类型值
     * @return
     */
    @GetMapping("/type/variable")
    //@RequiresPermissions("generator:osquestionlibrary:allSelectTm")
    public List<OsQuestionTypeVariableEntity> allSelectTm1() {
    	return dao.variableList();
    }
    
    
    /**
         *  编辑查看题库
     * @return
     */
    @GetMapping("/type/selectTypeList/{libraryId}")
    //@RequiresPermissions("generator:osquestionlibrary:allSelectTm")
    public List<OsQuestionLibraryEntity> selectTypeList(@PathVariable("libraryId") String libraryId) {
    	return dao.SelectTypeList(libraryId);
    }
    
    /**
         * 查询某个题库下的单选、多选、判断、和总题数
     * @param libraryId
     * @return
     */
    @RequestMapping("/getLibraryQuestionCount/{libraryId}")
    public QuestionLibraryVo getLibraryQuestionCount(@PathVariable("libraryId")String libraryId) {
    	return osQuestionLibraryService.getLibraryQuestionCount(libraryId);
    }

}
