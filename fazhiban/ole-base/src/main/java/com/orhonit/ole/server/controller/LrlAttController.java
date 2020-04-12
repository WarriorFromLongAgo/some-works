package com.orhonit.ole.server.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.server.dao.LrlAttDao;
import com.orhonit.ole.server.model.LrlAtt;
import com.orhonit.ole.server.model.LtcAtt;

import io.swagger.annotations.ApiOperation;
/**
 * 法律法规列表控制器
 *
 */
@RestController
@RequestMapping("/LrlAtts")
public class LrlAttController {

	@Autowired
	private LrlAttDao lrlAttDao;

	@GetMapping
	@RequiresPermissions(value = "sys:log:query")
	@ApiOperation(value = "主体列表")
	public TableResponse<LrlAtt> list(TableRequest request) {
		return TableRequestHandler.<LrlAtt> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=lrlAttDao.count(request.getParams());
				return result;
			}
		}).listHandler(new ListHandler<LrlAtt>() {

			@Override
			public List<LrlAtt> list(TableRequest request) {
				List<LrlAtt> lists=lrlAttDao.list(request.getParams(), request.getStart(), request.getLength());
				List<LtcAtt> detlists=lrlAttDao.getLtcs();
				if(null!=lists&&lists.size()>0){
					for(LrlAtt les:lists){
						for(LtcAtt ltc:detlists){
							if(StrUtil.isNotEmpty(les.getPub_dept())&&les.getPub_dept().equals(String.valueOf(ltc.getId()))){
								les.setPub_dept(ltc.getName());
								break;
							}
						}
					}
				}
				return lists;
			}
		}).build().handle(request);
	}
	
	
	
}
