package com.orhonit.modules.enterprise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.enterprise.entity.NopublicReply;
import com.orhonit.modules.enterprise.mapper.NopublicReplyMapper;
import com.orhonit.modules.enterprise.service.NopublicReplyService;

@Service
public class NopublicReplyServiceImpl extends ServiceImpl<NopublicReplyMapper, NopublicReply> implements NopublicReplyService{
	
	@Autowired
	private NopublicReplyMapper replyMapper;

	@Override
	public NopublicReply selectNopublicReplyById(Long id) {
		return replyMapper.selectNopublicReplyById(id);
	}

}
