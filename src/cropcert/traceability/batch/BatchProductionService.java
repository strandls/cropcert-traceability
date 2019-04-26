package cropcert.traceability.batch;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
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
import cropcert.traceability.collection.Collection;
import cropcert.traceability.collection.CollectionService;
import cropcert.traceability.common.AbstractService;


public class BatchProductionService extends AbstractService<BatchProduction>{

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	private BatchingService batchingService;
	
	@Inject
	private CollectionService collectionService;
	
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
		
		// update the transfer time stamp
		Timestamp transferTimestamp = batchProduction.getTransferTimestamp();
		if (transferTimestamp == null) {
			transferTimestamp = new Timestamp(new Date().getTime());
			batchProduction.setTransferTimestamp(transferTimestamp);
		}

		// save the batch production for getting the batch id
		batchProduction = save(batchProduction);
		
		// Add batch id to all batching and save it to database.
		Long batchId = batchProduction.getBatchId();
		String quality = batchProduction.getQuality();
		for(Batching batching : batchings) {
			batching.setBatchId(batchId);
			batching.setTransferTimestamp(transferTimestamp);
			if (batching.getQuality()==null || "".equals(batching.getQuality())) {
				batching.setQuality(quality);
			}
			batchingService.save(batching);
		}
		
		// Update the collections quantity based on current batching.
		for(Batching batching : batchings) {
			Long collectionId = batching.getCollectionId();
			Collection collection = collectionService.findById(collectionId);
			float availaleQuantity = collection.getAvailableQuantity() - batching.getQuantity();
			collection.setAvailableQuantity(availaleQuantity);
			collectionService.update(collection);
		}
		
		return batchProduction;
	}
	
}
