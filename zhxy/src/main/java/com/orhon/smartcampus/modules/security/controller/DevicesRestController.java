package com.orhon.smartcampus.modules.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.security.entity.Devices;
import com.orhon.smartcampus.modules.security.service.IDevicesService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/security", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DevicesRestController extends ApiController {

    @Autowired
    private IDevicesService devicesService;

    /**
     * 绑定设备
     *
     * @param maps
     * @return string
     */
    @PostMapping("/devices")
    public R add(@RequestBody HashMap<String, Object> maps) {
        String save = devicesService.insertDevices(maps);
        return R.ok().put("msg", save);
    }

    /**
     * 删除绑定设备
     */

    @DeleteMapping("/devices")
    public R delete(@RequestBody HashMap<String, Object> maps) {
        devicesService.removeById(maps);
        return R.ok();
    }

    /**
     * 修改绑定设备
     */

    @PutMapping("/devices")
    public R update(@RequestBody HashMap<String, Object> maps) {
        String update = devicesService.updateDevices(maps);
        return R.ok().put("msg", update);
    }

    /**
     * 查看绑定设备
     */


    @GetMapping("/devices")
    public R index(@RequestParam(value = "paging", required = false, defaultValue = "false") Boolean paging, Devices devices, PageDto dto) {
        IPage<Devices> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Devices> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(devices);
        if (paging) {
            IPage<Devices> pagelist = devicesService.page(page, queryWrapper);
            return R.ok().put("data", pagelist);
        } else {
            List<Devices> pagelists = devicesService.list();
            return R.ok().put("data", pagelists);
        }
    }

    /**
     * 查询单条绑定设备
     */

    @GetMapping("/devices/{id}")
    public R single(@PathVariable("id") Long id) {
        Devices devices = devicesService.getById(id);
        return R.ok().put("data", devices);
    }

}
