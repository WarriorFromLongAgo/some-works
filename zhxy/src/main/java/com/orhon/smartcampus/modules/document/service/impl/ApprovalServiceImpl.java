package com.orhon.smartcampus.modules.document.service.impl;

import com.orhon.smartcampus.modules.document.entity.Approval;
import com.orhon.smartcampus.modules.document.mapper.ApprovalMapper;
import com.orhon.smartcampus.modules.document.service.IApprovalService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class ApprovalServiceImpl extends BaseServiceImpl<ApprovalMapper, Approval>implements IApprovalService {

}
