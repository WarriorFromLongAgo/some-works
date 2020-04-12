package com.orhonit.ole.enforce.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.CheckRecordDTO;
import com.orhonit.ole.enforce.entity.CheckRecordEntity;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.service.lssued.LssuedService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 专项控制器
 * 
 * @author lizj
 *
 */
@RestController
@RequestMapping("/scenc")
@Slf4j
public class SceneController {

	@Autowired
	private LssuedService lssuedService;

	@GetMapping(value = "/list")
	@ApiOperation(value = "下达通知列表")
	public TableResponse<LssuedEntity> listCase(TableRequest request) {
		request.getParams().put("status", CommonParameters.CheckStatus.LOCATE_CHECK);
		return TableRequestHandler.<LssuedEntity> builder().countHandler(new CountHandler() {
			@Override
			public int count(TableRequest request) {
				System.out.println(request.getParams().toString());
				return lssuedService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<LssuedEntity>() {

			@Override
			public List<LssuedEntity> list(TableRequest request) {
				List<LssuedEntity> list = lssuedService.getCaseList(request.getParams(), request.getStart(),
						request.getLength());
				return list;
			}
		}).build().handle(request);
	}

	@PostMapping(value = "/recordSave")
	@ApiOperation(value = "专项检查记录入库")
	public void recordSave(@RequestBody CheckRecordDTO checkRecordDTO) {
		CheckRecordEntity checkRecordEntity = new CheckRecordEntity();
		BeanUtils.copyProperties(checkRecordDTO, checkRecordEntity);
		lssuedService.saveCheckRecord(checkRecordEntity);
	}
	
	/**
	 * 查找专项检查记录
	 * @param caseid
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	@ResponseBody
	public CheckRecordEntity getCheckById(@PathVariable String id) {
		//根据ID获取案件
		CheckRecordEntity checkRecordEntity = this.lssuedService.findRecord(id);
		return checkRecordEntity;
	}
	
	@GetMapping(value = "/getRecord")
	@ApiOperation(value = "获取专项检查记录")
	public Result<CheckRecordDTO> getRecord(@RequestParam String checkId) {
		CheckRecordEntity checkRecordEntity = lssuedService.getRecordBycheckId(checkId);
		if (checkRecordEntity != null) {
			CheckRecordDTO checkRecordDTO = new CheckRecordDTO();
			BeanUtils.copyProperties(checkRecordEntity, checkRecordDTO);
			// 转换一些内容
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkRecordDTO);
		} else {
			return ResultUtil.toResponseWithData(ResultCode.ERROR, null);
		}
	}
}
