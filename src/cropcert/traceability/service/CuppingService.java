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
import cropcert.traceability.dao.CuppingDao;
import cropcert.traceability.model.Activity;
import cropcert.traceability.model.Cupping;
import cropcert.traceability.util.UserUtil;

public class CuppingService extends AbstractService<Cupping> {

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	private ActivityService activityService;

	@Inject
	public CuppingService(CuppingDao dao) {
		super(dao);
	}

	public Cupping save(HttpServletRequest request, String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Cupping cupping = objectMappper.readValue(jsonString, Cupping.class);
		cupping.setIsDeleted(false);
		
		 /**
         * save the activity here.
         */
        String userId = UserUtil.getUserDetails(request).getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(cupping.getClass().getSimpleName(), cupping.getId(), userId,
                timestamp, Constants.CUPPING, cupping.getId().toString());
        activity = activityService.save(activity);
        
		return save(cupping);
	}

    public Cupping update(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Cupping cupping = objectMappper.readValue(jsonString, Cupping.class);
		cupping = update(cupping);
		return cupping;
	}
}
