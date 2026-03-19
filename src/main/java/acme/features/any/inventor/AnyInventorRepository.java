
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.inventor.Inventor;

@Repository
public interface AnyInventorRepository extends AbstractRepository {

	@Query("select a from Inventor a where a.userAccount.id = :id")
	Inventor findOneInventorByUserAccountId(int id);

	@Query("select a from Inventor a where a.id = :id")
	Inventor findInventorById(int id);
}
