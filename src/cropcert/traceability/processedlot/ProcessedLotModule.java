package cropcert.traceability.processedlot;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ProcessedLotModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(ProcessedLotDao.class).in(Scopes.SINGLETON);
		bind(ProcessedLotService.class).in(Scopes.SINGLETON);
		bind(ProcessedLotEndPoint.class).in(Scopes.SINGLETON);
	}
}
