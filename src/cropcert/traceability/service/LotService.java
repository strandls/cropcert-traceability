package cropcert.traceability.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import cropcert.traceability.LotStatus;
import cropcert.traceability.dao.LotDao;
import cropcert.traceability.model.Activity;
import cropcert.traceability.model.Lot;
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
			lot.setLotStatus(LotStatus.AT_FACTORY);
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
		lot.setLotStatus(LotStatus.AT_FACTORY);
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
		lot.setLotStatus(LotStatus.AT_FACTORY);
		lot = update(lot);		
		
        String userId = UserUtil.getUserDetails(request);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                timestamp, Constants.OUT_TURN, outTurn.toString());
        activity = activityService.save(activity);
        
		return "Updated succesfully";
	}

	public String dispatchToUnion(String jsonString, HttpServletRequest request) 
			throws JsonProcessingException, JSONException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray   = jsonObject.getJSONArray("ids");
		
		String dispatchTimeString = jsonObject.get(Constants.DISPATCH_TIME).toString();		
		Timestamp dispatchTime    = Timestamp.valueOf(dispatchTimeString);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			Long lotId = jsonArray.getLong(i);
			
			Lot lot = findById(lotId); 
			lot.setLotStatus(LotStatus.AT_UNION); 
			lot = update(lot);
			
	        String userId = UserUtil.getUserDetails(request);
	        Timestamp timestamp = new Timestamp(new Date().getTime());
	        Activity activity = new Activity(lot.getClass().getSimpleName(), lotId, userId,
	                timestamp, Constants.DISPATCH_TIME, dispatchTime.toString());
	        activity = activityService.save(activity);
		}
		return "Dispatched to union succesful";
	}

	public String updateGRNNumer(String jsonString, HttpServletRequest request) 
			throws JsonProcessingException, JSONException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Long id = jsonObject.getLong("id");
		Lot lot = findById(id);
		
		String grnNumber = jsonObject.get(Constants.GRN_NUMBER).toString();
		
		lot.setGrnNumber(grnNumber);
		lot.setLotStatus(LotStatus.AT_UNION);
		lot = update(lot);		
		
        String userId = UserUtil.getUserDetails(request);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                timestamp, Constants.GRN_NUMBER, grnNumber);
        activity = activityService.save(activity);
        
		return "GRN number added successfully";
	}

	public List<Lot> getByStatusAndUnion(String lotStatusString, String coCodes, Integer limit, Integer offset) {
		LotStatus lotStatus = LotStatus.fromValue(lotStatusString);
		Object[] values = coCodes.split(",");
        Long[] longValues = new Long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Long.parseLong(values[i].toString());
        }
		return ((LotDao) dao).getByPropertyfromArray("coCode", longValues, lotStatus, limit, offset);
	}
}
