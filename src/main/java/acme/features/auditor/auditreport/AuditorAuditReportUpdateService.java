
package acme.features.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportUpdateService extends AbstractService<Auditor, AuditReport> {

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
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);

		// ✔️ ticker único (excepto el propio)
		if (!super.getErrors().hasErrors("ticker")) {
			boolean exists = this.repository.existsAuditReportByTickerAndNotId(this.auditReport.getTicker(), this.auditReport.getId());

			super.state(!exists, "ticker", "auditor.audit-report.error.duplicated");
		}

		// ✔️ fechas coherentes
		if (!super.getErrors().hasErrors("startMoment") && !super.getErrors().hasErrors("endMoment")) {

			boolean valid = this.auditReport.getStartMoment().before(this.auditReport.getEndMoment());
			super.state(valid, "endMoment", "auditor.audit-report.error.invalid-moment");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "published");
	}
}
