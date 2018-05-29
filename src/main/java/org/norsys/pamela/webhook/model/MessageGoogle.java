package org.norsys.pamela.webhook.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * la representation objet d'un message google recu sur action on Google
 * @author panou
 *
 */
@Entity
public class MessageGoogle {
	
	static final long MESSAGE_TIMEOUT = 24 * 60 * 60 * 1000;
	public static long getMessageTimeout() {
		return MESSAGE_TIMEOUT;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	public long getId() {
		return id;
	}

	
	@Column(nullable = false)
	private String messageBody;
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	@Column(nullable = false)
	private String contentType;
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	
	@Column(name = "heure", nullable = false)
	private Timestamp timestamp;
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	@ManyToOne(optional = false)
	private DestinationApi destination;
	public DestinationApi getDestination() {
		return destination;
	}
	public void setDestination(DestinationApi destination) {
		this.destination = destination;
	}

	
	public MessageGoogle(String messageBody, String contentType, DestinationApi destination) {
		this.messageBody = messageBody;
		this.contentType = contentType;
		this.destination = destination;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageGoogle other = (MessageGoogle) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MessageGoogle [id=" + id + ", messageBody=" + messageBody + ", contentType=" + contentType
				+ ", timestamp=" + timestamp + ", destination=" + destination + "]";
	}









	





	
}
