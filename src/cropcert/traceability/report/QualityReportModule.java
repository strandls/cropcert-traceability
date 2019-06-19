package cropcert.traceability.report;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class QualityReportModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(QualityReportDao.class).in(Scopes.SINGLETON);
		bind(QualityReportService.class).in(Scopes.SINGLETON);
		bind(QualityReportEndPoint.class).in(Scopes.SINGLETON);
	}
}
