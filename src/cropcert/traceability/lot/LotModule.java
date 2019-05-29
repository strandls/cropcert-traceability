package cropcert.traceability.lot;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class LotModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(LotDao.class).in(Scopes.SINGLETON);
		bind(LotService.class).in(Scopes.SINGLETON);
		bind(LotEndPoint.class).in(Scopes.SINGLETON);
	}
}
