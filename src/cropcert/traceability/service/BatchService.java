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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

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

		batchValidation(batch, farmerContributions);

		// update the transfer time stamp
		Timestamp createdOn = batch.getCreatedOn();
		if (createdOn == null) {
			createdOn = new Timestamp(new Date().getTime());
			batch.setCreatedOn(createdOn);
		}
		batch.setReadyForLot(true);
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
					batchCreation.setTimestamp(createdOn);
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
		Activity activity = new Activity(batch.getClass().getSimpleName(), batch.getBatchId(), userId, createdOn,
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

	/*
	 * This object list is the comma separated value.
	 */
	public List<Batch> getByPropertyfromArray(String property, String objectList, Boolean isLotDone,
			Boolean isReadyForLot, int limit, int offset) {
		Object[] values = objectList.split(",");
		Long[] longValues = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.parseLong(values[i].toString());
		}
		return ((BatchDao) dao).getByPropertyfromArray(property, longValues, isLotDone, isReadyForLot, limit, offset);
	}
}
