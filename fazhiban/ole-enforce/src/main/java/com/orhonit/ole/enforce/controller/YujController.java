package com.orhonit.ole.enforce.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.cim.util.StringUtil;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dao.YujDao;
import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.YujDTO;
import com.orhonit.ole.enforce.dto.YujPersonDTO;
import com.orhonit.ole.enforce.service.yuj.YujService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 预警列表
 */
@RestController
@RequestMapping("/yuj")
@Slf4j
public class YujController {

    @Autowired
    private YujService yujService;

    @Autowired
    private YujDao yujDao;
    
    @Autowired
    private PersonDao personDao;
    
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     * @param request
     * @return
     */
    @GetMapping(value="/list")
    public TableResponse<YujDTO> listYuj(TableRequest request) {
        return TableRequestHandler.<YujDTO> builder().countHandler(new TableRequestHandler.CountHandler() {
            @Override
            public int count(TableRequest request) {
                User user = UserUtil.getCurrentUser();
                request.getParams().put("pid", user.getPerson_id().toString());
                if(null != request.getParams().get("deptId") && StringUtil.isEmpty(request.getParams().get("deptId"))) {
                	request.getParams().put("deptIds", sysDeptService.getDeptsApp(request.getParams().get("deptId").toString()));
                	request.getParams().remove("deptId");
                }
                return yujDao.YuJcount(request.getParams());
            }
        }).listHandler(new TableRequestHandler.ListHandler<YujDTO>() {

            @Override
            public List<YujDTO> list(TableRequest request) {
                User user = UserUtil.getCurrentUser();
                request.getParams().put("pid", user.getPerson_id().toString());
                if(null != request.getParams().get("deptId") && StringUtil.isEmpty(request.getParams().get("deptId"))) {
                	request.getParams().put("deptIds", sysDeptService.getDeptsApp(request.getParams().get("deptId").toString()));
                	request.getParams().remove("deptId");
                }
                List<YujDTO> list = yujService.appList(request.getParams(), request.getStart(), request.getLength());
                for(int i=0;i<list.size();i++){
                	list.get(i).setPersonName(user.getNickname());
                }
                return list;
            }
        }).build().handle(request);
    }


    /**
     * 标题预警总数
     * @param request
     * @return
     */
    @PostMapping(value="/Bt")
    public Integer  hehe(TableRequest request)
    {
        User user = UserUtil.getCurrentUser();
        request.getParams().put("pid", user.getPerson_id().toString());
        request.getParams().put("deal", CommonParameters.YuJChuL.WCL);
        return yujDao.YuJcount(request.getParams());
    }

    /**
     *  标题的预警列表
     * @param request
     * @return
     */
    @GetMapping(value="/Btlist")
    public TableResponse<YujDTO> listBtYuj(TableRequest request) {
        return TableRequestHandler.<YujDTO> builder().countHandler(new TableRequestHandler.CountHandler() {
            @Override
            public int count(TableRequest request) {
                User user = UserUtil.getCurrentUser();
                request.getParams().put("pid", user.getPerson_id().toString());
                request.getParams().put("deal", CommonParameters.YuJChuL.WCL);
                return yujDao.YuJcount(request.getParams());
            }
        }).listHandler(new TableRequestHandler.ListHandler<YujDTO>() {

            @Override
            public List<YujDTO> list(TableRequest request) {
                User user = UserUtil.getCurrentUser();
                request.getParams().put("deal", CommonParameters.YuJChuL.WCL);
                request.getParams().put("pid", user.getPerson_id().toString());
                List<YujDTO> list = yujService.appList(request.getParams(), 0, 20);                
                return list;
            }
        }).build().handle(request);
    }

    /**
     *查询某一条预警
     * @param yujId
     * @param request
     * @return
     */
    @GetMapping("/query/{yujId}")
    public Result<YujDTO> queryByYujId(@PathVariable String yujId,TableRequest request) {
       
    	User user = UserUtil.getCurrentUser();
    	
        YujDTO dto = this.yujService.getWarnInfoByWarnId(yujId);
       
        Map<String, Object> params = new HashMap<>();
        params.put("warnId",yujId);
        
      //获取当前登录人角色
      	List<PersonAppDTO> persons = this.personDao.getPersonRole(user.getPerson_id());

      	List<String> roles = new ArrayList<String>();
      		
      	for(PersonAppDTO person:persons){
      		roles.add(person.getRoleId().toString());
      	}
        
      	if(roles.contains(CommonParameters.Role.LAW_ENFORCEMENT_OFFICIALS.toString())){
      		params.put("personId",user.getPerson_id());
      	}
      	
        
        List<YujPersonDTO> list = this.yujService.getWarnPerson(params);
        
        dto.setYujPersons(list);
        return ResultUtil.toResponseWithData(ResultCode.SUCCESS, dto);
    }

   /**
     * 预警处理
     * @param yujId
     * @param request
     * @return
     */
    @PostMapping("/up")
    public Integer up(@RequestBody YujPersonDTO yujPersonDTO) {
    	User user = UserUtil.getCurrentUser();
    	yujPersonDTO.setPersonId(user.getPerson_id());
    	yujPersonDTO.setIsDeal(CommonParameters.YuJChuL.YCL);
    	yujPersonDTO.setUpdateBy(user.getUsername());
    	yujPersonDTO.setUpdateDate(new Date());
    	yujPersonDTO.setUpdateName(user.getNickname());
    	
    	YujDTO yujDTO = new YujDTO();
    	yujDTO.setId(yujPersonDTO.getWarnId());
    	yujDTO.setUpdateBy(user.getUsername());
    	yujDTO.setUpdateDate(yujPersonDTO.getUpdateDate());
    	yujDTO.setUpdateName(user.getNickname());
    	
    	this.yujService.warnUpdate(yujDTO);
    	return this.yujService.warnDeal(yujPersonDTO);
   	 	
   	 	
    }
    
    /**
     * 查询某个案件的预警信息
     * @param caseId
     * @return
     */
    @GetMapping("/warnList")
	public Result<Object> list(@RequestParam(value = "caseId" , required = false) String caseId ){
		List<YujDTO> YujDTO = this.yujService.getWarnList(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,YujDTO);
	}
}
