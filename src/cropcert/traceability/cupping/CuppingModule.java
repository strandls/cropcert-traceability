package cropcert.traceability.cupping;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class CuppingModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(CuppingDao.class).in(Scopes.SINGLETON);
		bind(CuppingService.class).in(Scopes.SINGLETON);
		bind(CuppingEndPoint.class).in(Scopes.SINGLETON);
	}
}
