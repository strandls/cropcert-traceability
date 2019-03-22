package cropcert.traceability.collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class CollectionDao extends AbstractDao<Collection, Long>{

	@Inject
	protected CollectionDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Collection findById(Long id) {
		Session session = sessionFactory.openSession();
		Collection entity = null;
		try {
			entity = session.get(Collection.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
