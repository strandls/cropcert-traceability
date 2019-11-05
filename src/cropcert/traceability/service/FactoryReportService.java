package cropcert.traceability.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.Constants;
import cropcert.traceability.dao.FactoryReportDao;
import cropcert.traceability.model.Activity;
import cropcert.traceability.model.FactoryReport;
import cropcert.traceability.model.Lot;
import cropcert.traceability.util.UserUtil;
import cropcert.traceability.util.ValidationException;

public class FactoryReportService extends AbstractService<FactoryReport> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	private LotService lotService;
	
	@Inject
	private ActivityService activityService;

	@Inject
	public FactoryReportService(FactoryReportDao dao) {
		super(dao);
	}

	public FactoryReport save(HttpServletRequest request, String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException, ValidationException {
		FactoryReport factoryReport = objectMappper.readValue(jsonString, FactoryReport.class);


		Long lotId = factoryReport.getLotId();
		factoryReport = save(factoryReport);
		Lot lot = lotService.findById(lotId);
		
		lot.setFactoryReportId(factoryReport.getId());
		lotService.update(lot);
		
		 /**
         * save the activity here.
         */
        String userId = UserUtil.getUserDetails(request).getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(factoryReport.getClass().getSimpleName(), factoryReport.getId(), userId,
                timestamp, Constants.FACTORY_REPORT, factoryReport.getLotId().toString());
        activity = activityService.save(activity);

		return factoryReport;
	}
	
	public FactoryReport update(String jsonString) throws JsonParseException, JsonMappingException, IOException, ValidationException {
		FactoryReport factoryReport = objectMappper.readValue(jsonString, FactoryReport.class);
		factoryReport = update(factoryReport);
		return factoryReport;
	}
}
