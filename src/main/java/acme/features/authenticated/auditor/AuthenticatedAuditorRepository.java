
package acme.features.authenticated.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Auditor;

@Repository
public interface AuthenticatedAuditorRepository extends AbstractRepository {

	@Query("select count(a) > 0 from Auditor a where a.userAccount.id = :userAccountId")
	boolean existsAuditorByUserAccountId(int userAccountId);

	@Query("select a from Auditor a where a.userAccount.id = :userAccountId")
	Auditor findAuditorByUserAccountId(int userAccountId);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);
}
