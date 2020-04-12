package com.orhon.smartcampus.modules.teacher.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrange;
import com.orhon.smartcampus.modules.teacher.entity.OfficeArrangeUser;
import com.orhon.smartcampus.modules.teacher.service.IOfficeArrangeUserService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/teacher/officeArrangeUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfficeArrangeUserRestController extends ApiController {
    @Autowired
    private IOfficeArrangeUserService service;

    @Autowired
    private InfoService infoService;
    /**
     * 条件加分页查询集合
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getList")
    @ResponseBody
    public R getList(OfficeArrangeUser officeArrangeUser, PageDto dto) {
        IPage<OfficeArrangeUser> page = new Page<>(dto.getPage(),dto.getLimit());
        QueryWrapper<OfficeArrangeUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(officeArrangeUser);
        IPage<OfficeArrangeUser> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", pagelist);
    }
    //办公室下教师信息
    @GetMapping(value="/getRoomTeacher/{id}")
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "teacher_name")
    })
    @ResponseBody
    public R getRoomTeacher(@RequestParam HashMap<String, Object> maps,PageDto dto,@PathVariable("id") Integer id) {
        maps.put("room_id", id);
        List<HashMap<String, Object>> list = service.getOfficeArrangeTeacher(maps,dto);
        return R.ok().put("data", list);
    }
    /**
     * id查询一条数据
     * @param Users
     * @param bao
     * @return
     */
    @GetMapping(value="/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable("id") Integer id) {
        OfficeArrangeUser byId = service.getById(id);
        return R.ok().put("data", byId);
    }

    /**
     * id删除数据
     * @param Users
     * @param bao
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
     * @param Users
     * @param bao
     * @return
     */
    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        return service.inserts(maps);
    }

    /**
     * id修改一条记录
     * @param Users
     * @param bao
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        OfficeArrangeUser officeArrangeUser = JSONObject.parseObject(JSONObject.toJSONString(maps), OfficeArrangeUser.class);
        service.updateById(officeArrangeUser);
        return R.ok().put("data",officeArrangeUser);
    }
}
