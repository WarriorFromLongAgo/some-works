package com.orhonit.ole.tts.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dto.WarnDTO;
import com.orhonit.ole.tts.entity.DeptEntity;
import com.orhonit.ole.tts.service.dashboard.DashboardService;
import com.orhonit.ole.tts.service.warn.WarnInfoService;

import lombok.extern.slf4j.Slf4j;


/**
 * 预警列表
 */
@RestController
@RequestMapping("/warn")
@Slf4j
public class WarnController {

    @Autowired
    private WarnInfoService warnInfoService;
    
    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     * @param request
     * @return
     */
    @GetMapping(value="/list")
    public TableResponse<Map<String, Object>> listYuj(TableRequest request) {
    	request.getParams().put("deptIds", sysDeptService.getDepts());
    	request.getParams().put("createBy", UserUtil.getCurrentUser().getUsername());
        return TableRequestHandler.<Map<String, Object>> builder().countHandler(new TableRequestHandler.CountHandler() {
            @Override
            public int count(TableRequest request) {
                return warnInfoService.getListCount(request.getParams());
            }
        }).listHandler(new TableRequestHandler.ListHandler<Map<String, Object>>() {

            @Override
            public List<Map<String, Object>> list(TableRequest request) {
                List<Map<String, Object>> list = warnInfoService.getList(request.getParams(), request.getStart(), request.getLength());
                return list;
            }
        }).build().handle(request);
    }
    
    
    /**
     * 基础信息预警列表
     * @param request
     * @return
     */
    @GetMapping(value="/baseList")
    public TableResponse<WarnDTO> baseList(TableRequest request) {
    	User user = UserUtil.getCurrentUser();
		if (!user.isAdmin()) {
			DeptEntity deptEntity = dashboardService.getDeptDTO(user.getDept_id());
			if (CommonParameters.LawType.JD.equals(deptEntity.getLawType())) {
				// 如果是市本级的法制办则显示所有
				if (CommonParameters.AreaLevel.SJ.equals(deptEntity.getLevel())) {

				} else {
					//委办局
					//request.getParams().put("deptIds", lepDao.execFunction(null, user.getDept_id()));
				}
			}
		}
        return TableRequestHandler.<WarnDTO> builder().countHandler(new TableRequestHandler.CountHandler() {
            @Override
            public int count(TableRequest request) {
                return warnInfoService.getBaseListCount(request.getParams());
            }
        }).listHandler(new TableRequestHandler.ListHandler<WarnDTO>() {

            @Override
            public List<WarnDTO> list(TableRequest request) {
                List<WarnDTO> list = warnInfoService.getBaseList(request.getParams(), request.getStart(), request.getLength());
                return list;
            }
        }).build().handle(request);
    }
    /**
	 * 根据id获取预警详情
	 * @param id
	 * @return
	 */
	@GetMapping("/query/{warnId}")
	public Result<Object> queryByCaseId(@PathVariable String warnId) {
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, this.warnInfoService.getWarnById(warnId));
	}
	
	/**
	 * 根据id获取预警人员详情
	 * @param id
	 * @return
	 */
	@GetMapping("/query/person/{warnId}")
	public Result<List<Map<String,Object>>> queryPerson(@PathVariable String warnId) {
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, this.warnInfoService.getPersonByWarnId(warnId));
	}
	
	/**
	 * 获取当前登陆人未读的预警反馈信息
	 * @param id
	 * @return
	 */
	@GetMapping("/warnRead")
	public Result<List<Map<String,Object>>> warnRead(@RequestParam String param) {
		return null;
	}
	
	/**
	 * 获取当前登录人是否有未阅读的预警反馈
	 * @param id
	 * @return
	 */
	@GetMapping("/haveWarnRead")
	public Result<Object> haveWarnRead() {
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,dashboardService.haveWarnRead());
	}
	
	/**
	 * 置为已读
	 * @param id
	 * @return
	 */
	@GetMapping("/isRead/{warnId}/{personId}")
	public Result<List<Map<String,Object>>> queryPerson(@PathVariable String warnId,@PathVariable String personId) {
		this.warnInfoService.isRead(warnId,personId);
		return ResultUtil.success();
	}
}
