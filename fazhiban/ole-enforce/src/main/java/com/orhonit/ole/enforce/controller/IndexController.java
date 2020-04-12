package com.orhonit.ole.enforce.controller;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dao.YujDao;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.FlowDealDTO;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

    @Autowired
    private YujDao yujDao;


    @PostMapping(value="/list")
    public Integer  hehe()
    {
        return yujDao.count(new HashMap<String, Object>());
    }


}
