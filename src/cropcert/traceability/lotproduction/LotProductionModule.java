package cropcert.traceability.lotproduction;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class LotProductionModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(LotProductionDao.class).in(Scopes.SINGLETON);
		bind(LotProductionService.class).in(Scopes.SINGLETON);
		bind(LotProductionEndPoint.class).in(Scopes.SINGLETON);
	}
}
