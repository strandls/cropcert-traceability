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
import cropcert.traceability.model.Batch;
import cropcert.traceability.model.BatchCreation;
import cropcert.traceability.model.WetBatch;
import cropcert.traceability.util.UserUtil;
import cropcert.traceability.util.ValidationException;

public class WetBatchService extends AbstractService<WetBatch> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	private ActivityService activityService;
	
    @Inject
    private BatchCreationService batchCreationService;

	@Inject
	public WetBatchService(WetBatchDao dao) {
		super(dao);
	}

	public WetBatch save(String jsonString, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException, JSONException, ValidationException {
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray farmerContributions = (JSONArray) jsonObject.remove("farmerContributions");

		WetBatch batch = objectMappper.readValue(jsonObject.toString(), WetBatch.class);
		batch.setDeleted(false);

		if(farmerContributions != null && farmerContributions.length() > 0)
			batchValidation(batch, farmerContributions);
		// update the transfer time stamp
		Timestamp transferTimestamp = batch.getCreatedOn();
		if (transferTimestamp == null) {
			transferTimestamp = new Timestamp(new Date().getTime());
			batch.setCreatedOn(transferTimestamp);
		}
		batch.setReadyForLot(false);
		batch.setLotDone(false);
		batch = save(batch);

		String userId = UserUtil.getUserDetails(request).getId();

		if (farmerContributions != null)
			for (int i = 0; i < farmerContributions.length(); i++) {
				jsonObject = farmerContributions.getJSONObject(i);
				BatchCreation batchCreation = objectMappper.readValue(jsonObject.toString(), BatchCreation.class);
				batchCreation.setBatchId(batch.getBatchId());
				batchCreation.setUserId(userId);
				if (batchCreation.getTimestamp() == null) {
					batchCreation.setTimestamp(transferTimestamp);
				}
				batchCreationService.save(batchCreation);
			}

		// Update the batch name, with batch id as well
		String batchName = batch.getBatchName() + "_" + batch.getBatchId();
		batch.setBatchName(batchName);
		update(batch);

		/**
		 * save the activity here.
		 */
		Timestamp timestamp = transferTimestamp = new Timestamp(new Date().getTime());
		Activity activity = new Activity(batch.getClass().getSimpleName(), batch.getBatchId(), userId, timestamp,
				Constants.BATCH, batch.getBatchName());
		activity = activityService.save(activity);

		return batch;
	}
	
	private void batchValidation(Batch batch, JSONArray farmerContributions) throws JSONException, ValidationException {
		// TODO Auto-generated method stub
		float batchWeight = batch.getQuantity();
		
		float contributionWeight = 0;
		for(int i=0; i<farmerContributions.length(); i++) {
			contributionWeight += farmerContributions.getJSONObject(i).getDouble("weight");
		}
		
		if(batchWeight != contributionWeight) {
			throw new ValidationException("Farmer contribution and the batch weight are not matching");
		}
	}

	public WetBatch updateStartTime(String jsoString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp startTime = new Timestamp((Long) jsonObject.get("startTime"));

		WetBatch wetBatch = findById(id);
		wetBatch.setStartTime(startTime);
		return update(wetBatch);
	}

	public WetBatch updateFermentationEndTime(String jsoString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp fermentationEndTime = new Timestamp((Long) jsonObject.get("fermentationEndTime"));

		WetBatch wetBatch = findById(id);
		wetBatch.setFermentationEndTime(fermentationEndTime);
		return update(wetBatch);
	}

	public WetBatch updateDryingEndTime(String jsoString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp dryingEndTime = new Timestamp((Long) jsonObject.get("dryingEndTime"));

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

	public void updateReadyForLot(String jsonString) throws JSONException {
		JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("batchIds");
		for (int i = 0; i < jsonArray.length(); i++) {
			Long batchId = jsonArray.getLong(i);
			WetBatch batch = dao.findById(batchId);
			batch.setReadyForLot(true);
			dao.update(batch);
		}

	}
	
	public List<WetBatch> getByPropertyfromArray(String property, String ccCodes, Boolean isLotDone,
			Boolean isReadyForLot, Integer limit, Integer offset) {
		Object[] values = ccCodes.split(",");
		Long[] longValues = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.parseLong(values[i].toString());
		}
		return ((WetBatchDao) dao).getByPropertyfromArray(property, longValues, isLotDone, isReadyForLot, limit,
				offset);
	}
	
	public List<WetBatch> getByPropertyfromArray(String property, String objectList, int limit, int offset)
			throws NumberFormatException {
		Object[] values = objectList.split(",");
		Long[] longValues = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.parseLong(values[i].toString());
		}
		return dao.getByPropertyfromArray(property, longValues, limit, offset);
	}
}
