package org.norsys.pamela.webhook.events;

import org.springframework.context.ApplicationEvent;
/**
 * ma classe qui si'occupe de mon evenement message
 * @author panou
 *
 */
public class MessageRecuEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	private String message;
	public String getMessage() {
		return message;
	}

	public MessageRecuEvent(Object source, String message) {
		super(source);
		// TODO Auto-generated constructor stub
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
