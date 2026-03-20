
package acme.features.any.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.inventor.Inventor;

@Service
public class AnyInventorShowService extends AbstractService<Any, Inventor> {

	@Autowired
	private AnyInventorRepository	repository;

	private Inventor				inventor;


	@Override
	public void authorise() {
		boolean status;

		status = this.inventor != null;

		super.setAuthorised(status);
	}

	//	@Override
	//	public void load() {
	//		// Comprobación preventiva de la existencia del parámetro en la petición HTTP
	//		if (super.getRequest().hasData("id", int.class)) {
	//			int id = super.getRequest().getData("id", int.class);
	//			this.inventor = this.repository.findInventorById(id);
	//		}
	//		// Si no existe el parámetro, this.inventor permanece como null por defecto.
	//	}
	@Override
	public void load() {
		try {
			if (super.getRequest().hasData("id", int.class)) {
				int id = super.getRequest().getData("id", int.class);
				this.inventor = this.repository.findInventorById(id);
			}
		} catch (final Exception oops) {
			// La excepción de mutación de tipos es interceptada silenciosamente.
			// La variable de dominio this.inventor mantiene su estado inmutable como null.
		}
	}

	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "identity.fullName", "identity.email", "bio", "keyWords", "licensed");
	}
}
