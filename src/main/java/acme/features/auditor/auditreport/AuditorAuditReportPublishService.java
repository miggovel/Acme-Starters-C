
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

		status = this.auditReport != null && this.auditReport.isDraftMode() && this.auditReport.getAuditor().getId() == auditorId;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		Collection<AuditSection> sections;

		super.validateObject(this.auditReport);

		if (!super.getErrors().hasErrors("ticker")) {
			boolean exists;

			exists = this.repository.existsAuditReportByTickerAndNotId(this.auditReport.getTicker(), this.auditReport.getId());

			super.state(!exists, "ticker", "auditor.audit-report.error.duplicated");
		}

		if (!super.getErrors().hasErrors("startMoment") && !super.getErrors().hasErrors("endMoment")) {
			boolean validInterval;

			validInterval = this.auditReport.getStartMoment() != null && this.auditReport.getEndMoment() != null && this.auditReport.getStartMoment().before(this.auditReport.getEndMoment());

			super.state(validInterval, "endMoment", "auditor.audit-report.error.invalid-moment");
		}

		sections = this.repository.findAuditSectionsByReportId(this.auditReport.getId());

		boolean hasSections = sections != null && !sections.isEmpty();
		super.state(hasSections, "*", "auditor.audit-report.error.no-sections");
	}

	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "hours", "monthsActive");
	}
}
