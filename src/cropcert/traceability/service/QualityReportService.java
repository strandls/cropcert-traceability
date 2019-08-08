package cropcert.traceability.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.dao.QualityReportDao;
import cropcert.traceability.model.QualityReport;

public class QualityReportService extends AbstractService<QualityReport> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public QualityReportService(QualityReportDao dao) {
		super(dao);
	}

	public QualityReport save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		QualityReport qualityReport = objectMappper.readValue(jsonString, QualityReport.class);
		
		// update the transfer time stamp
		Timestamp transferTimestamp = qualityReport.getTimestamp();
		if (transferTimestamp == null) {
			transferTimestamp = new Timestamp(new Date().getTime());
			qualityReport.setTimestamp(transferTimestamp);
		}
		qualityReport = save(qualityReport);
		return qualityReport;
	}
}
