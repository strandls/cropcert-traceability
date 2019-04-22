package cropcert.traceability.batch;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.batching.Batching;
import cropcert.traceability.batching.BatchingService;
import cropcert.traceability.common.AbstractService;


public class BatchProductionService extends AbstractService<BatchProduction>{

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	private BatchingService batchingService;
	
	@Inject
	public BatchProductionService(BatchProductionDao dao) {
		super(dao);
	}

	public BatchProduction save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject object  = new JSONObject(jsonString);
		
		JSONArray collections = (JSONArray) object.remove("collections");
		
		List<Batching> batchings = batchingService.convertFromJsonArray(collections.toString());
		
		BatchProduction batchProduction = objectMappper.readValue(object.toString(), BatchProduction.class);
		
		//Calculate the total weight for the batch production
		float quantity = 0.0f;
		for(Batching batching : batchings) {
			quantity += batching.getQuantity();
		}
		batchProduction.setQuantity(quantity);
		batchProduction.setAvailableQuantity(quantity);

		// save the batch production for getting the batch id
		batchProduction = save(batchProduction);
		
		// Add batch id to all batching and save it to database.
		Long batchId = batchProduction.getBatchId();
		for(Batching batching : batchings) {
			batching.setBatchId(batchId);
			batchingService.save(batching);
		}
		return batchProduction;
	}
	
}
