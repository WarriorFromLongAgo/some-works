package com.orhonit.ole.tts.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.tts.dto.ComplainDTO;
import com.orhonit.ole.tts.service.complain.ComplainService;


/**
 * 投诉控制器
 */
@RestController
@RequestMapping("/complain")
public class WarnComplainController {

    @Autowired
    private ComplainService complainService;
    
    /**
     * 列表
     * @param request
     * @return
     */
    @GetMapping(value="/list")
    public TableResponse<ComplainDTO> list(TableRequest request) {
        return TableRequestHandler.<ComplainDTO> builder().countHandler(new TableRequestHandler.CountHandler() {
            @Override
            public int count(TableRequest request) {
                return complainService.getListCount(request.getParams());
            }
        }).listHandler(new TableRequestHandler.ListHandler<ComplainDTO>() {

            @Override
            public List<ComplainDTO> list(TableRequest request) {
                List<ComplainDTO> list = complainService.getList(request.getParams(), request.getStart(), request.getLength());
                return list;
            }
        }).build().handle(request);
    }
    
    /**
	 * 根据id获取投诉详情
	 * @param id
	 * @return
	 */
	@GetMapping("/query/{id}")
	public Result<Object> queryByCaseId(@PathVariable Integer id) {
		ComplainDTO complainDTO= complainService.getComplain(id);
		if(complainDTO != null){
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, complainDTO);
		}else{
			return ResultUtil.toResponseWithData(ResultCode.ERROR, "不存在的投诉信息");
		}
	}
}
