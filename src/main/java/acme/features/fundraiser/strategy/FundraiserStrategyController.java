/*
 * FundraiserStrategyController.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.strategy;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Controller
public class FundraiserStrategyController extends AbstractController<Fundraiser, Strategy> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", FundraiserStrategyListService.class);
		super.addBasicCommand("show", FundraiserStrategyShowService.class);
		super.addBasicCommand("create", FundraiserStrategyCreateService.class);
		super.addBasicCommand("update", FundraiserStrategyUpdateService.class);
		super.addBasicCommand("delete", FundraiserStrategyDeleteService.class);

		super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
