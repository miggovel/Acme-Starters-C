
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;
import acme.entities.part.Part;

@Repository
public interface InventorPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.id = :id")
	Part findPartById(int id);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);

	@Query("select i from Invention i where i.id = :inventionId and i.inventor.id = :inventorId")
	Invention findInventionByIdAndInventorId(int inventionId, int inventorId);

	// NUEVO: Busca una pieza concreta validando que pertenece a un invento del inventor en sesión
	@Query("select p from Part p where p.id = :id and p.invention.inventor.id = :inventorId")
	Part findOneByIdAndInventorId(int id, int inventorId);

	// NUEVO: Busca las piezas de un invento, asegurando la propiedad del inventor
	@Query("select p from Part p where p.invention.id = :inventionId and p.invention.inventor.id = :inventorId")
	Collection<Part> findManyByInventionIdAndInventorId(int inventionId, int inventorId);

}
