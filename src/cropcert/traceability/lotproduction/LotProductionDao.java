package cropcert.traceability.lotproduction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class LotProductionDao extends AbstractDao<LotProduction, Long>{

	@Inject
	protected LotProductionDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public LotProduction findById(Long id) {
		Session session = sessionFactory.openSession();
		LotProduction entity = null;
		try {
			entity = session.get(LotProduction.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
