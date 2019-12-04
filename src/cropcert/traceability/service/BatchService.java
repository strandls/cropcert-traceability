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

import cropcert.traceability.BatchType;
import cropcert.traceability.Constants;
import cropcert.traceability.dao.BatchDao;
import cropcert.traceability.model.Activity;
import cropcert.traceability.model.Batch;
import cropcert.traceability.model.BatchCreation;
import cropcert.traceability.util.UserUtil;
import cropcert.traceability.util.ValidationException;

public class BatchService extends AbstractService<Batch> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	private ActivityService activityService;

	@Inject
	private BatchCreationService batchCreationService;

	@Inject
	public BatchService(BatchDao dao) {
		super(dao);
	}

	public Batch save(String jsonString, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException, JSONException, ValidationException {
		JSONObject jsonObject = new JSONObject(jsonString);

		JSONArray farmerContributions = (JSONArray) jsonObject.remove("farmerContributions");

		Batch batch = objectMappper.readValue(jsonObject.toString(), Batch.class);
		batch.setIsDeleted(false);

		if (farmerContributions != null && farmerContributions.length() > 0)
			batchValidation(batch, farmerContributions);

		// update the transfer time stamp
		Timestamp createdOn = batch.getCreatedOn();
		if (createdOn == null) {
			createdOn = new Timestamp(new Date().getTime());
			batch.setCreatedOn(createdOn);
		}
		batch.setIsReadyForLot(true);
		batch = save(batch);

		String userId = UserUtil.getUserDetails(request).getId();

		if (farmerContributions != null)
			for (int i = 0; i < farmerContributions.length(); i++) {
				jsonObject = farmerContributions.getJSONObject(i);
				BatchCreation batchCreation = objectMappper.readValue(jsonObject.toString(), BatchCreation.class);
				batchCreation.setBatchId(batch.getId());
				batchCreation.setUserId(userId);
				if (batchCreation.getTimestamp() == null) {
					batchCreation.setTimestamp(createdOn);
				}
				batchCreationService.save(batchCreation);
			}

		// Update the batch name, with batch id as well
		String batchName = batch.getBatchName() + "_" + batch.getId();
		batch.setBatchName(batchName);
		update(batch);

		/**
		 * save the activity here.
		 */
		Activity activity = new Activity(batch.getClass().getSimpleName(), batch.getId(), userId, createdOn,
				Constants.BATCH_CREATION, batch.getBatchName());
		activity = activityService.save(activity);

		return batch;
	}

	private void batchValidation(Batch batch, JSONArray farmerContributions) throws JSONException, ValidationException {
		// TODO Auto-generated method stub
		float batchWeight = batch.getQuantity();

		float contributionWeight = 0;
		for (int i = 0; i < farmerContributions.length(); i++) {
			contributionWeight += farmerContributions.getJSONObject(i).getDouble("weight");
		}

		if (batchWeight != contributionWeight) {
			throw new ValidationException("Farmer contribution and the batch weight are not matching");
		}
	}
	
	public Batch updateStartTime(String jsoString) throws JSONException, ValidationException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp startTime = new Timestamp((Long) jsonObject.get("startTime"));

		Batch wetBatch = findById(id);
		if(BatchType.DRY.equals(wetBatch.getType()))
			throw new ValidationException("Found dry batch");
		wetBatch.setStartTime(startTime);
		return update(wetBatch);
	}

	public Batch updateFermentationEndTime(String jsoString) throws JSONException, ValidationException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp fermentationEndTime = new Timestamp((Long) jsonObject.get("fermentationEndTime"));

		Batch wetBatch = findById(id);
		if(BatchType.DRY.equals(wetBatch.getType()))
			throw new ValidationException("Found dry batch");
		wetBatch.setFermentationEndTime(fermentationEndTime);
		return update(wetBatch);
	}

	public Batch updateDryingEndTime(String jsoString) throws JSONException, ValidationException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp dryingEndTime = new Timestamp((Long) jsonObject.get("dryingEndTime"));

		Batch wetBatch = findById(id);
		if(BatchType.DRY.equals(wetBatch.getType()))
			throw new ValidationException("Found dry batch");
		wetBatch.setDryingEndTime(dryingEndTime);
		return update(wetBatch);
	}

	public Batch updatePerchmentQuantity(String jsoString) throws JSONException, ValidationException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		float perchmentQuantity = Float.parseFloat(jsonObject.get("perchmentQuantity").toString());

		Batch wetBatch = findById(id);
		if(BatchType.DRY.equals(wetBatch.getType()))
			throw new ValidationException("Found dry batch");
		wetBatch.setPerchmentQuantity(perchmentQuantity);
		return update(wetBatch);
	}

	public Batch update(String jsonString) throws JSONException, JsonProcessingException, IOException {
		Long id = new JSONObject(jsonString).getLong("id");
		Batch wetBatch = findById(id);
		ObjectReader objectReader = objectMappper.readerForUpdating(wetBatch);
		wetBatch = objectReader.readValue(jsonString);
		wetBatch = update(wetBatch);
		return wetBatch;
	}

	public void updateReadyForLot(String jsonString) throws JSONException {
		JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("batchIds");
		for (int i = 0; i < jsonArray.length(); i++) {
			Long batchId = jsonArray.getLong(i);
			Batch batch = dao.findById(batchId);
			batch.setIsReadyForLot(true);
			dao.update(batch);
		}

	}

	/*
	 * This object list is the comma separated value.
	 */
	public List<Batch> getByPropertyfromArray(String property, String objectList, Boolean isLotDone,
			Boolean isReadyForLot, int limit, int offset) throws NumberFormatException {
		Object[] values = objectList.split(",");
		Long[] longValues = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.parseLong(values[i].toString());
		}
		return ((BatchDao) dao).getByPropertyfromArray(property, longValues, isLotDone, isReadyForLot, limit, offset);
	}

	public List<Batch> getByPropertyfromArray(String property, String objectList, int limit, int offset)
			throws NumberFormatException {
		Object[] values = objectList.split(",");
		Long[] longValues = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.parseLong(values[i].toString());
		}
		return dao.getByPropertyfromArray(property, longValues, limit, offset);
	}
}
