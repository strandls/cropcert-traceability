package cropcert.traceability.lot;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class LotDao extends AbstractDao<Lot, Long>{

	@Inject
	protected LotDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Lot findById(Long id) {
		Session session = sessionFactory.openSession();
		Lot entity = null;
		try {
			entity = session.get(Lot.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
