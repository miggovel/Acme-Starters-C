
package acme.entities.audits;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MathHelper;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.auditor.auditreport.AuditorAuditReportRepository;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditReport extends AbstractEntity {

	// Serialisation version -----------------------------------------------

	private static final long				serialVersionUID	= 1L;

	// Attributes ----------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String							ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String							name;

	@Mandatory
	@ValidText
	@Column
	private String							description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date							startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date							endMoment;

	@Optional
	@ValidUrl
	@Column
	private String							moreInfo;

	@Mandatory
	@Column
	private boolean							draftMode;

	// Derived attributes --------------------------------------------------

	@Transient
	@Autowired
	private AuditorAuditReportRepository	repository;


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null || !MomentHelper.isAfter(this.endMoment, this.startMoment))
			return 0.0;

		double months = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
		return MathHelper.roundOff(months, 1);
	}

	@Mandatory
	@ValidNumber(min = 0)
	@Transient
	public Integer getHours() {
		Integer total;

		if (this.repository == null || this.getId() == 0)
			return 0;

		total = this.repository.computeHours(this.getId());

		return total == null ? 0 : total;
	}

	// Relationships -------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Auditor auditor;

}
