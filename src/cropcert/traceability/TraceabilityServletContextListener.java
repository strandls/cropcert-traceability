package cropcert.traceability;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import cropcert.traceability.api.APIModule;
import cropcert.traceability.dao.DaoModule;
import cropcert.traceability.service.ServiceModule;
import cropcert.traceability.util.Utility;

public class TraceabilityServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		Injector injector = Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				
				Configuration configuration = new Configuration();
				
				try {
					for (Class<?> cls : Utility.getEntityClassesFromPackage("cropcert")) {
						configuration.addAnnotatedClass(cls);
					}
				} catch (ClassNotFoundException | IOException | URISyntaxException e) {
					e.printStackTrace();
				}
				
				configuration = configuration.configure();
				SessionFactory sessionFactory = configuration.buildSessionFactory();
				
				bind(SessionFactory.class).toInstance(sessionFactory);
				bind(ObjectMapper.class).in(Scopes.SINGLETON);
				
				Map<String, String> props = new HashMap<String, String>();
				props.put("javax.ws.rs.Application", MyApplication.class.getName());
				props.put("jersey.config.server.wadl.disableWadl", "true");
				
				serve("/api/*").with(GuiceContainer.class, props);
			}
		}, new DaoModule(), new APIModule(), new ServiceModule());
		
		return injector; 
	}
}
