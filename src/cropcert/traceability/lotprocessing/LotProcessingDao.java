package cropcert.traceability.lotprocessing;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class LotProcessingDao extends AbstractDao<LotProcessing, Long>{

	@Inject
	protected LotProcessingDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public LotProcessing findById(Long id) {
		Session session = sessionFactory.openSession();
		LotProcessing entity = null;
		try {
			entity = session.get(LotProcessing.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
