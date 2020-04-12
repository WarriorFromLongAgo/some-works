package com.orhonit.ole.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.sys.dao.PermissionDao;
import com.orhonit.ole.sys.model.Area;
import com.orhonit.ole.sys.model.Dept;
import com.orhonit.ole.sys.model.Permission;
import com.orhonit.ole.sys.model.Person;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.PermissionService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 权限相关接口
 *
 * @author caodw
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "当前登录用户拥有的权限")
    @GetMapping("/current/{sysId}")
    public JSONArray permissionsCurrent(@PathVariable String sysId) {
        List<Permission> list = UserUtil.getCurrentPermissions();
        if (list == null) {
            User user = UserUtil.getCurrentUser();
            list = permissionDao.listByUserId(user.getId(), sysId);
            UserUtil.setPermissionSession(list);
        }
        final List<Permission> permissions = list;

        return reset(permissions, sysId);
    }

    private JSONArray reset(List<Permission> permissions, String sysId) {
        JSONArray array = new JSONArray();

        List<Permission> parents = permissions.stream().filter(p -> p.getParentId().equals(Long.valueOf(sysId)))
                .collect(Collectors.toList());
        parents.forEach(p -> {
            String string = JsonUtil.toJson(p);
            JSONObject parent = (JSONObject) JSONObject.parse(string);
            List<Permission> child = permissions.stream().filter(per -> per.getParentId().equals(p.getId()))
                    .collect(Collectors.toList());
            parent.put("child", child);

            array.add(parent);
        });

        return array;
    }

    /**
     * 菜单列表
     *
     * @param pId
     * @param permissionsAll
     * @param list
     */
    private void setPermissionsList(Long pId, List<Permission> permissionsAll, List<Permission> list) {
        for (Permission per : permissionsAll) {
            if (per.getParentId().equals(pId)) {
                list.add(per);
                if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
                    setPermissionsList(per.getId(), permissionsAll, list);
                }
            }
        }
    }

    @GetMapping
    @ApiOperation(value = "菜单列表")
    @RequiresPermissions("sys:menu:query")
    public List<Permission> permissionsList() {
        List<Permission> permissionsAll = permissionDao.listAll();
		/*List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(Permission per:permissionsAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId",per.getParentId());
			map.put("text", per.getName());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id");
		String  json = JsonUtil.toJson(retMap);*/
        List<Permission> list = Lists.newArrayList();
        setPermissionsList(0L, permissionsAll, list);

        return list;
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有菜单")
    @RequiresPermissions("sys:menu:query")
    public List<Map<String, Object>> permissionsAll() {
        List<Permission> permissionsAll = permissionDao.listAll();

        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> map;
        for (Permission per : permissionsAll) {
            map = new HashMap<>();
            map.put("id", per.getId());
            map.put("parentId", per.getParentId());
            map.put("text", per.getName());
            listMap.add(map);
        }
        List<Map<String, Object>> retMap = AppUtil.list2Tree(listMap, "parentId", "id", null);

        return retMap;
    }

    @GetMapping("/parents")
    @ApiOperation(value = "一级菜单")
    @RequiresPermissions("sys:menu:query")
    public List<Map<String, Object>> parentMenu() {
        List<Permission> parents = permissionDao.listParents1();
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> map;
        for (Permission per : parents) {
            map = new HashMap<>();
            map.put("id", per.getId());
            map.put("parentId", per.getParentId());
            map.put("text", per.getName());
            listMap.add(map);
        }
        List<Map<String, Object>> retMap = AppUtil.list2Tree(listMap, "parentId", "id", null);
        return retMap;
    }

    @GetMapping(params = "roleId")
    @ApiOperation(value = "根据角色id获取权限列表")
    @RequiresPermissions(value = {"sys:menu:query", "sys:role:query"}, logical = Logical.OR)
    public List<Permission> listByRoleId(Long roleId) {
        return permissionDao.listByRoleId(roleId);
    }

    @LogAnnotation
    @PostMapping
    @ApiOperation(value = "保存菜单")
    @RequiresPermissions("sys:menu:add")
    public void save(@RequestBody Permission permission) { 
    	permissionDao.save(permission);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据菜单id获取菜单")
    @RequiresPermissions("sys:menu:query")
    public Permission get(@PathVariable Long id) {
        return permissionDao.getById(id);
    }

    @LogAnnotation
    @PutMapping
    @ApiOperation(value = "修改菜单")
    @RequiresPermissions("sys:menu:add")
    public void update(@RequestBody Permission permission) {
        permissionDao.update(permission);
    }

    /**
     * 校验权限
     *
     * @return
     */
    @GetMapping("/owns")
    @ApiOperation(value = "校验当前用户的权限")
    public Set<String> ownsPermission() {
        List<Permission> permissions = UserUtil.getCurrentPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptySet();
        }

        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(Permission::getPermission).collect(Collectors.toSet());
    }

    @LogAnnotation
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除菜单")
    @RequiresPermissions(value = {"sys:menu:del"})
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }


    @GetMapping("/area")
    @ApiOperation(value = "区划")
    @RequiresPermissions("sys:menu:query")
    public List<Map<String, Object>> areaAll() {
        List<Area> areaAll = permissionDao.areaAll();
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> map;
        for (Area per : areaAll) {
            map = new HashMap<>();
            map.put("id", per.getId());
            map.put("parentId", per.getParent_id());
            map.put("text", per.getName());
            listMap.add(map);
        }
        List<Map<String, Object>> retMap = AppUtil.list2Tree(listMap, "parentId", "id", null);

        return retMap;
    }

    @RequestMapping("/dept")
    @ApiOperation(value = "主体")
    @RequiresPermissions("sys:menu:query")
    public List<Map<String, Object>> deptAll(String id) {
        List<Dept> deptAll = permissionDao.deptAll(id);
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> map;
        for (Dept per : deptAll) {
            map = new HashMap<>();
            map.put("id", per.getId());
            map.put("parent_id", per.getParent_id());
            map.put("text", per.getName());
            map.put("refId", per.getArea_id());
            listMap.add(map);
        }
        List<Map<String, Object>> retMap = AppUtil.list2Tree(listMap, "parent_id", "id", id);

        return retMap;
    }

    @GetMapping("/persons")
    @ApiOperation(value = "主体人员列表")
    @RequiresPermissions("sys:menu:query")
    public List<Person> personList(String dept_id) {
        List<Person> personAll = permissionDao.personAll(dept_id);
        return personAll;
    }

    @GetMapping("/person")
    @ApiOperation(value = "人员信息")
    @RequiresPermissions("sys:menu:query")
    public Person person(String id) {
        Person person = permissionDao.person(id);
        return person;
    }

}
