package cropcert.traceability.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DaoModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(ActivityDao.class).in(Scopes.SINGLETON);
		bind(BatchDao.class).in(Scopes.SINGLETON);
		bind(CuppingDao.class).in(Scopes.SINGLETON);
		bind(LotCreationDao.class).in(Scopes.SINGLETON);
		bind(LotDao.class).in(Scopes.SINGLETON);
		bind(QualityReportDao.class).in(Scopes.SINGLETON);
		bind(WetBatchDao.class).in(Scopes.SINGLETON);
	}
}
