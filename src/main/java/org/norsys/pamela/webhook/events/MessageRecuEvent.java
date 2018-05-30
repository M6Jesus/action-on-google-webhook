package org.norsys.pamela.webhook.events;

import org.norsys.pamela.webhook.model.MessageGoogle;
import org.springframework.context.ApplicationEvent;
/**
 * ma classe qui si'occupe de mon evenement message
 * @author panou
 *
 */
public class MessageRecuEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	private MessageGoogle message;
	public MessageGoogle getMessage() {
		return message;
	}

	public MessageRecuEvent(Object source, MessageGoogle message) {
		super(source);
		// TODO Auto-generated constructor stub
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
