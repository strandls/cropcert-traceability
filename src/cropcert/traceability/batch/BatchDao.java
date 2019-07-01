package cropcert.traceability.batch;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class BatchDao extends AbstractDao<Batch, Long>{

	@Inject
	protected BatchDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Batch findById(Long id) {
		Session session = sessionFactory.openSession();
		Batch entity = null;
		try {
			entity = session.get(Batch.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
