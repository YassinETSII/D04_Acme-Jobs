
package acme.features.administrator.chart;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.chart.Chart;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorChartShowService implements AbstractShowService<Administrator, Chart> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AdministratorChartRepository repository;


	// AbstractShowService<Administrator, Chart> interface --------------

	@Override
	public boolean authorise(final Request<Chart> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Chart> request, final Chart entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "companySector", "companyNumber", "investorSector", "investorNumber");

	}

	@Override
	public Chart findOne(final Request<Chart> request) {
		assert request != null;
		Chart result = new Chart();

		List<Long> companyNumber = new LinkedList<>();
		List<String> companySector = new LinkedList<>();
		List<Long> investorNumber = new LinkedList<>();
		List<String> investorSector = new LinkedList<>();
		//--------------------------------------------------companies
		Collection<Object[]> companies = this.repository.numCompaniesBySector();

		companies.stream().forEach(c -> companyNumber.add((Long) c[0]));
		companies.stream().forEach(c -> companySector.add((String) c[1]));

		result.setCompanyNumber(companyNumber);
		result.setCompanySector(companySector);
		//--------------------------------------------------investors
		Collection<Object[]> investors = this.repository.numInvestorsBySector();

		investors.stream().forEach(i -> investorNumber.add((Long) i[0]));
		investors.stream().forEach(i -> investorSector.add((String) i[1]));

		result.setInvestorNumber(investorNumber);
		result.setInvestorSector(investorSector);

		return result;
	}

}
