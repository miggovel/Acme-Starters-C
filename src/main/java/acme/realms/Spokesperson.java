
package acme.realms;

import javax.persistence.Column;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRealm;
import acme.client.components.validation.Mandatory;
import acme.constraints.ValidText;

public abstract class Spokesperson extends AbstractRealm {

	// Serialisation version ----------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ---------------------------------------

	@Mandatory
	@ValidText
	@Column
	private String				cv;

	@Mandatory
	@ValidText
	@Column
	private String				achievements;

	@Mandatory
	@Valid
	@Column
	private Boolean				licensed;

}
