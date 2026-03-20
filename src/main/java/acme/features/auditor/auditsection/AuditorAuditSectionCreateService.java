
package acme.features.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.entities.audits.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionCreateService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					section;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int reportId;
		AuditReport report;

		reportId = super.getRequest().getData("auditReportId", int.class);
		report = this.repository.findAuditReportById(reportId);

		this.section = super.newObject(AuditSection.class);
		this.section.setName("");
		this.section.setNotes("");
		this.section.setHours(0);
		this.section.setKind(SectionKind.PRELIMINARY);
		this.section.setPublished(false);
		this.section.setAuditReport(report);
	}

	@Override
	public void authorise() {
		boolean status;
		int reportId;
		int auditorId;
		AuditReport report;

		reportId = super.getRequest().getData("auditReportId", int.class);
		report = this.repository.findAuditReportById(reportId);

		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = report != null && !report.isPublished() && report.getAuditor().getId() == auditorId;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.section, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.section);
	}

	@Override
	public void execute() {
		this.repository.save(this.section);
	}

	@Override
	public void unbind() {
		SelectChoices kinds;
		Tuple tuple;

		kinds = SelectChoices.from(SectionKind.class, this.section.getKind());

		tuple = super.unbindObject(this.section, "name", "notes", "hours", "kind", "published");

		tuple.put("auditReportId", this.section.getAuditReport().getId());
		tuple.put("kinds", kinds);
	}
}
