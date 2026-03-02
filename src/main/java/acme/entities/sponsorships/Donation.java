
package acme.entities.sponsorships;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Donation extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidHeader
	@Column(length = 75)
	private String				name;

	@Mandatory
	@ValidText
	@Column(length = 255)
	private String				notes;

	@Mandatory
	@ValidMoney
	@Column
	private Money				money;

	@Mandatory
	@Column
	@Enumerated(EnumType.STRING)
	private Donationkind		kind;

	// -----------------
	// RELATIONSHIP
	// -----------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsorship			sponsorship;
}
