package cropcert.traceability.batch;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class BatchProductionModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(BatchProductionDao.class).in(Scopes.SINGLETON);
		bind(BatchProductionService.class).in(Scopes.SINGLETON);
		bind(BatchProductionEndPoint.class).in(Scopes.SINGLETON);
	}
}
