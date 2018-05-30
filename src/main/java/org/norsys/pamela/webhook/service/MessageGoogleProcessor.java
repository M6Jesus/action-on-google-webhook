package org.norsys.pamela.webhook.service;

import java.util.List;

import org.norsys.pamela.webhook.events.MessageRecuEvent;
import org.norsys.pamela.webhook.model.DestinationApi;
import org.norsys.pamela.webhook.model.MessageGoogle;
import org.norsys.pamela.webhook.persistance.DestinationApiRepository;
import org.norsys.pamela.webhook.persistance.MessageGoogleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Cette classe joue le role de Listener d'evenement
 * 
 * @author panou
 *
 */
@Service
public class MessageGoogleProcessor {
	private static final Logger logger = LoggerFactory.getLogger(MessageGoogleProcessor.class);

	@Autowired
	private MessageGoogleRepository messageGoogleRepository;

	@Autowired
	private DestinationApiRepository destinationAPiRepository;

	@Autowired
	private RestTemplate restTemplate;

	public MessageGoogleProcessor(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	/**
	 * le listener asynchrone de l'evenement messageRecuEvent
	 */
	@Async
	@EventListener
	public void messageRecuEventListener(MessageRecuEvent messageRecuEvent) {
		MessageGoogle messageGoogle = messageRecuEvent.getMessage();

		logger.debug("Ecoute du message {} d'id : {}", messageGoogle.getMessageBody(), messageGoogle.getId());

		// envoie du message a la destination
		EnvoyeurMessageALaDestination(messageGoogle.getDestination());

	}

	/**
	 * cette methode programme l'exectute de toute cette classe, l'ecoute et
	 * l'envoie du message
	 */
	@Scheduled(cron = "* * * * *")
	public void programmerLeMessageGoogleProcessor() {
		logger.debug("execution de messageGoogleProcessor à {} ", System.currentTimeMillis());
		// trouve toutes les destinations et pour chaque destination on envoie la liste
		// de messages (une destinattion a une liste de messages)
		destinationAPiRepository.findAll().forEach(DestinationApi -> EnvoyeurMessageALaDestination(DestinationApi));
	}

	private void EnvoyeurMessageALaDestination(DestinationApi destination) {
		logger.debug("debut de l'envoi du message pour la destination {}", destination.getUrl());

		try {
			// je met la destination online au cas ou
			destinationAPiRepository.setDestinationApiOnline(destination.getId());
			// je recupere les(les) messages qui sont liés a cette destination et qui
			// doivent etre envoyer
			List<MessageGoogle> messageGoogle = messageGoogleRepository
					.findAllMessageByDestination(destination.getId());

			for (MessageGoogle message : messageGoogle) {
				if (message.isTimeOut()) {
					effacer(message);
				} else {
					envoyer(message);
				}
			}
		} catch (MessageGoogleProcessorException ex) {
			logger.info("Le messageGoogleProcessor a la destination a rencontrer une exeption : {}", ex.getMessage());
		}

	}

	private void envoyer(MessageGoogle message) throws MessageGoogleProcessorException {
		try {
			HttpHeaders headers = new HttpHeaders();

			headers.set(HttpHeaders.CONTENT_TYPE, message.getContentType());
			String url = message.getDestination().getUrl();
			HttpEntity<String> request = new HttpEntity<>(message.getMessageBody(), headers);
			Thread.sleep(500);

			ResponseEntity<String> reponse = restTemplate.postForEntity(url, request, String.class);
			// si le message a bien ete envoyer on l'efface dans la base pour ne pas avoir a
			// le renvoyer
			// sinon on met la destination offline
			if (reponse.getStatusCode().equals(HttpStatus.OK)) {
				logger.debug("message d'id {} et de body {} à bien été envoyé :-)", message.getId(),
						message.getMessageBody());
				effacer(message);
			} else {
				throw new MessageGoogleProcessorException("code contraire a HttpStatus 200");
			}
		} catch (Exception ex) {
			logger.info("l'envoie du message a la destination  à rencontrer une exception ", ex.getMessage());
			logger.debug("mesage non envoyer {}  :-(", message.getId());
			destinationAPiRepository.setDestinationApiOffline(message.getId());
			throw new MessageGoogleProcessorException(ex.getMessage());
		}
	}

	private void effacer(MessageGoogle message) {
		messageGoogleRepository.deleteById(message.getId());
	}

}
