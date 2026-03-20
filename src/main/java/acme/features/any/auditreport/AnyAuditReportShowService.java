
package acme.features.any.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;

@Service
public class AnyAuditReportShowService extends AbstractService<Any, AuditReport> {

	@Autowired
	private AnyAuditReportRepository	repository;

	private AuditReport					report;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.report = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.report != null && this.report.isPublished();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.report, "ticker", "name", "description", "startMoment", "endMoment");

		// ✔️ navegación
		tuple.put("auditReportId", this.report.getId());

		// ✔️ perfil auditor (MEJORADO)
		tuple.put("auditorFirm", this.report.getAuditor().getFirm());
		tuple.put("auditorHighlights", this.report.getAuditor().getHighlights());
		tuple.put("auditorSolicitor", this.report.getAuditor().isSolicitor());
	}
}
