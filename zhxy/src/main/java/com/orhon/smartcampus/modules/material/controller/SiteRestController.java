package com.orhon.smartcampus.modules.material.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.material.entity.Campus;
import com.orhon.smartcampus.modules.material.entity.Site;
import com.orhon.smartcampus.modules.material.service.ICampusService;
import com.orhon.smartcampus.modules.material.service.ISiteService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.HashMap;

/**
 * <p>
 * 场地 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/material/site", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SiteRestController extends ApiController {
    @Autowired
    private ISiteService service;

    /**
     * 条件加分页查询集合
     * @param site
     * @param dto
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(Site site, PageDto dto) {
        IPage<Site> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<Site> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(site);
        IPage<Site> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", pagelist);
    }

    /**
     * id查询一条数据
     * @param site
     * @param dto
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        Site byId = service.getById(id);
        return R.ok().put("data", byId);
    }

    /**
     * id删除数据
     * @param Holidays
     * @param dto
     * @return
     */
    @DeleteMapping(value="/delById/{id}")
    @ResponseBody
    public R delById(@PathVariable("id") Integer id) {
        service.removeById(id);
        return R.ok().put("msg", "删除成功");
    }

    /**
     * 新增一条记录
     * @param site
     * @param dto
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Site site = JSONObject.parseObject(JSONObject.toJSONString(maps), Site.class);
        boolean save = service.save(site);
        if (save) {
            return R.ok().put("data", site);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }



    /**
     * id修改一条记录
     * @param site
     * @param dto
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Site site = JSONObject.parseObject(JSONObject.toJSONString(maps), Site.class);
        service.updateById(site);
        return R.ok().put("data", site);
    }
}
