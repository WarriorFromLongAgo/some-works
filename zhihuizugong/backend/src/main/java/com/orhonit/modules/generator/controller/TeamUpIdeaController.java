package com.orhonit.modules.generator.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.TeamUpIdeaEntity;
import com.orhonit.modules.generator.service.TeamUpIdeaService;


/**
 * 我为组工出点子  
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/teamupidea")
public class TeamUpIdeaController {
	
	
	@Autowired
	TeamUpIdeaService teamUpIdeaService;
	
	
	/**
	 * 点子列表
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	/**@RequiresPermissions("generator:teamupidea:list")*/
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page=teamUpIdeaService.queryPage(params);
		return R.ok().put("page", page);
	}
	/**
     * 删除
     */
    @DeleteMapping("/delete")
    /**@RequiresPermissions("generator:meetpeople:delete")*/
    public R delete(@RequestBody Integer ideaId){
    	teamUpIdeaService.deleteIdeaById(ideaId);

        return R.ok();
    }
    
    /**
     * 详细
     * @param ideaId
     * @return
     */
    @GetMapping("/info/{ideaId}")
    public R info(@PathVariable("ideaId") Integer ideaId) {
    	TeamUpIdeaEntity entity =teamUpIdeaService.selectById(ideaId);
    	return R.ok().put("TeamUpIdeaEntity", entity);
    }

}
