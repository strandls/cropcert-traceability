package cropcert.traceability.batch;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class BatchModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(BatchDao.class).in(Scopes.SINGLETON);
		bind(BatchService.class).in(Scopes.SINGLETON);
		bind(BatchEndPoint.class).in(Scopes.SINGLETON);
	}
}
