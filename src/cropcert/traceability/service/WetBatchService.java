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
import cropcert.traceability.dao.WetBatchDao;
import cropcert.traceability.model.Activity;
import cropcert.traceability.model.WetBatch;
import cropcert.traceability.util.UserUtil;

public class WetBatchService extends AbstractService<WetBatch> {
    
    @Inject
    private ObjectMapper objectMappper;
    
    @Inject
    private ActivityService activityService;
    
    @Inject
    public WetBatchService(WetBatchDao dao) {
        super(dao);
    }
    
    public WetBatch save(String jsonString, HttpServletRequest request)
            throws JsonParseException, JsonMappingException, IOException, JSONException {
        WetBatch batch = objectMappper.readValue(jsonString, WetBatch.class);
        
        // update the transfer time stamp
        Timestamp transferTimestamp = batch.getCreatedOn();
        if (transferTimestamp == null) {
            transferTimestamp = new Timestamp(new Date().getTime());
            batch.setCreatedOn(transferTimestamp);
        }
        batch.setReadyForLot(false);
        batch.setLotDone(false);
        batch = save(batch);

        /**
         * save the activity here.
         */
        String userId = UserUtil.getUserDetails(request);
        Timestamp timestamp = transferTimestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(batch.getClass().getSimpleName(), batch.getBatchId(), userId,
                timestamp, Constants.BATCH, batch.getBatchName());
        activity = activityService.save(activity);
        
        return batch;
    }
    
    public WetBatch updateStartTime(String jsoString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsoString);
        Long id = jsonObject.getLong("id");
        Timestamp startTime = Timestamp.valueOf(jsonObject.get("startTime").toString());
        
        WetBatch wetBatch = findById(id);
        wetBatch.setStartTime(startTime);
        return update(wetBatch);
    }
    
    public WetBatch updateFermentationEndTime(String jsoString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsoString);
        Long id = jsonObject.getLong("id");
        Timestamp fermentationEndTime = Timestamp.valueOf(jsonObject.get("fermentationEndTime").toString());
        
        WetBatch wetBatch = findById(id);
        wetBatch.setFermentationEndTime(fermentationEndTime);
        return update(wetBatch);
    }
    
    public WetBatch updateDryingEndTime(String jsoString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsoString);
        Long id = jsonObject.getLong("id");
        Timestamp dryingEndTime = Timestamp.valueOf(jsonObject.get("dryingEndTime").toString());
        
        WetBatch wetBatch = findById(id);
        wetBatch.setDryingEndTime(dryingEndTime);
        return update(wetBatch);
    }
    
    public WetBatch updatePerchmentQuantity(String jsoString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsoString);
        Long id = jsonObject.getLong("id");
        float perchmentQuantity = Float.parseFloat(jsonObject.get("perchmentQuantity").toString());
        
        WetBatch wetBatch = findById(id);
        wetBatch.setPerchmentQuantity(perchmentQuantity);
        return update(wetBatch);
    }

	public WetBatch update(String jsonString) throws JSONException, JsonProcessingException, IOException {
		Long id = new JSONObject(jsonString).getLong("id");
		WetBatch wetBatch = findById(id);
		ObjectReader objectReader = objectMappper.readerForUpdating(wetBatch);
		wetBatch = objectReader.readValue(jsonString);
		wetBatch = update(wetBatch);
		return wetBatch;
	}

	public List<WetBatch> getByPropertyfromArray(String property, String ccCodes, Boolean isLotDone, Boolean isReadyForLot, Integer limit, Integer offset) {
		Object[] values = ccCodes.split(",");
        Long[] longValues = new Long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Long.parseLong(values[i].toString());
        }
        return ((WetBatchDao) dao).getByPropertyfromArray(property, longValues, isLotDone, isReadyForLot, limit, offset);
	}

	public void updateReadyForLot(String jsonString) throws JSONException {
		JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("batchIds");
		for (int i = 0; i < jsonArray.length(); i++) {
			Long batchId = jsonArray.getLong(i);
			WetBatch batch = dao.findById(batchId);
			batch.setReadyForLot(true);
			dao.update(batch);
		}
		
	}
}
