package cropcert.traceability.wetbatch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
	
	public List<WetBatch> getByPropertyfromArray(String property, Object[] values, int limit, int offset) {
		
		/*
		 * queary to get all batches from collection center ids specified in the values and 
		 * the status of the batch is Not lot done.
		 */
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+property+" in (:values) and isLotDone = false" +
			    " order by id";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(queryStr);
		query.setParameterList("values", values);

		List<WetBatch> resultList = new ArrayList<WetBatch>();
		try {
			if(limit>0 && offset >= 0)
				query = query.setFirstResult(offset).setMaxResults(limit);
			resultList = query.getResultList();
			
		} catch (NoResultException e) {
			throw e;
		}
		session.close();
		return resultList;
	}
}
