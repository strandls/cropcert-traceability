package cropcert.traceability.cupping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class CuppingDao extends AbstractDao<Cupping, Long>{

	@Inject
	protected CuppingDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Cupping findById(Long id) {
		Session session = sessionFactory.openSession();
		Cupping entity = null;
		try {
			entity = session.get(Cupping.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
