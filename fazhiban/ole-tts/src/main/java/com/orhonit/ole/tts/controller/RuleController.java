package com.orhonit.ole.tts.controller;

import com.google.common.collect.Maps;
import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.model.Role;
import com.orhonit.ole.sys.service.RoleService;
import com.orhonit.ole.sys.service.SysDictService;
import com.orhonit.ole.tts.dto.RuleDTO;
import com.orhonit.ole.tts.service.ruleinfo.RuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : haoshuai
 * CreateDate : 18-1-8
 * CreateTime : 上午9:54
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

//    注册角色服务
    @Autowired
    private RoleService roleService;

//    注册字典服务
    @Autowired
    private SysDictService sysDictService;

    /**
     * 获取规则列表
     * @param request
     * @return
     */

    @GetMapping("/list")
    public TableResponse<RuleDTO> listRule(TableRequest request) {
        return TableRequestHandler.<RuleDTO> builder().countHandler(new TableRequestHandler.CountHandler() {

            @Override
            public int count(TableRequest request) {
                return ruleService.getRuleCount(request.getParams());
            }
        }).listHandler(new TableRequestHandler.ListHandler<RuleDTO>() {

            @Override
            public List<RuleDTO> list(TableRequest request) {
                List<RuleDTO> list = ruleService.getRuleList(request.getParams(), request.getStart(), request.getLength());
                return list;
            }
        }).build().handle(request);
    }

    @PostMapping("/save")
    public Result<Object> saveRule(@RequestBody RuleDTO ruleDTO) {
        this.ruleService.save(ruleDTO);
        return ResultUtil.success();
    }
    
    @PostMapping("/update")
    public Result<Object> updateRule(@RequestBody RuleDTO ruleDTO) {
        this.ruleService.update(ruleDTO);
        return ResultUtil.success();
    }

    @GetMapping("/listAllRole")
    public List<Role> roles() {
        return roleService.list(Maps.newHashMap(), null, null);
    }

    @GetMapping("/getRuleById")
    public RuleDTO getRuleById(@RequestParam("ruleId") Integer ruleId){
        return ruleService.getRuleById(ruleId);
    }

    @LogAnnotation
    @DeleteMapping("/deleteRuleById/{ruleId}")
    @ApiOperation(value = "删除规则")
    public void deleteRuleById(@PathVariable Integer ruleId){
        ruleService.deleteRuleById(ruleId);
    }

}
