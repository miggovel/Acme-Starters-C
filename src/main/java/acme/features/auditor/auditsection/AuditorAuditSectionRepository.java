
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;

@Repository
public interface AuditorAuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.auditReport.id = :reportId")
	Collection<AuditSection> findAuditSectionsByReportId(int reportId);

	@Query("select r from AuditReport r where r.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select s from AuditSection s where s.id = :id")
	AuditSection findAuditSectionById(int id);

}
