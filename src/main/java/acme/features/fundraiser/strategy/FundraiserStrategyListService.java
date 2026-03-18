/*
 * FundraiserStrategyListService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyListService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Collection<Strategy>			strategies;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int fundraiserId;

		fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategies = this.repository.findStrategiesByFundraiserId(fundraiserId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "startMoment", "endMoment", "expectedPercentage", "draftMode");
	}

}
