package cropcert.traceability.batch;

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
import cropcert.traceability.activity.Activity;
import cropcert.traceability.activity.ActivityService;
import cropcert.traceability.common.AbstractService;
import cropcert.traceability.util.UserUtil;

public class BatchService extends AbstractService<Batch> {

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	private ActivityService batchActivityService;

	@Inject
	public BatchService(BatchDao dao) {
		super(dao);
	}

	public Batch save(String jsonString, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Batch batch = objectMappper.readValue(jsonString, Batch.class);
		
		// update the transfer time stamp
		Timestamp transferTimestamp = batch.getCreatedOn();
		if (transferTimestamp == null) {
			transferTimestamp = new Timestamp(new Date().getTime());
			batch.setCreatedOn(transferTimestamp);
		}
		batch = save(batch);
		
		/**
		 * save the activity here.
		 */
		String userId = UserUtil.getUserDetails(request);
		Timestamp timestamp = transferTimestamp = new Timestamp(new Date().getTime());
		Activity activity = new Activity(Constants.BATCH, batch.getBatchId(), userId, 
				timestamp, "batch_creation", batch.getBatchName());
		activity = batchActivityService.save(activity);
		
		return batch;
	}
}
