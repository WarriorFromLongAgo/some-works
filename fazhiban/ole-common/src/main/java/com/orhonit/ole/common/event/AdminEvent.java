package com.orhonit.ole.common.event;

import org.springframework.context.ApplicationEvent;

import com.orhonit.ole.common.constants.EventType;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义事件
 * 
 * @author caodw
 *
 *
 */
@Getter
@Setter
public class AdminEvent extends ApplicationEvent {

	private static final long serialVersionUID = -8244067001371123563L;

	private EventType eventType;

	public AdminEvent(Object source, EventType eventType) {
		super(source);
		this.eventType = eventType;
	}

}
