
package acme.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import acme.client.components.validation.ValidString;
import acme.client.components.validation.ValidUrl;
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
	@Valid
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidString
	@Column(unique = true)
	private String				name;

	@Mandatory
	@ValidString
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

	// Derived Attributes -------------------------------


	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return null;

		long millis = this.endMoment.getTime() - this.startMoment.getTime();
		if (millis <= 0)
			return 0.0;

		double days = millis / (1000.0 * 60 * 60 * 24);
		double months = days / 30.4375;

		return Math.round(months * 10.0) / 10.0;
	}

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

	@OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Milestone>	milestones;

}
