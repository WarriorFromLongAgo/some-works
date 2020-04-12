package com.orhon.smartcampus.modules.systemctl.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.systemctl.entity.Menus;
import com.orhon.smartcampus.modules.systemctl.entity.Widgets;
import com.orhon.smartcampus.modules.systemctl.service.IWidgetsService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.orhon.smartcampus.framework.controller.ApiController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/systemctl/widgets", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WidgetsRestController extends ApiController {
    @Autowired
    private IWidgetsService service;

    @GetMapping("/get/list")
    @ResponseBody
    public R getList(@RequestBody Widgets widgets, PageDto dto) {
        // TODO: 11/7/2019 实现过滤条件，可选字段
        //    private String title;  //"组件标题"
        //    private String apis;  //"所用API"
        //    private String type;  //"组件类型(modulecard,pie,statics等)"
        //    private Integer menuId;  //"所属菜单id"
        //    private Integer status;  //启用状态
        //    private Integer moduleId;  //"所属模块id"
        //    private String clients;  //"支持客户端[pc_cn/pc_mn/app_cn/app_mn]"

        IPage<Widgets> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Widgets> queryWrapper = new QueryWrapper<>();
       
        JSONObject parseObject = JSONObject.parseObject(widgets.getTitle().toString());
        String string = "";
        for (Object map : parseObject.entrySet()){
        	if(!((Map.Entry)map).getKey().equals("")&&!((Map.Entry)map).getValue().toString().equals("")) {
        		string += "and title -> $."+((Map.Entry)map).getKey()+" LIKE '%"+((Map.Entry)map).getValue()+"%'  ";
        	}
        }
        List<Widgets> list = service.getList();
        System.out.println(string);
		queryWrapper.setEntity(widgets);
        IPage<Widgets> pagelist = service.page(page, queryWrapper);
        return R.ok().put("data", list);
    }
    
    /**
     * 
     * String转map
     * @param str
     * @return
     */
    public static Map<String,String> getStringToMap(String str){
        //根据逗号截取字符串数组
        String[] str1 = str.split(",");
        //创建Map对象
        Map<String,String> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0],str2[1]);
        }
        return map;
    }
 


    @GetMapping("/get/list2")
    @ResponseBody
    public R getList2(@RequestBody HashMap<String, Object> maps) {

        HashMap<String, Object> params = JSONObject.parseObject(JSONObject.toJSONString(maps), HashMap.class);
        //Integer module_id = (Integer) params.get("moduleID");

        QueryWrapper<Widgets> queryWrapper = new QueryWrapper<>();

        Iterator iterator = params.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();

            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.equals("title")) queryWrapper.eq("title",value);
            if (key.equals("apis")) queryWrapper.like("apis",value);
            if (key.equals("type")) queryWrapper.eq("type",value);
            if (key.equals("menuId")) queryWrapper.eq("menu_id",value);
            //

        }
        

        return R.ok().put("data", service.list(queryWrapper));
    }
    
    @RequestMapping(value = "/get/{id}",method= RequestMethod.GET)
    public R getById(@PathVariable("id") Long id) {
        Widgets byId = service.getById(id);
        return R.ok().put("data", byId);
    }
    
    @GetMapping(value="/get/Ids")
	@ResponseBody
	public R getIds(Widgets menus,@RequestParam("ids") String ids) {
		QueryWrapper<Widgets> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(menus);
		
		List<Integer> id = new ArrayList<Integer>();
		String[] split = ids.split(",");
		for (String string : split) {
			id.add(Integer.parseInt(string));
		}
		queryWrapper.in("id",id);
		List<Widgets> list = service.list(queryWrapper);
		return R.ok().put("data", list);
	}


    @DeleteMapping("/del/{id}")
    public R delById(@PathVariable("id") Long id) {
        boolean byId = service.removeById(id);
        if(byId) {
        	 return R.ok().put("msg", "删除成功");
        }
        return R.error();
    }


    @PostMapping(value="/save")
    @ResponseBody
    public R save(@RequestBody HashMap<String, Object> maps) {
        Widgets widgets = JSONObject.parseObject(JSONObject.toJSONString(maps), Widgets.class);
        boolean save = service.save(widgets);
        if (save) {
            return R.ok().put("data", widgets);
        }
        return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
    }


    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody HashMap<String, Object> maps) {
        Widgets widgets = JSONObject.parseObject(JSONObject.toJSONString(maps), Widgets.class);
        service.updateById(widgets);
        return R.ok().put("data", widgets);
    }

    // TODO: 11/7/2019 实现无分页列表，可传筛选条件
    @GetMapping("/get/nopaginglist")
    @ResponseBody
    public R getNopaginglist(Widgets widgets) {
		List<Widgets>list=service.WidgetsList(widgets);
		return R.ok().put("data", list);
    }
}
