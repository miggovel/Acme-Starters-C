
package acme.features.any.spokesperson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Spokesperson;

@Repository
public interface AnySpokespersonRepository extends AbstractRepository {

	@Query("select s from Spokesperson f where f.id = :id")
	Spokesperson findSpokespersonById(int id);

}
