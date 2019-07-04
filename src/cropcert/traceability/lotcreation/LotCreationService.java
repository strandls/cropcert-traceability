package cropcert.traceability.lotcreation;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.Constants;
import cropcert.traceability.activity.Activity;
import cropcert.traceability.activity.ActivityService;
import cropcert.traceability.batch.Batch;
import cropcert.traceability.batch.BatchService;
import cropcert.traceability.common.AbstractService;
import cropcert.traceability.lot.Lot;
import cropcert.traceability.lot.LotService;
import cropcert.traceability.lot.LotStatus;
import cropcert.traceability.util.UserUtil;

public class LotCreationService extends AbstractService<LotCreation> {

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject 
	private LotService lotService;
	
	@Inject
	private BatchService batchService;
	
	@Inject
	private ActivityService activityService;

	@Inject
	public LotCreationService(LotCreationDao dao) {
		super(dao);
	}

	public Lot saveInBulk(String jsonString, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		
		
		JSONArray jsonArray = (JSONArray) jsonObject.remove("batchIds");
		
		Lot lot = objectMappper.readValue(jsonObject.toString(), Lot.class);
		lot.setLotStatus(LotStatus.AT_CO_OPERATIVE);
		
		lot = lotService.save(lot);
		
		Timestamp timestamp = lot.getCreatedOn();
		
		Long lotId = lot.getId();
		
		String userId = UserUtil.getUserDetails(request);
		
		// Add traceability for the lot creation.
		for(int i=0; i<jsonArray.length(); i++) {
			Long batchId = jsonArray.getLong(i);
			
			LotCreation lotCreation = new LotCreation();
			lotCreation.setBatchId(batchId);
			lotCreation.setLotId(lotId);
			lotCreation.setUserId(userId);
			lotCreation.setTimestamp(timestamp);
			lotCreation.setNote("");
			
			// update the batch activity..
			Batch batch = batchService.findById(batchId);
			if (batch == null) {
				throw new JSONException("Invalid batch id found");
			}
			batch.setLotDone(true);
			batchService.update(batch);
			save(lotCreation);
		}
		
		// Add activity of lot creation.
        Activity activity = new Activity(lot.getClass().getSimpleName(), lotId, userId,
                timestamp, Constants.LOT_CREATION, lot.getLotName());
        activity = activityService.save(activity);
		
		return lot;
	}

	public List<Batch> getByLotId(String lotIdString, Integer limit, Integer offset) {
		Long lotId = Long.parseLong(lotIdString);
		List<LotCreation> lotCreations = getByPropertyWithCondtion("lotId", lotId, "=", limit, offset);
		List<Batch> batches = new ArrayList<Batch>();
		for (int i = 0; i < lotCreations.size(); i++) {
			LotCreation lotCreation = lotCreations.get(i);
			Long batchId = lotCreation.getBatchId();
			Batch batch = batchService.findById(batchId);
			batches.add(batch);
		}
		return batches;
	}

	public List<Long> getLotOrigins(String lotIdString) {
		Long lotId = Long.parseLong(lotIdString);
		List<LotCreation> lotCreations = getByPropertyWithCondtion("lotId", lotId, "=", -1, -1);
		List<Batch> batches = new ArrayList<Batch>();
		Set<Long> ccCodes = new HashSet<Long>();
		for (int i = 0; i < lotCreations.size(); i++) {
			LotCreation lotCreation = lotCreations.get(i);
			Long batchId = lotCreation.getBatchId();
			Batch batch = batchService.findById(batchId);
			ccCodes.add(batch.getCcCode());
			batches.add(batch);
		}
		return new ArrayList<Long>(ccCodes);
	}
}
