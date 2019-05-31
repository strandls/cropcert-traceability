package cropcert.traceability.wetbatch;

import java.io.IOException;
import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class WetBatchService extends AbstractService<WetBatch> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public WetBatchService(WetBatchDao dao) {
		super(dao);
	}

	public WetBatch save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		WetBatch batch = objectMappper.readValue(jsonString, WetBatch.class);
		return save(batch);
	}
	
	public WetBatch updateStartTime(String jsoString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp startTime = (Timestamp) jsonObject.get("startTime");
		
		WetBatch wetBatch = findById(id);
		wetBatch.setStartTime(startTime);
		return update(wetBatch);
	}
	
	public WetBatch updateFermentationEndTime(String jsoString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp fermentationEndTime = (Timestamp) jsonObject.get("fermentationEndTime");
		
		WetBatch wetBatch = findById(id);
		wetBatch.setFermentationEndTime(fermentationEndTime);
		return update(wetBatch);
	}
	
	public WetBatch updateDryingEndTime(String jsoString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		Timestamp dryingEndTime = (Timestamp) jsonObject.get("dryingEndTime");
		
		WetBatch wetBatch = findById(id);
		wetBatch.setDryingEndTime(dryingEndTime);
		return update(wetBatch);
	}
	
	public WetBatch updatePerchmentQuantity(String jsoString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsoString);
		Long id = jsonObject.getLong("id");
		float perchmentQuantity = (float) jsonObject.get("perchmentQuantity");
		
		WetBatch wetBatch = findById(id);
		wetBatch.setPerchmentQuantity(perchmentQuantity);
		return update(wetBatch);
	}
}
