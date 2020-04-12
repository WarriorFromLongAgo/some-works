package com.orhon.smartcampus.modules.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import com.orhon.smartcampus.utils.IPInfoUtil;
import com.orhon.smartcampus.utils.IPUtil;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bao
 */

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersRestController extends ApiController {

	@Autowired
	private IUsersService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Users
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Users users,PageDto dto) {
		IPage<Users> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(users);
	    IPage<Users> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
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
		Users byId = service.getById(id);
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
		Users users = JSONObject.parseObject(JSONObject.toJSONString(maps), Users.class);
	    boolean save = service.save(users);
	    if (save) {
			return R.ok().put("data", users);
		}
	    return R.error().put("msg" , service.getValidationData().toString());
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
		Users users = JSONObject.parseObject(JSONObject.toJSONString(maps), Users.class);
		service.updateById(users);
	    return R.ok();
	}
	
	@GetMapping(value = "/weather")
    public Object tianqi(HttpServletRequest request) throws Exception {
		String ipAddress = IPUtil.getIpAddress(request);
    	int indexOf2 = ipAddress.indexOf(",");
    	String ip = ipAddress.substring(0, indexOf2);
    	String weather = IPInfoUtil.getIpInfo(ip);
    	Object weathers = JSON.parse(weather);
    	return weathers;
	}
	
	
}
