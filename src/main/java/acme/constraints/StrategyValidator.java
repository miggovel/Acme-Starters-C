/*
 * StrategyValidator.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private StrategyRepository	repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {
			{
				boolean uniqueStrategy;
				Strategy existingStrategy;

				existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}
			{
				boolean correctInterval;

				if (strategy.getStartMoment() != null && strategy.getEndMoment() != null) {
					correctInterval = MomentHelper.isAfter(strategy.getEndMoment(), strategy.getStartMoment());
					super.state(context, correctInterval, "endMoment", "acme.validation.strategy.interval.message");
				}
			}
			{
				// Publication constraints: these must hold whenever draftMode is false.
				if (!strategy.isDraftMode()) {
					boolean hasTactics;
					boolean validIntervalInFuture;
					Double expectedPercentageWrapper;
					double expectedPercentage;
					boolean validExpectedPercentage;

					hasTactics = this.repository.countTacticsByStrategyId(strategy.getId()) > 0;
					super.state(context, hasTactics, "*", "acme.validation.strategy.publish.minimum-tactics.message");

					validIntervalInFuture = strategy.getStartMoment() != null && strategy.getEndMoment() != null && MomentHelper.isAfter(strategy.getEndMoment(), strategy.getStartMoment())
						&& MomentHelper.isFuture(strategy.getStartMoment()) && MomentHelper.isFuture(strategy.getEndMoment());
					super.state(context, validIntervalInFuture, "*", "acme.validation.strategy.publish.interval.message");

					expectedPercentageWrapper = this.repository.computeExpectedPercentageByStrategyId(strategy.getId());
					expectedPercentage = expectedPercentageWrapper == null ? 0.0 : expectedPercentageWrapper.doubleValue();
					validExpectedPercentage = expectedPercentage >= 0.0 && expectedPercentage <= 100.0;
					super.state(context, validExpectedPercentage, "*", "acme.validation.strategy.publish.expected-percentage.message");
				}
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
