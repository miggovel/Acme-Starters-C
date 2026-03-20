
package acme.features.any.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	@Autowired
	private AnyAuditSectionRepository	repository;

	private Collection<AuditSection>	sections;


	@Override
	public void load() {
		int reportId;

		reportId = super.getRequest().getData("auditReportId", int.class);
		this.sections = this.repository.findPublishedSectionsByReportId(reportId);
	}

	@Override
	public void authorise() {
		boolean status;
		int reportId;
		AuditReport report;

		reportId = super.getRequest().getData("auditReportId", int.class);
		report = this.repository.findAuditReportById(reportId);

		status = report != null && report.isPublished();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sections, "name", "kind", "hours");
	}
}
