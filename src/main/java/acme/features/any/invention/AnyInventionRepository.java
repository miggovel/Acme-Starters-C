
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;

@Repository
public interface AnyInventionRepository extends AbstractRepository {

	@Query("select ar from Invention ar where ar.id = :id")
	Invention findInventionById(int id);

	@Query("select ar from Invention ar where ar.draftMode = false")
	Collection<Invention> findPublishedInvention();

	@Query("select ar from Invention ar where ar.inventor.id = :id and ar.draftMode = false")
	Collection<Invention> findPublishedInventionsByInventorId(int id);

}
