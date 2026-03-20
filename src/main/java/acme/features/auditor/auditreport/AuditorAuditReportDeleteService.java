
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportDeleteService extends AbstractService<Auditor, AuditReport> {

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
		// no binding en delete
	}

	@Override
	public void validate() {
		// normalmente vacío
	}

	@Override
	public void execute() {
		Collection<AuditSection> sections;

		sections = this.repository.findAuditSectionsByReportId(this.auditReport.getId());

		for (AuditSection section : sections)
			this.repository.delete(section);

		this.repository.delete(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "published");
	}
}
