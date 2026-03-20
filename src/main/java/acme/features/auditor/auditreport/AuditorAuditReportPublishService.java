
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = this.auditReport != null && !this.auditReport.isPublished() && this.auditReport.getAuditor().getId() == auditorId;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// no binding
	}

	@Override
	public void validate() {

		Collection<AuditSection> sections;

		sections = this.repository.findAuditSectionsByReportId(this.auditReport.getId());

		boolean hasSections = sections != null && !sections.isEmpty();

		super.state(hasSections, "*", "auditor.audit-report.error.no-sections");
	}

	@Override
	public void execute() {

		// ✔️ publicar report
		this.auditReport.setPublished(true);
		this.repository.save(this.auditReport);

		// ✔️ publicar todas las sections
		Collection<AuditSection> sections = this.repository.findAuditSectionsByReportId(this.auditReport.getId());

		for (AuditSection s : sections) {
			s.setPublished(true);
			this.repository.save(s);
		}
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "published");
	}
}
