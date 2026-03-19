
package acme.entities.campaigns;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

	@Query("select c from Campaign c where c.name = :name")
	Campaign findCampaignByName(String name);

	@Query("select c from Campaign c where c.draftMode = :mode")
	Collection<Campaign> findCampaignWithDraftModeProvided(Boolean mode);

	@Query("select c from Campaign c where c.spokesperson.id = :id")
	Collection<Campaign> findBySpokespersonId(@Param("id") int id);

	@Query("select count(m) from Milestone m where m.campaign.id = :id")
	Integer countMilestonesByCampaignId(@Param("id") int id);

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :id")
	Double computeEffortByCampaignId(@Param("id") int id);
}
