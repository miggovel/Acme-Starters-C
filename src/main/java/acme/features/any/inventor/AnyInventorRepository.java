
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

@Repository
public interface AnyInventorRepository extends AbstractRepository {

	@Query("select t from Inventor t where t.userAccount.id = :id ")
	Inventor findOneInventorByUserAccountId(int id);

	@Query("select t from Inventor t where t.id = :id")
	Inventor findInvetorById(int id);
}
