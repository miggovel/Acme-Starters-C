
package acme.features.any.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditSection;
import acme.entities.audits.SectionKind;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				section;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.section = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.section != null && this.section.isPublished();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices kinds;
		Tuple tuple;

		kinds = SelectChoices.from(SectionKind.class, this.section.getKind());

		tuple = super.unbindObject(this.section, "name", "notes", "hours", "kind", "published");
		tuple.put("kinds", kinds);
	}
}
