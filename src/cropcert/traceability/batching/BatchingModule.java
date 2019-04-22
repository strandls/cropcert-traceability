package cropcert.traceability.batching;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class BatchingModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(BatchingDao.class).in(Scopes.SINGLETON);
		bind(BatchingService.class).in(Scopes.SINGLETON);
		bind(BatchingEndPoint.class).in(Scopes.SINGLETON);
	}
}
