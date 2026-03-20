
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;
// Importa tu entidad Part aquí si la usas en las colecciones
// import acme.entities.part.Part;
import acme.entities.part.Part;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	// --- TUS MÉTODOS ORIGINALES ---
	@Query("select j from Invention j where j.id = :id")
	Invention findInventionById(int id);

	@Query("select j from Invention j where j.inventor.id = :inventorId")
	Collection<Invention> findInventionsByInventorId(int inventorId);

	// --- NUEVOS MÉTODOS AÑADIDOS ---

	// Busca un invento concreto asegurando que pertenece al inventor logueado (Crítico para Update/Delete)
	@Query("select i from Invention i where i.id = :id and i.inventor.id = :inventorId")
	Invention findOneByIdAndInventorId(int id, int inventorId);

	// Busca las piezas de un invento (Útil para listar las piezas de un invento o borrarlas en cascada)
	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId); // Cambia 'Object' por 'Part' 

	// Calcula el coste total sumando el importe de sus piezas (ajusta 'price.amount' al nombre exacto de tu variable Money en Part)
	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :inventionId")
	Double computeInventionCost(int inventionId);

	@Query("select i from Invention i where i.ticker = :ticker")
	Invention findOneByTicker(String ticker);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	int countPartsByInventionId(int inventionId);

}
