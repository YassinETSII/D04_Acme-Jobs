
package acme.features.administrator.chart;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorChartRepository extends AbstractRepository {

	@Query("select count(c), c.sector from CompanyRecord c group by c.sector")
	Collection<Object[]> numCompaniesBySector();

	@Query("select count(i), i.sector from InvestorRecord i group by i.sector")
	Collection<Object[]> numInvestorsBySector();
}
