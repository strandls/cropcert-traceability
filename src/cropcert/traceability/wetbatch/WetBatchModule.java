package cropcert.traceability.wetbatch;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class WetBatchModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(WetBatchDao.class).in(Scopes.SINGLETON);
		bind(WetBatchService.class).in(Scopes.SINGLETON);
		bind(WetBatchEndPoint.class).in(Scopes.SINGLETON);
	}
}
