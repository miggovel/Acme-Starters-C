
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.datatypes.MilestoneKind;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Milestone extends AbstractEntity {

	//Serialisation version --------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ------------------------------------

	@Mandatory
	@ValidString
	@Column
	private String				title;

	@Mandatory
	@ValidString
	@Column
	private String				achievements;

	@Mandatory
	@ValidNumber(min = 0.0)
	@Column
	private Double				effort;

	@Mandatory
	@Valid
	@Column
	private MilestoneKind		kind;

	// Relationships ----------------------------------

	@ManyToOne(optional = false)
	@JoinColumn(name = "campaign_id")
	private Campaign			campaign;

}
