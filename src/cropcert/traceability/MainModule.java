package cropcert.traceability;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class MainModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ObjectMapper.class).in(Scopes.SINGLETON);
		bind(Ping.class).in(Scopes.SINGLETON);
		bind(Logout.class).in(Scopes.SINGLETON);
		bind(JavaClient.class).in(Scopes.SINGLETON);
	}
}
