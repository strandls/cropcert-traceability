package cropcert.traceability.lot;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.inject.Inject;

import cropcert.traceability.Constants;
import cropcert.traceability.activity.Activity;
import cropcert.traceability.activity.ActivityService;
import cropcert.traceability.common.AbstractService;
import cropcert.traceability.util.UserUtil;

public class LotService extends AbstractService<Lot> {

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	private ActivityService activityService;

	@Inject
	public LotService(LotDao dao) {
		super(dao);
	}

	public Lot save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Lot lot = objectMappper.readValue(jsonString, Lot.class);
		return save(lot);
	}

	public Lot update(String jsonString) throws JSONException, JsonProcessingException, IOException {
		Long id = new JSONObject(jsonString).getLong("id");
		Lot lot = findById(id);
		ObjectReader objectReader = objectMappper.readerForUpdating(lot);
		lot = objectReader.readValue(jsonString);
		lot = update(lot);
		return lot;
	}

	public String updateTimeToFactory(String jsonString, HttpServletRequest request) throws JsonProcessingException, JSONException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray   = jsonObject.getJSONArray("ids");
		
		String timeToFactoryString = jsonObject.get(Constants.TIME_TO_FACTORY).toString();		
		Timestamp timeToFactory    = Timestamp.valueOf(timeToFactoryString);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			Long id = jsonArray.getLong(i);
			Lot lot = findById(id);
			lot.setTimeToFactory(timeToFactory);
			lot.setLotStatus(LotStatus.IN_TRANSPORT);
			lot = update(lot);		
			
	        String userId = UserUtil.getUserDetails(request);
	        Timestamp timestamp = new Timestamp(new Date().getTime());
	        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
	                timestamp, Constants.TIME_TO_FACTORY, timeToFactory.toString());
	        activity = activityService.save(activity);
		}
		return "Updated succesfully";
	}

	public String updateMillingTime(String jsonString, HttpServletRequest request)  throws JsonProcessingException, JSONException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Long id = jsonObject.getLong("id");
		Lot lot = findById(id);
		
		String millingTimeString = jsonObject.get(Constants.MILLING_TIME).toString();		
		Timestamp millingTime    = Timestamp.valueOf(millingTimeString);
		
		lot.setMillingTime(millingTime);
		lot.setLotStatus(LotStatus.AT_UNION);
		lot = update(lot);		
		
        String userId = UserUtil.getUserDetails(request);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                timestamp, Constants.MILLING_TIME, millingTime.toString());
        activity = activityService.save(activity);
        
		return "Updated succesfully";
	}

	public String updateOutTurn(String jsonString, HttpServletRequest request)  throws JsonProcessingException, JSONException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Long id = jsonObject.getLong("id");
		Lot lot = findById(id);
		
		String outTurnString = jsonObject.get(Constants.OUT_TURN).toString();
		Float outTurn = Float.valueOf(outTurnString);
		
		lot.setOutTurn(outTurn);
		lot.setLotStatus(LotStatus.AT_UNION);
		lot = update(lot);		
		
        String userId = UserUtil.getUserDetails(request);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                timestamp, Constants.OUT_TURN, outTurn.toString());
        activity = activityService.save(activity);
        
		return "Updated succesfully";
	}
}
