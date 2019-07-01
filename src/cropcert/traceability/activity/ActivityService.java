package cropcert.traceability.activity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class ActivityService extends AbstractService<Activity> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public ActivityService(ActivityDao dao) {
		super(dao);
	}

	public Activity save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Activity batch = objectMappper.readValue(jsonString, Activity.class);
		
		// update the transfer time stamp
		Timestamp transferTimestamp = batch.getTimestamp();
		if (transferTimestamp == null) {
			transferTimestamp = new Timestamp(new Date().getTime());
			batch.setTimestamp(transferTimestamp);
		}
		batch = save(batch);
		return batch;
	}
}
