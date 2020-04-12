package com.orhonit.modules.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.orhonit.common.utils.SplitUtils;
import com.orhonit.modules.enterprise.entity.NopublicEnterpriseMsg;
import com.orhonit.modules.enterprise.entity.NopublicFile;
import com.orhonit.modules.enterprise.mapper.NopublicEnterpriseMsgMapper;
import com.orhonit.modules.enterprise.mapper.NopublicFileMapper;
import com.orhonit.modules.enterprise.model.NopublicEnterpriseMsgModel;
import com.orhonit.modules.enterprise.model.NopublicFileModel;
import com.orhonit.modules.enterprise.service.NopublicEnterpriseMsgService;

@Service
public class NopublicEnterpriseMsgServiceImpl extends ServiceImpl<NopublicEnterpriseMsgMapper, NopublicEnterpriseMsg>
		implements NopublicEnterpriseMsgService {

	@Autowired
	private NopublicEnterpriseMsgMapper msgMapper;

	@Autowired
	private NopublicFileMapper fileMapper;

	@Override
	public Map<String, Object> selectNopublicEnterpriseByUserId(Long id, String type,int pageNum,int pageSize) {
		List<NopublicEnterpriseMsgModel> list = new ArrayList<>();
		
		PageHelper.startPage(pageNum, pageSize);
		List<NopublicEnterpriseMsg> msgS = msgMapper.selectNopublicEnterpriseByUserId(id, type);
		
		List<NopublicEnterpriseMsg> msgSize = msgMapper.selectNopublicEnterpriseByUserId(id, type);
		//使用model返回进行封装
		
		if (null != msgS) {
			for (NopublicEnterpriseMsg nopublicEnterpriseMsg : msgS) {
				List<NopublicFileModel> fileResults = new ArrayList<>();
				if(StringUtils.isNotBlank(nopublicEnterpriseMsg.getAccessoryIds())) {
					List<Long> fileIds = SplitUtils.splitIds(nopublicEnterpriseMsg.getAccessoryIds(), ",");
					// 查询所有文件
					List<NopublicFile> fileList = fileMapper.selectBatchIds(fileIds);
					for (NopublicFile file : fileList) {
						String fileName = file.getFileName()+"."+file.getSuffix();
						String downPath = file.getFilePath() + file.getFileNike() + file.getSuffix();
						fileResults.add(new NopublicFileModel(file.getId(),fileName, downPath));
					}
				}
				String auditStatus="";
				if(null ==nopublicEnterpriseMsg.getAuditStatus()) {
					auditStatus="0";
				}else {
					auditStatus=nopublicEnterpriseMsg.getAuditStatus();
				}
				list.add(new NopublicEnterpriseMsgModel(nopublicEnterpriseMsg.getId(),
						nopublicEnterpriseMsg.getTitle(),
						nopublicEnterpriseMsg.getContent(),
						nopublicEnterpriseMsg.getUserName(),
						nopublicEnterpriseMsg.getReceiveEnterpriseId(),
						nopublicEnterpriseMsg.getReceiveEnterpriseName(), 
						"0".equals(nopublicEnterpriseMsg.getIsRead()) ?"未读":"已读",
						fileResults,auditStatus));
			}
		}
		Map<String, Object> results=new HashMap<>();
		results.put("size", msgSize.size());
		results.put("list", list);
		return results;
	}

}
