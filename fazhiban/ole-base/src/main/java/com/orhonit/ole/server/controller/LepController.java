package com.orhonit.ole.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.server.dao.LepDao;
import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.Lepeson;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
/**
 * 执法人员列表控制器
 *
 */
@RestController
@RequestMapping("/LepLists")
public class LepController {

	@Autowired
	private LepDao lepDao;
	
	@Autowired
	private LtcAttDao ltcAttDao;

	@GetMapping
	@ApiOperation(value = "执法人列表")
	public TableResponse<Lepeson> list(TableRequest request) {
		User user = UserUtil.getCurrentUser();
		LtcAtt ltcAtt = ltcAttDao.getLtc(user.getDept_id());
		request.getParams().put("dept_id", user.getDept_id());
		if(ltcAtt != null && ltcAtt.getDept_property() == 3 && ltcAtt.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				request.getParams().put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			request.getParams().put("lx_type", 2);
			request.getParams().put("deptIds", lepDao.execFunction(null, user.getDept_id()));
		}
		return TableRequestHandler.<Lepeson> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=lepDao.count(request.getParams());
				return result;
			}
		}).listHandler(new ListHandler<Lepeson>() {

			@Override
			public List<Lepeson> list(TableRequest request) {
				
				List<Lepeson> lists=lepDao.list(request.getParams(), request.getStart(), request.getLength());
				return lists;
			}
		}).build().handle(request);
	}

}
