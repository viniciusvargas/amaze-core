package mobi.inspiring.amaze.core.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class JPA {
	private JPA() {};
	
	@PersistenceContext
	private static EntityManager em = null;

	public static EntityManager em() {
		if (em == null || !em.isOpen()) {
			em = Persistence.createEntityManagerFactory("mobi-inspiring-amaze-persistence").createEntityManager();
		}
		return em;
	}
}