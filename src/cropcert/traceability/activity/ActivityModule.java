package cropcert.traceability.activity;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ActivityModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(ActivityDao.class).in(Scopes.SINGLETON);
		bind(ActivityService.class).in(Scopes.SINGLETON);
		bind(ActivityEndPoint.class).in(Scopes.SINGLETON);
	}
}
