package cropcert.traceability.wetbatch;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class WetBatchDao extends AbstractDao<WetBatch, Long>{

	@Inject
	protected WetBatchDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public WetBatch findById(Long id) {
		Session session = sessionFactory.openSession();
		WetBatch entity = null;
		try {
			entity = session.get(WetBatch.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
