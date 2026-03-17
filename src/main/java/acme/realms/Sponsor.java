
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidText
	@Column(length = 75)
	private String				address;

	@Mandatory
	//@ValidHeader
	@Column(length = 75)
	private String				im;

	@Mandatory
	@Column
	private Boolean				gold;
}
