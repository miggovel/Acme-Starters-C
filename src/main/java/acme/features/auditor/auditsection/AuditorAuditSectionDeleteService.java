
package acme.features.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionDeleteService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					section;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.section = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = this.section != null && !this.section.isPublished() && !this.section.getAuditReport().isPublished() && this.section.getAuditReport().getAuditor().getId() == auditorId;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// no binding
	}

	@Override
	public void validate() {
		// normalmente vacío
	}

	@Override
	public void execute() {
		this.repository.delete(this.section);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.section, "name", "notes", "hours", "kind", "published");
	}
}
