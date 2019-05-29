package cropcert.traceability.lotprocessing;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class LotProcessingModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(LotProcessingDao.class).in(Scopes.SINGLETON);
		bind(LotProcessingService.class).in(Scopes.SINGLETON);
		bind(LotProcessingEndPoint.class).in(Scopes.SINGLETON);
	}
}
