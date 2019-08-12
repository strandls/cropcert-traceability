package cropcert.traceability.api;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class APIModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(ActivityApi.class).in(Scopes.SINGLETON);
		bind(BatchApi.class).in(Scopes.SINGLETON);
		bind(CuppingApi.class).in(Scopes.SINGLETON);
		bind(LotApi.class).in(Scopes.SINGLETON);
		bind(LotCreationApi.class).in(Scopes.SINGLETON);
		bind(QualityReportApi.class).in(Scopes.SINGLETON);
		bind(WetBatchApi.class).in(Scopes.SINGLETON);
	}

}
