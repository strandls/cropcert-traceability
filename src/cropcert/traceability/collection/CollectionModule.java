package cropcert.traceability.collection;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class CollectionModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(CollectionDao.class).in(Scopes.SINGLETON);
		bind(CollectionService.class).in(Scopes.SINGLETON);
		bind(CollectionEndPoint.class).in(Scopes.SINGLETON);
	}
}
