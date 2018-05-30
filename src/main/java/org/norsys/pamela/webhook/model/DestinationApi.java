package org.norsys.pamela.webhook.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * la representation objet de la destination d'un message recu par le webhook
 * qui sera normalement l'Url de mon API
 * 
 * @author panou
 *
 */
@Entity(name = "destination")
public class DestinationApi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public Long getId() {
		return id;
	}

	@Column(nullable = false)
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "enLigne", nullable = false)
	private boolean online;

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	@OneToMany(mappedBy = "destination", cascade = CascadeType.REMOVE)
	private List<MessageGoogle> messages;

	public List<MessageGoogle> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageGoogle> messages) {
		this.messages = messages;
	}

	public DestinationApi(String url) {
		super();
		this.url = url;
		this.online = true;
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
		DestinationApi other = (DestinationApi) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DestinationApi [id=" + id + ", url=" + url + "]";
	}

}
