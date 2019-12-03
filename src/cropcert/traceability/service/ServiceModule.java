package cropcert.traceability.service;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ActivityService.class).in(Scopes.SINGLETON);
		bind(BatchService.class).in(Scopes.SINGLETON);
		bind(CuppingService.class).in(Scopes.SINGLETON);
		bind(LotCreationService.class).in(Scopes.SINGLETON);
		bind(LotService.class).in(Scopes.SINGLETON);
		bind(QualityReportService.class).in(Scopes.SINGLETON);
	}
}
