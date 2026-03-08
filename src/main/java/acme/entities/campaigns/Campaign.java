
package acme.entities.campaigns;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

	// Serialisation version ---------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes --------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column(unique = true)
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	// Constant -----------------------------------------

	private static final double	MILLIS_PER_MONTH	= 1000.0 * 60 * 60 * 24 * 30.4375;

	// Derived Attributes -------------------------------


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		long millis = this.endMoment.getTime() - this.startMoment.getTime();
		double months = millis <= 0 ? 0.0 : millis / Campaign.MILLIS_PER_MONTH;

		return Math.round(months * 10.0) / 10.0;
	}

	@Mandatory
	@ValidNumber(min = 0.0)
	@Transient
	public Double getEffort() {

		double total = 0.0;

		if (this.milestones != null)
			for (Milestone m : this.milestones)
				if (m.getEffort() != null)
					total += m.getEffort();

		return total;
	}

	// Responsabilities --------------------------------


	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;

	// Relationships -----------------------------------

	@Mandatory
	@Valid
	@OneToMany
	private Collection<Milestone>	milestones;

	@Mandatory
	@Valid
	@ManyToOne
	private Spokesperson			spokesperson;

}
