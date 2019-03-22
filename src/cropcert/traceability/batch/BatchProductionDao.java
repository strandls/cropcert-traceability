package cropcert.traceability.batch;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class BatchProductionDao extends AbstractDao<BatchProduction, Long>{

	@Inject
	protected BatchProductionDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public BatchProduction findById(Long id) {
		Session session = sessionFactory.openSession();
		BatchProduction entity = null;
		try {
			entity = session.get(BatchProduction.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
