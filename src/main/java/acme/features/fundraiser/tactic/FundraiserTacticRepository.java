/*
 * FundraiserTacticRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface FundraiserTacticRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

	@Query("select t.strategy from Tactic t where t.id = :id")
	Strategy findStrategyByTacticId(int id);

	@Query("select t from Tactic t where t.id = :id")
	Tactic findTacticById(int id);

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> findTacticsByStrategyId(int strategyId);

}
