
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private Collection<AuditSection>		sections;

	private AuditReport						report;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int reportId;

		reportId = super.getRequest().getData("auditReportId", int.class);
		this.sections = this.repository.findAuditSectionsByReportId(reportId);
		this.report = this.repository.findAuditReportById(reportId);
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

		status = report != null && report.getAuditor().getId() == auditorId;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sections, "name", "kind", "hours", "notes", "published");
		super.getResponse().addGlobal("showCreate", !this.report.isPublished());
		super.getResponse().addGlobal("auditReportId", super.getRequest().getData("auditReportId", int.class));
	}
}
