package org.norsys.pamela.webhook.restcontrollers;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.norsys.pamela.webhook.events.MessageRecuEvent;
import org.norsys.pamela.webhook.model.DestinationApi;
import org.norsys.pamela.webhook.model.MessageGoogle;
import org.norsys.pamela.webhook.persistance.DestinationApiRepository;
import org.norsys.pamela.webhook.persistance.MessageGoogleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author panou
 *
 */
@RestController
public class DestinationController implements ApplicationEventPublisherAware {

	private static final Logger logger = LoggerFactory.getLogger(DestinationController.class);

	// pour effectuer des modif en BD j'ai besoin des mes interfaces repository

	@Autowired
	private DestinationApiRepository destinationApiRepository;

	@Autowired
	private MessageGoogleRepository messageGoogleRepository;

	// pour pouvoir publier un evenement par l'application contexte j'ai besoin de
	// cet attribut qui sera injecter
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;

	}
	/**
	 * renregistrement d'une nouvelle destination
	 * @param url : url de la destination
	 * @return l'id de la destination creer
	 */
	@PostMapping("/destination")
	public Long enregistreUneNouvelleDestination(@RequestParam("url") String url) {
		// verifie que l'url n'est ni vide ni null
		validateParam(url, "url");
		
		DestinationApi destination =  destinationApiRepository.save(new DestinationApi(url));
		
		logger.debug("Reception de la destination  {}", url);
		
		return destination.getId();

	}
	
	/**
	 * liste de toutes les destinations existantes
	 * @return
	 */
	@GetMapping("/destinations")
	public Iterable<DestinationApi> listeDesDestinations(){
		logger.debug("listing de toutes les destinations");
		
		return destinationApiRepository.findAll();
	}
	
	/**
	 * efface une destination en fonction de son id
	 * @param id
	 */
	@DeleteMapping("/detination/{id}")
	public void effacerLaDestination(@PathVariable("id") long id) {
		
		destinationApiRepository.deleteById(id);
		logger.debug("Deleted Destination {}", TrouverLaDestinationParId(id).getUrl());
	}
	

	
	
	@PostMapping("/destination/{id}/message")
	public void PostMessageALaDestination(@PathVariable("id") long id,
										  @RequestBody String body,
										  @RequestHeader("content-Type") String contentType) {
		//je verifie qu'il ya bien un param body dans la requete et qu'il n'est pas vide
		validateParam(body, "body");
		//je trouve la destination correspondant a l'id de la requete
		DestinationApi destination = TrouverLaDestinationParId(id);
		//j'associe le message a la destination dans la BD
		MessageGoogle message = messageGoogleRepository.save(new MessageGoogle(body, contentType, destination));
		
		logger.debug("le message {} d'ID {} viens d'être recu pour la destination {}", message.getMessageBody(), message.getId(), destination.getUrl());
		//le plus plus important est ceci: maintennat que le message a été enregistrer en BD je le publie avec le publisher de l'applicationContexte
		applicationEventPublisher.publishEvent(new MessageRecuEvent(this, message));
	}
	
	
	//les methodes utilisées plus haut
	private DestinationApi TrouverLaDestinationParId(long id) {
		Optional<DestinationApi>  destination = destinationApiRepository.findById(id);
		
		return destination.orElseThrow(() -> new NoSuchElementException("Does not exist destination with ID " + id));
	}
	

	
	private void validateParam(String param, String paramName) {
		if(param == null || paramName.isEmpty()) {
			throw new IllegalArgumentException("le '" + paramName + " 'ne doit pas être vide ou null");
		}
		
	}

}
