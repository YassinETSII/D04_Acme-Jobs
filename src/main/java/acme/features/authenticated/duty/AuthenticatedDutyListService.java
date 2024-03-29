
package acme.features.authenticated.duty;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedDutyListService implements AbstractListService<Authenticated, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AuthenticatedDutyRepository repository;

	// AbstractListService<Authenticated, Duty> interface --------------


	//An authenticated principal can not list the duties of a not finalMode job
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean result;
		Collection<Duty> duties = this.repository.findManyDutiesByJobId(request.getModel().getInteger("idJob"));
		result = duties.stream().allMatch(d -> d.getJob().isFinalMode() == true);
		return result;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");
	}

	@Override
	public Collection<Duty> findMany(final Request<Duty> request) {
		assert request != null;

		Collection<Duty> result;

		int id = request.getModel().getInteger("idJob");
		result = this.repository.findManyDutiesByJobId(id);
		return result;

	}

}
