
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	@Query("select j from Invention j where j.id = :id")
	Invention findInventionById(int id);

	@Query("select j from Invention j where j.inventor.id = :inventorId")
	Collection<Invention> findInventionsByInventorId(int inventorId);

	@Query("select i from Invention i where i.id = :id and i.inventor.id = :inventorId")
	Invention findOneByIdAndInventorId(int id, int inventorId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId); // Cambia 'Object' por 'Part' 

	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :inventionId")
	Double computeInventionCost(int inventionId);

	@Query("select i from Invention i where i.ticker = :ticker")
	Invention findOneByTicker(String ticker);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	int countPartsByInventionId(int inventionId);
}
