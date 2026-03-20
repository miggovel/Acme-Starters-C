
package acme.features.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportCreateService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int auditorId;
		Auditor auditor;

		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		auditor = this.repository.findAuditorById(auditorId);

		this.auditReport = super.newObject(AuditReport.class);
		this.auditReport.setTicker("");
		this.auditReport.setName("");
		this.auditReport.setDescription("");
		this.auditReport.setStartMoment(null);
		this.auditReport.setEndMoment(null);
		this.auditReport.setMoreInfo("");
		this.auditReport.setPublished(false);
		this.auditReport.setAuditor(auditor);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);

		// ✔️ ticker único
		if (!super.getErrors().hasErrors("ticker")) {
			boolean exists = this.repository.existsAuditReportByTicker(this.auditReport.getTicker());
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
