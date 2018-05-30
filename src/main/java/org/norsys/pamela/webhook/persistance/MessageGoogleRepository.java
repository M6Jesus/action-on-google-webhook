package org.norsys.pamela.webhook.persistance;

import java.util.List;

import org.norsys.pamela.webhook.model.MessageGoogle;
import org.norsys.pamela.webhook.service.MessageGoogleProcessorException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * cette Interface permet de faire persister dans la base de onner un objet messageGoogle et effectuer des methodes CRUD
 * @author panou
 *
 */
//les parametres de CrudRepository sont le type de l’entité que l’on manipule et le type de l’identifiant de l’entité
public interface MessageGoogleRepository extends CrudRepository<MessageGoogle, Long>{
	//en plus de toutes les methodes CRUD heriter de la classe mere, nous ajouns une methode qui permet de trouver toutes les messages en fonction de leurs destinations
	
	@Modifying
	@Transactional
	@Query("select messageBody, destinationId from message join destination on message.destination_id = destination.destinationid order by destinationId Asc")
	List<MessageGoogle> findAllMessageByDestinationOrderByIdAsc();
	
	@Modifying
	@Transactional
	@Query("select messageBody from message join destination on message.destination_id = destination.destinationid where message.destination_id= ?")
	List<MessageGoogle> findAllMessageByDestination(long destinationId) throws MessageGoogleProcessorException;
	
}
