package org.norsys.pamela.webhook.persistance;

import org.norsys.pamela.webhook.model.DestinationApi;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Cette interface permets de faire persister l'objet DestinationAPI et effectuer des methodes CRUD
 * @author panou
 *
 */
public interface DestinationApiRepository extends CrudRepository<DestinationApi, Long> {
	//en plus des methodes CRUD nous rajjoutons d'autres methodes telles ques:
	
	@Modifying
	@Transactional
	@Query("update destination d set d.enligne = true where d.id = ?")
	int setDestinationApiOnline(long id);
	
	
	@Modifying
	@Transactional
	@Query("update Destination d set d.enligne = false where d.id = ?")
	int setDestinationApiOffline(long id) ;

}
