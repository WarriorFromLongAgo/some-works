package com.orhonit.modules.generator.controller;

import java.util.Date;
import java.util.Map;

import com.orhonit.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.dao.UserPhoneNumCardDao;
import com.orhonit.modules.generator.entity.NewsEntity;
import com.orhonit.modules.generator.service.NewsService;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-11 09:10:07
 */
@RestController
@RequestMapping("generator/news")
public class NewsController extends AbstractController{
    @Autowired
    private NewsService newsService;
    
//    @Autowired
//    private UserPhoneNumCardDao userPhoneNumCardDao;    
    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("sys:news:list")
    public R list(@RequestParam Map<String, Object> params){
    	String newModel = (String) params.get("newModel");
    	if(StringUtils.isNotBlank(newModel)) {
    		 PageUtils page = newsService.queryPage(params);
    	        return R.ok().put("page", page);
    	}
       return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{newId}")
    //@RequiresPermissions("sys:news:info")
    public R info(@PathVariable("newId") Integer newId){
			NewsEntity news = newsService.selectById(newId);

        return R.ok().put("news", news);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("sys:news:save")
    public R save(@RequestBody NewsEntity news){
    	//System.out.println(new Date());、
    if(news.getUserId()!=null) {
    	news.setNewCreateTime(new Date());
        SysUserEntity user = getUser();
        news.setLowerId(user.getLowerId());
        news.setLowerName(user.getLowerName());
        newsService.insert(news);
		//如果新闻二级模块为记忆与思念(model_id=25)发送推送信息
//		if(news.getNewModel()!=null&&news.getNewModel()==25) {
//			AppTuiSongVo appTuiSongVo = new AppTuiSongVo();
//			appTuiSongVo.setTypeCode(1);
//			String newTitle=news.getNewTitle();
//			JiguangPushUtil.jiguangPush(userPhoneNumCardDao.selectByDeptId(getUser().getUserDept()),newTitle,appTuiSongVo);
//			return R.ok();
//		}
		return R.ok();
    }
        return R.parameterIsNul();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
   //@RequiresPermissions("sys:news:update")
    public R update(@RequestBody NewsEntity news){
			newsService.updateById(news);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("sys:news:delete")
    public R delete(@RequestParam Integer newId){
			newsService.deleteByNewId(newId);

        return R.ok();
    }
    
    /**
      * 新闻置顶
     */
    @GetMapping("/toTop/{newTopNew}/{newId}")
    //@RequiresPermissions("sys:news:delete")
    public R NewtoTop(@PathVariable Integer newTopNew,@PathVariable Integer newId){
    	if(newTopNew!=null&&newId!=null) {
    		if(newTopNew==0) {
    			newTopNew=1;
    			newsService.NewtoTop(newTopNew,newId);
    		}else if(newTopNew==1) {
    			newTopNew=0;
    			newsService.NewtoTop(newTopNew,newId);
    		}
    	}
        return R.ok();
    }

}
