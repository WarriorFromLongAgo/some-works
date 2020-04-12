package com.orhonit.ole.online.dto;

import com.orhonit.ole.sys.model.Articles;

import lombok.Getter;
import lombok.Setter;

/**
 * 公告通知对象
 * 
 * @author caodw
 *
 *
 */
@Getter
@Setter
public class NoticeDto extends BaseDto {

	private static final long serialVersionUID = 3780307737849221278L;

	private Articles articles;
}
