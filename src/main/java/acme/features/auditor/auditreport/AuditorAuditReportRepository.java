
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditReport;
import acme.entities.audits.AuditSection;
import acme.realms.Auditor;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.auditor.id = :auditorId")
	Collection<AuditReport> findAuditReportsByAuditorId(int auditorId);

	@Query("select a from AuditReport a where a.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findAuditorById(int id);

	@Query("select count(a) > 0 from AuditReport a where a.ticker = :ticker")
	boolean existsAuditReportByTicker(String ticker);

	@Query("select count(a) > 0 from AuditReport a where a.ticker = :ticker and a.id != :id")
	boolean existsAuditReportByTickerAndNotId(String ticker, int id);

	@Query("select s from AuditSection s where s.auditReport.id = :reportId")
	Collection<AuditSection> findAuditSectionsByReportId(int reportId);
}
