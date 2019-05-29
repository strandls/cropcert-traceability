package cropcert.traceability.processedlot;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class ProcessedLotDao extends AbstractDao<ProcessedLot, Long>{

	@Inject
	protected ProcessedLotDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public ProcessedLot findById(Long id) {
		Session session = sessionFactory.openSession();
		ProcessedLot entity = null;
		try {
			entity = session.get(ProcessedLot.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
