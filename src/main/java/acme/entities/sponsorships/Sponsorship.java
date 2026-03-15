
package acme.entities.sponsorships;

import java.time.Duration;
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
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidSponsorship;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidSponsorship
public class Sponsorship extends AbstractEntity {

	private static final long		serialVersionUID	= 1L;

	// -----------------
	// ATTRIBUTES
	// -----------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	@ValidHeader
	@Column(length = 75)
	private String					name;

	@Mandatory
	@ValidText
	@Column(length = 255)
	private String					description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	@ValidUrl
	@Column(length = 255)
	private String					moreInfo;

	@Mandatory
	@Column
	private Boolean					draftMode;

	// -----------------
	// DERIVED
	// -----------------

	@Transient
	@Autowired
	private SponsorshipRepository	repository;


	@Transient
	public double monthsActive() {
		double result;
		Duration duration;
		duration = MomentHelper.computeDuration(this.startMoment, this.endMoment);
		result = duration.toDays() / 30.0;
		return result;
	}

	@Transient
	public Money totalMoney() {
		Money result;

		if (this.repository == null || this.getId() == 0) {
			result = new Money();
			result.setAmount(0.0);
			result.setCurrency("EUR");
		} else {
			Double totalAmount = this.repository.computeTotalMoneyBySponsorshipId(this.getId());
			result = new Money();
			result.setAmount(totalAmount == null ? 0.0 : totalAmount);
			result.setCurrency("EUR"); // Asegúrate de que todas las donations usan la misma moneda
		}

		return result;
	}
	// -----------------
	// RELATIONSHIPS
	// -----------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;

}
