package cropcert.traceability.lotcreation;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class LotCreationModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(LotCreationDao.class).in(Scopes.SINGLETON);
		bind(LotCreationService.class).in(Scopes.SINGLETON);
		bind(LotCreationEndPoint.class).in(Scopes.SINGLETON);
	}
}
