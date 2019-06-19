package cropcert.traceability.report;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.traceability.common.AbstractDao;

public class QualityReportDao extends AbstractDao<QualityReport, Long>{

	@Inject
	protected QualityReportDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public QualityReport findById(Long id) {
		Session session = sessionFactory.openSession();
		QualityReport entity = null;
		try {
			entity = session.get(QualityReport.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
