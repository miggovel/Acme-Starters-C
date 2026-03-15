
package acme.entities.sponsorships;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select count(d) from Donation d where d.sponsorship.id = :sponsorshipId")
	int countDonationsBySponsorshipId(int sponsorshipId);

	@Query("select sum(d.money.amount) from Donation d where d.sponsorship.id = :sponsorshipId")
	Double computeTotalMoneyBySponsorshipId(int sponsorshipId);

}
