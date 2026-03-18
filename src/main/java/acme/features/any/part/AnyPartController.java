
package acme.features.any.part;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.inventions.Part;

@Controller
@RequestMapping("/invention/part/")
public class AnyPartController extends AbstractController<Any, Part> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		//super.addBasicCommand("list", AnyPartListService.class);
		super.addBasicCommand("show", AnyPartShowService.class);

	}
}
