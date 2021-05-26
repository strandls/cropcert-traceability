package cropcert.traceability.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import cropcert.traceability.model.Batch;
import cropcert.traceability.model.Lot;

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
	
	public List getByPropertyfromArray(String property, Object[] values, int limit, int offset, String orderBy) {
		if(orderBy == null || "".equals(orderBy))
			orderBy = "id";
		String queryStr = "from Batch B left outer join Lot L "
				+ "on B.lotId = L.id where B.isDeleted != true "
				+ "and B."+property+" in (:values)"
				+ " order by B.createdOn";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(queryStr);
		query.setParameterList("values", values);
		
		try {
			if(limit>0 && offset >= 0)
				query = query.setFirstResult(offset).setMaxResults(limit);
			List<Object[]> resultList = query.getResultList();
			for(Object[] l : resultList) {
				if(l[1] != null)
					l[1] = ((Lot) l[1]).clone();
			}
			session.close();
			return resultList;
		} catch (NoResultException e) {
			throw e;
		} catch (CloneNotSupportedException e) {
			throw new NoResultException("Not able to clone the Lot");
		}
	}
	
	public List<Batch> getByPropertyfromArray(String property, Object[] values, Boolean isLotDone, Boolean isReadyForLot, int limit, int offset) {
		
		/*
		 * queary to get all batches from collection center ids specified in the values and 
		 * the status of the batch is Not lot done.
		 */
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+property+" in (:values) and "
			    		+ " isLotDone = " + isLotDone + " and "
			    		+ " isReadyForLot = " + isReadyForLot + " and "
						+ " ( isDeleted is null or isDeleted = " + false + " ) "
			    		+ " order by id";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(queryStr);
		query.setParameterList("values", values);

		List<Batch> resultList = new ArrayList<Batch>();
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
