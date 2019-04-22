package cropcert.traceability.batching;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class BatchingDao extends AbstractDao<Batching, Long>{

	@Inject
	protected BatchingDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Batching findById(Long id) {
		Session session = sessionFactory.openSession();
		Batching entity = null;
		try {
			entity = session.get(Batching.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
