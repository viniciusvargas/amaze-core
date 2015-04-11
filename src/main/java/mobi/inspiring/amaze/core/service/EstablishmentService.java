package mobi.inspiring.amaze.core.service;

import java.util.List;

import mobi.inspiring.amaze.core.model.Establishment;

public class EstablishmentService extends GenericService<Establishment, Integer>{

	public EstablishmentService() {
		super(Establishment.class);
	}
	
	public Establishment findByCode(String code) {
		Establishment establishment = new Establishment();
		establishment.setCode(code);
		List<Establishment> list = this.findByFilter(establishment);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
