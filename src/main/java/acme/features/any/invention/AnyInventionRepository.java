
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;

@Repository
public interface AnyInventionRepository extends AbstractRepository {

	@Query("select x from Invention x where x.id= :id")
	Invention findInventionById(int id);

	@Query("select x from Invention x where x.draftMode = false")
	Collection<Invention> findPublishedInvention();

	@Query("select x from Invention x where x.inventor.id = :id")
	Collection<Invention> findPublishedInventionByInventorId(int id);

}
